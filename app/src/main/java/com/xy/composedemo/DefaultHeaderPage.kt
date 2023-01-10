package com.xy.composedemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xy.composedemo.refresh.RefreshDefaultHeader
import com.xy.composedemo.ui.theme.ComposeDemoTheme
import com.xy.refreshlayout.RefreshLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun RefreshDefaultHeaderSimple() {

    var refreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Column {
        Row(Modifier.background(Color.White)) {
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {
                refreshing = true
            }) {
                Text(
                    text = "刷新"
                )
            }
        }

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
fun DefaultPreview4() {
    ComposeDemoTheme {
        RefreshDefaultHeaderSimple()
    }
}