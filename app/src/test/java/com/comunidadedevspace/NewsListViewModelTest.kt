package com.comunidadedevspace

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.comunidadedevspace.taskbeats.data.Remote.NewsDto
import com.comunidadedevspace.taskbeats.data.Remote.NewsResponce
import com.comunidadedevspace.taskbeats.data.Remote.NewsServise
import com.comunidadedevspace.taskbeats.presentation.NewsListViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class NewsListViewModelTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val service: NewsServise = mock()
    private lateinit var underTest: NewsListViewModel

    @Test
    fun `GIVEN request succeed news WHEN  fetch THEN return list`() {
        runBlocking {

            //GIVEN
            val expected = listOf<NewsDto>(
                NewsDto(
                    id = "big",
                    content = "content",
                    imageUrl = "image",
                    title = "title1"
                )
            )
            val responce = NewsResponce(data = expected, category = "tech")
            whenever(service.fetchNews()).thenReturn(responce)

            //WHEN
            underTest = NewsListViewModel(service)
            val result =  underTest.newsListLiveData.getOrAwaitValue()

            //THEN
            assert(result == expected)
        }
    }
}

