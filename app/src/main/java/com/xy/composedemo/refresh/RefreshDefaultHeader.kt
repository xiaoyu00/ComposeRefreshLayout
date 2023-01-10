package com.xy.composedemo.refresh

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.xy.composedemo.R

@Composable
fun RefreshDefaultHeader(animationState: Boolean, offset: Float, refreshHeaderHeight: Float = 80f) {
    val state = offset <= refreshHeaderHeight * 0.8
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (animationState)
            CircularProgressIndicator(modifier = Modifier.size(26.dp), color = Color.Gray)
        else Image(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = if (state) R.drawable.ic_down_arrow else R.drawable.ic_up_arrow),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.size(16.dp))

        Text(text = if (animationState) "正在刷新" else if (state) "下拉刷新" else "松开刷新")
    }
}