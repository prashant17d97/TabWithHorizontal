package com.prashant.tabnhorizontalpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.prashant.tabnhorizontalpager.ui.theme.TabNHorizontalPagerTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val tabList = listOf("Live", "OnGoing", "Upcoming")
            val state = rememberPagerState()
            val coroutine = rememberCoroutineScope()
            val color = remember {
                Animatable(Color.Blue)
            }
            TabNHorizontalPagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TabRow(
                            selectedTabIndex = state.currentPage,
                            modifier = Modifier.background(
                                Color.White
                            ),
                            indicator = { tabPositions ->
                                TabRowDefaults.Indicator(
                                    Modifier.pagerTabIndicatorOffset(state, tabPositions).size(0.dp)
                                )
                            }
                        ) {
                            tabList.forEachIndexed { index, s ->
                                LaunchedEffect(key1 = state.currentPage == index) {
                                    color.animateTo(Color.Blue/*.takeIf { state.currentPage == index }
                                        ?: Color.Green*/)
                                }
                                Tab(
                                    selected = state.currentPage == index,
                                    onClick = {
                                        coroutine.launch {
                                            state.animateScrollToPage(index)
                                        }
                                    },
                                    modifier = Modifier.background(
                                        color = color.value.takeIf { state.currentPage == index }
                                            ?: Color.White,
                                        shape = CircleShape
                                    )
                                ) {
                                    Text(text = s, color = Color.Black)
                                }
                            }
                        }
                        HorizontalPager(
                            count = tabList.size,
                            state = state,
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            Text(text = tabList[page])
                        }
                    }
                }
            }
        }
    }
}

