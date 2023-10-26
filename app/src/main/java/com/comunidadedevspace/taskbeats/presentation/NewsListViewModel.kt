package com.comunidadedevspace.taskbeats.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.data.Remote.NewsDto
import com.comunidadedevspace.taskbeats.data.Remote.NewsServise
import com.comunidadedevspace.taskbeats.data.Remote.RetrofitModule
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val newsService :NewsServise
):ViewModel() {

    private val _newsListLiveData = MutableLiveData<List<NewsDto>>()
    val newsListLiveData :LiveData<List<NewsDto>> = _newsListLiveData

    //fluxo: faço a chamada logo que inicializar a viewModel
    init {
        getNewsList()
    }

    private fun getNewsList(){
        viewModelScope.launch {
            try {
                val response = newsService.fetchNews()
                _newsListLiveData.value = response.data
            } catch (ex:Exception){
                ex.printStackTrace()
            }
        }
    }

    companion object{
        fun create():NewsListViewModel{
            val newsService =  RetrofitModule.createNewsService()
            return NewsListViewModel(newsService)
        }
    }

}