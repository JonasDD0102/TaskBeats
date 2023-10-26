package com.comunidadedevspace

import com.comunidadedevspace.taskbeats.data.Local.Task
import com.comunidadedevspace.taskbeats.data.Local.TaskDao
import com.comunidadedevspace.taskbeats.presentation.ActionType
import com.comunidadedevspace.taskbeats.presentation.TasDetailViewModel
import com.comunidadedevspace.taskbeats.presentation.TaskAction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class TaskDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


        private val taskDao : TaskDao = mock()

        private val underTest :TasDetailViewModel  by lazy {
            TasDetailViewModel(
                taskDao,
            )
        }

        @Test
        fun update_task() = runTest {
            //Given
            val task = Task(
                1,
                "academia",
                "treino perna"
            )
            val taskAction = TaskAction(
                task,
                ActionType.UPDATE.name
            )
            //When
            underTest.execute(taskAction)

            //Then
            verify(taskDao).update(task)
        }
        @Test
        fun deleteById() = runTest{
            //Given
            val task  = Task(
                2,
                "joans",
                "progamers"
            )

            val taskAction   = TaskAction(
                task,
                ActionType.DELETE.name
            )

            //When

            underTest.execute(taskAction)

            //Then
            verify(taskDao).deleteById(task.id)

        }

        @Test
        fun create_Task() = runTest {
            //Given
            val task = Task(
                1,
                "name",
                "bigsnMES"
            )
            val taskAction = TaskAction(
                task,
                ActionType.CREATE.name
            )

            //When
            underTest.execute(taskAction)

            //Then

            verify(taskDao).insert(task)

        }


    }

