package com.yijihun.navigationcustomdataclassargumentexample.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.yijihun.navigationcustomdataclassargumentexample.model.Lecture
import com.yijihun.navigationcustomdataclassargumentexample.model.Student
import com.yijihun.navigationcustomdataclassargumentexample.ui.theme.NavigationCustomDataClassArgumentExampleTheme
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailScreen(
    val lectureName: String,
    val studentGradeList: List<Int>,
    val lecture: Lecture,
    val studentList: List<Student>,
) : Screen {
    data class State(
        val lectureName: String,
        val studentGradeDistribution: Map<Int, Int>,
        val lecture: Lecture,
        val studentList: List<Student>,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data object OnBackClick : Event
    }
}

@CircuitInject(DetailScreen::class, ActivityRetainedComponent::class)
@Composable
fun Detail(
    state: DetailScreen.State,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
        ) {
            DetailTopAppBar(
                popBackStack = {
                    state.eventSink(DetailScreen.Event.OnBackClick)
                },
                lectureName = state.lectureName,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "강의:\n ${state.lecture}",
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "교수: ${"홍길동"}",
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "수강생 목록:\n ${state.studentList}",
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "수강색 학년 분포:\n ${state.studentGradeDistribution}",
            )
        }
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    NavigationCustomDataClassArgumentExampleTheme {
        Detail(
            state = DetailScreen.State(
                lectureName = "컴퓨터 구조",
                lecture = Lecture(1, "컴퓨터 구조", "홍길동"),
                studentList = listOf(
                    Student(1, "홍길동", 20),
                ),
                studentGradeDistribution = mapOf(),
                eventSink = {},
            ),
        )
    }
}
