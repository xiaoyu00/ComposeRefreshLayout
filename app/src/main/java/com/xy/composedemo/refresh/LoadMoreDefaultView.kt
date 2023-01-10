package com.xy.composedemo.refresh

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadMoreDefaultView(animationState: Boolean, isNoMore: Boolean = false) {
    val text = if (isNoMore) "我也是有底线的" else if (animationState) "加载中..." else "上拉加载更多"
    Row {
        if (animationState) {
            CircularProgressIndicator(
                modifier = Modifier.size(26.dp),
                color = Color.Gray
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
        Text(text = text)
    }
}