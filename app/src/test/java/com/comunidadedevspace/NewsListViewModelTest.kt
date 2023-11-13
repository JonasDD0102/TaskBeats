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
            val expectedTop = listOf<NewsDto>(
                NewsDto(
                    id = "big1",
                    content = "content1",
                    imageUrl = "image1",
                    title = "title1"
                )
            )
            val expectedAll = listOf<NewsDto>(
                NewsDto(
                    id = "big2",
                    content = "content2",
                    imageUrl = "image2",
                    title = "title2"
                )
            )

            val topResponce = NewsResponce(data = expectedTop)
            val allResponce = NewsResponce(data = expectedAll)
            whenever(service.fetTopNews()).thenReturn(topResponce)
            whenever(service.fetAllNews()).thenReturn(allResponce)

            //WHEN
            underTest = NewsListViewModel(service)
            val result =  underTest.newsListLiveData.getOrAwaitValue()

            //THEN
            assert(result == expectedTop + expectedAll)
        }
    }
}

