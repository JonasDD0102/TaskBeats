package com.comunidadedevspace.taskbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.Local.Task
import com.comunidadedevspace.taskbeats.data.Remote.NewsResponce
import com.comunidadedevspace.taskbeats.data.Remote.RetrofitModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [TaskListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskListFragment : Fragment() {

    private lateinit var ctnContetn: LinearLayout

    private val retorfitModule  = RetrofitModule

    private val adapter : TaskListAdapter by lazy {
        TaskListAdapter(::openTaskListDetail)
    }
    private val viewModel : TaskListViewModel by lazy {
        TaskListViewModel.create(requireActivity().application)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         ctnContetn = view.findViewById(R.id.ctn_content)

         val rvtask = view.findViewById<RecyclerView>(R.id.rv_task_list)
         rvtask.adapter= adapter

         val  newsService  = retorfitModule.createNewsService()



    }

    override fun onStart() {
        super.onStart()

        listFromDataBase()
    }

    private fun listFromDataBase(){
        //oObserver
        val listObserver = Observer<List<Task>>{ listTasks ->
            if(listTasks.isEmpty()){
                ctnContetn.visibility = View.VISIBLE
            }else{
                ctnContetn.visibility  = View.GONE
            }
            adapter.submitList(listTasks)
        }
        //live data
        viewModel.taskListLiveData.observe(this, listObserver)

    }
    private fun openTaskListDetail(task: Task){
        val intent = TaskdateilActitivity.start(requireContext(),task)
            requireActivity().startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * * @return A new instance of fragment TaskListFragment.
         */
        @JvmStatic
        fun newInstance() = TaskListFragment()
            }
    }
