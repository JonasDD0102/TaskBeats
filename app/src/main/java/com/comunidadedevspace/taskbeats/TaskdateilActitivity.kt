package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class TaskdateilActitivity : AppCompatActivity() {

    private   var task:Task? = null
    private lateinit var btnDone : Button

    companion object {
       private const val TASK_DETAIL_EXTRA = "task.extra.detail"
         fun start (context:Context, task:Task?):Intent{
            val intent =Intent(context,TaskdateilActitivity::class.java)
                .apply {
                    putExtra( TASK_DETAIL_EXTRA,task)
                }
             return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taskdateil_actitivity)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Recuperar task
         task = intent.getSerializableExtra( TASK_DETAIL_EXTRA) as Task?

        val edtTitle = findViewById<EditText>(R.id.edt_task_title)
        val edtDescription = findViewById<EditText>(R.id.edt_task_description)
        btnDone = findViewById<Button>(R.id.btn_done)

        if (task!= null){
            edtTitle.setText(task!!.title)
            edtDescription.setText(task!!.description)
        }


        btnDone.setOnClickListener{
              val title = edtTitle.text.toString()
              val desc = edtDescription.text.toString()

              if(title.isNotEmpty() && desc.isNotEmpty()){
                  if(task == null ) {
                      addOrUpdateTask(0,title, desc,ActionType.CREATE)
                  }else{
                      addOrUpdateTask(task!!.id,title, desc,ActionType.UPDATE)
                  }
              }else{
                  showMessage(it,"Filds are required")
              }
        }

        // Recuperar campo do xml
         //tvtitle = findViewById(R.id.tv_task_title_detail)

        //  Setar um novo texto na tela
       //  tvtitle.text =  task?.title ?:"Adicione uma terefa"
    }

    private fun addOrUpdateTask(
        id:Int,
        title: String,
        description : String,
        actionType: ActionType
    ){
        val Task =Task(id,title,description)
        returnAction(Task, actionType)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_task_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleti_task -> {

                if(task != null) {
                   returnAction(task!!,ActionType.DELETE)

                }else{
                 showMessage(btnDone,"Item not found")
                }
                return true
            }
        else -> super.onOptionsItemSelected(item)
        }
    }

    private fun returnAction(task:Task , actionType: ActionType){
        val intent = Intent()
            .apply {
                val taskAction = TaskAction(task, actionType.name )
                putExtra(TASK_ACTION_RESULT, taskAction)
            }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
    private fun showMessage(view: View, message:String){
        Snackbar.make(view,message, Snackbar.LENGTH_LONG)
            .setAction("action",null)
            .show()
    }
}