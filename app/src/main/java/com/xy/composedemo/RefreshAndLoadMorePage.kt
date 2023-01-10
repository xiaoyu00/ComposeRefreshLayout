package com.xy.composedemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xy.composedemo.refresh.LoadMoreDefaultView
import com.xy.composedemo.refresh.RefreshDefaultHeader
import com.xy.composedemo.ui.theme.ComposeDemoTheme
import com.xy.refreshlayout.LoadMoreLayout
import com.xy.refreshlayout.RefreshLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun RefreshAndLoadMoreSimple() {
    var refreshing by remember { mutableStateOf(false) }
    var isLoadMore by remember { mutableStateOf(false) }
    val isNoMore = false
    val scope = rememberCoroutineScope()
    LoadMoreLayout(loadMoreState = isLoadMore, onLoadMore = {
        isLoadMore = true
        scope.launch {
            delay(2000)
            isLoadMore = false
        }
    }, loadMore = { offset, state ->
        LoadMoreDefaultView(state, isNoMore)
    }) {
        RefreshLayout(refreshingState = refreshing, onRefresh = {
            refreshing = true
            scope.launch {
                delay(2000)
                refreshing = false
            }
        }, refreshHeader = { offset, state ->
            RefreshDefaultHeader(state, offset)
        }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                items(30) {
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .padding(start = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Hello $it!")
                    }
                    Divider()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    ComposeDemoTheme {
        RefreshAndLoadMoreSimple()
    }
}