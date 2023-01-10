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
fun RefreshLayout(
    refreshingState: Boolean = false,
    refreshEffectHeight: Dp = 80.dp,
    onRefresh: () -> Unit,
    refreshHeader: @Composable (offset: Float, animationState: Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    val refreshDistance = with(LocalDensity.current) {
        refreshEffectHeight.value
    }
    var oldValue = false //防重复调用
    val state = rememberRefreshState(refreshingState) { newValue ->
        if (newValue && newValue != oldValue && !refreshingState) {
            onRefresh()
        }
        oldValue = newValue
        true
    }
    LaunchedEffect(refreshingState) {
        if (!state.isAnimationRunning && state.currentValue != refreshingState) {
            state.animateToAndChangeState(refreshingState)
        }
    }
    Box(
        modifier = Modifier
            .clipToBounds()
            .nestedScroll(state.RefreshNestedScroll)
            .swipeable(
                state = state,
                anchors = mapOf(
                    0f to false,
                    refreshDistance to true
                ),
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Vertical,
            )

    ) {
        Box(
            modifier = Modifier
                .offset(y = (-refreshEffectHeight + state.offset.value.dp))
                .height(refreshEffectHeight)
                .align(Alignment.TopCenter)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            refreshHeader(state.offset.value, state.currentValue)
        }
        Box(
            Modifier
                .offset(y = if (state.offset.value > 0) state.offset.value.dp else 0.dp)
        ) {
            content()
        }
    }
}
