package com.xy.composedemo.refresh

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.xy.composedemo.R

@Composable
fun RefreshLottieHeader(animationState: Boolean, refreshLottieHeight: Dp = 80.dp) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottielego))
    val progress by animateLottieCompositionAsState(
        isPlaying = animationState,
        composition = composition,
        iterations = LottieConstants.IterateForever,
        cancellationBehavior = LottieCancellationBehavior.OnIterationFinish
    )
    LottieAnimation(
        composition,
        modifier = Modifier.height(refreshLottieHeight),
        progress = { progress })
}