package com.boringkm.customcalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.boringkm.customcalendar.ui.theme.CustomCalendarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val monthCalendar = MonthCalendar()

        setContent {
            CustomCalendarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))

                        val currentYearMonth = remember { mutableStateOf(monthCalendar.getDate()) }

                        Text(text = "현재: ${currentYearMonth.value}")
                        Spacer(modifier = Modifier.height(10.dp))
                        val days = monthCalendar.dayList.observeAsState()

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "월", Modifier.padding(12.dp), textAlign = TextAlign.Center)
                            Text(text = "화", Modifier.padding(12.dp), textAlign = TextAlign.Center)
                            Text(text = "수", Modifier.padding(12.dp), textAlign = TextAlign.Center)
                            Text(text = "목", Modifier.padding(12.dp), textAlign = TextAlign.Center)
                            Text(text = "금", Modifier.padding(12.dp), textAlign = TextAlign.Center)
                            Text(text = "토", Modifier.padding(12.dp), textAlign = TextAlign.Center)
                            Text(text = "일", Modifier.padding(12.dp), textAlign = TextAlign.Center)
                        }

                        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
                            items(days.value!!) { date ->
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = "${date.day}",
                                        Modifier
                                            .fillMaxSize()
                                            .background(if (date.today) Color.Green else Color.LightGray)
                                            .padding(8.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(horizontalArrangement = Arrangement.Center) {
                            Button(onClick = {
                                currentYearMonth.value = monthCalendar.moveToBeforeMonth()
                            }) {
                                Text(text = "Before")
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(onClick = {
                                currentYearMonth.value = monthCalendar.moveToNextMonth()
                            }) {
                                Text(text = "Next")
                            }
                        }
                    }

                }
            }
        }
    }
}
