package com.yijihun.navigationcustomdataclassargumentexample.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize

@Parcelize
data object HomeScreen : Screen {
    data class State(
        val lectureList: List<Lecture>,
        val studentGradeList: List<Int>,
        val studentList: List<Student>,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data class OnLectureClick(
            val lectureName: String,
            val studentGradeList: List<Int>,
            val lecture: Lecture,
            val studentList: List<Student>,
        ) : Event
    }
}

@CircuitInject(HomeScreen::class, ActivityRetainedComponent::class)
@Composable
fun Home(
    state: HomeScreen.State,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(
                count = state.lectureList.size,
                key = { index -> index },
            ) { index ->
                LectureItem(
                    lectureName = state.lectureList[index].lectureName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            state.eventSink(
                                HomeScreen.Event.OnLectureClick(
                                    state.lectureList[index].lectureName,
                                    state.studentGradeList,
                                    state.lectureList[index],
                                    state.studentList,
                                ),
                            )
                        },
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Color.LightGray,
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    NavigationCustomDataClassArgumentExampleTheme {
        Home(
            state = HomeScreen.State(
                lectureList = persistentListOf(
                    Lecture(lectureId = 1, lectureName = "컴퓨터 구조", professor = "홍길동"),
                    Lecture(lectureId = 2, lectureName = "운영체제", professor = "김철수"),
                    Lecture(lectureId = 3, lectureName = "알고리즘", professor = "이영희"),
                ),
                studentGradeList = listOf(),
                studentList = listOf(),
                eventSink = {},
            ),
        )
    }
}
