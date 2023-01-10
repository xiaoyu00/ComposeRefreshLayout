package com.xy.refreshlayout

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun LoadMoreLayout(
    loadMoreState: Boolean = false,
    loadMoreEffectHeight: Dp = 40.dp,
    onLoadMore: () -> Unit,
    loadMore: @Composable (offset: Float, animationState: Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    val loadDistance = with(LocalDensity.current) {
        0f
    }
    var oldValue = false //防重复调用
    val state = rememberRefreshState(loadMoreState) { newValue ->
        if (newValue && newValue != oldValue && !loadMoreState) {
            onLoadMore()
        }

        oldValue = newValue
        true
    }
    LaunchedEffect(loadMoreState) {
        if (!state.isAnimationRunning && state.currentValue != loadMoreState) {
            state.animateToAndChangeState(loadMoreState)
        }
    }
    Box(
        modifier = Modifier
            .clipToBounds()
            .nestedScroll(state.LoadMoreNestedScroll)
            .swipeable(
                state = state,
                anchors = mapOf(
                    loadDistance to false,
                    -loadMoreEffectHeight.value to true
                ),
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Vertical,
            )

    ) {
        Box(
            Modifier
                .offset(y = if (state.offset.value >= 0f) 0.dp else state.offset.value.dp)
        ) {
            content()
        }

        Box(
            modifier = Modifier
                .offset(y = loadMoreEffectHeight + state.offset.value.dp)
                .height(loadMoreEffectHeight)
                .fillMaxWidth()
                .align(Alignment.BottomCenter), contentAlignment = Alignment.Center
        ) {
            loadMore(state.offset.value, state.currentValue)

        }

    }
}