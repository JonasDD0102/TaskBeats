package com.comunidadedevspace.taskbeats.presentation



import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.AppDataBase
import com.comunidadedevspace.taskbeats.data.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private val dataBase by lazy { Room.databaseBuilder(
        applicationContext,            // nome do banco de dados//
        AppDataBase::class.java, "taskbeats-datebase"
    ).build() }

     private val dao by lazy {
         dataBase.taskDao()
     }

    private lateinit var ctnContetn: LinearLayout
    private val adapter : TaskListAdapter = TaskListAdapter(::onListItemClicked)

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
            result:ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){
            //pegando resultado
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT)as TaskAction
            val task: Task = taskAction.task


           when(taskAction.actionType ){
               ActionType.DELETE.name -> deletebyid(task.id)
               ActionType.CREATE.name -> insertIntoDataBase(task)
               ActionType.UPDATE.name -> updateIntoDataBase(task)

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        setSupportActionBar(findViewById(R.id.toolbar))


        listFromDataBase()

        ctnContetn= findViewById(R.id.ctn_content)

        // recyclerView//
        val rvtask = findViewById<RecyclerView>(R.id.rv_task_list)
        rvtask.adapter= adapter

        val fab =findViewById<FloatingActionButton>(R.id.fab_add)
        fab.setOnClickListener{
            openTaskListDetail(null)
        }

    }

    private fun insertIntoDataBase(task: Task){
        //para incerir a tarefa//
        CoroutineScope(IO).launch {
           dao.insert(task)
       listFromDataBase()
        }
    }

    private fun updateIntoDataBase(task: Task){
        //para incerir a tarefa//
        CoroutineScope(IO).launch {
            dao.update(task)
            listFromDataBase()
        }
    }
    //listagem//
    private fun listFromDataBase(){
        CoroutineScope(IO).launch {
            val myDataBaseList :List<Task> = dao.getAll()
            adapter.submitList(myDataBaseList)

        }
    }
    private fun showMessage(view: View,message:String){
        Snackbar.make(view,message,Snackbar.LENGTH_LONG)
            .setAction("action",null)
            .show()
    }

    private fun onListItemClicked(task: Task){
        openTaskListDetail(task)
    }
    private fun openTaskListDetail(task: Task? = null){
        val intent = TaskdateilActitivity.start(this, task)
        startForResult.launch(intent)
    }

    private fun deletebyid(id:Int) {
        CoroutineScope(IO).launch {
            dao.deleteById(id)
            listFromDataBase()
        }
    }
    private fun deleteAll() {
        CoroutineScope(IO).launch {
            dao.deleteAll()
            listFromDataBase()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_task_list, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleti_All -> {
            deleteAll()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
//CRUD
enum class  ActionType{
      DELETE,
      UPDATE,
      CREATE
}
data class TaskAction(
    val task: Task,
    val actionType: String
    ):Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT "