package com.xy.refreshlayout

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity

@Composable
@ExperimentalMaterialApi
fun <T : Any> rememberRefreshState(
    initialValue: T,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    confirmStateChange: (newValue: T) -> Boolean = { true }
): RefreshSwipeableState<T> {
    return rememberSaveable(
        saver = RefreshSwipeableState.Saver(
            animationSpec = animationSpec,
            confirmStateChange = confirmStateChange
        )
    ) {
        RefreshSwipeableState(
            initialValue = initialValue,
            animationSpec = animationSpec,
            confirmState = confirmStateChange
        )
    }
}

@ExperimentalMaterialApi
class RefreshSwipeableState<T>(
    initialValue: T,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    private val confirmState: (newValue: T) -> Boolean = { true }
) : SwipeableState<T>(
    initialValue = initialValue,
    animationSpec = animationSpec, confirmStateChange = confirmState
) {

    suspend fun animateToAndChangeState(stateAble: T) {
        animateTo(stateAble)
        confirmState.invoke(stateAble)
    }

    companion object {
        fun <T : Any> Saver(
            animationSpec: AnimationSpec<Float>,
            confirmStateChange: (T) -> Boolean
        ) = androidx.compose.runtime.saveable.Saver<RefreshSwipeableState<T>, T>(
            save = { it.currentValue },
            restore = { RefreshSwipeableState(it, animationSpec, confirmStateChange) }
        )
    }
}

@ExperimentalMaterialApi
internal val <T> SwipeableState<T>.RefreshNestedScroll: NestedScrollConnection
    get() = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

            val delta = available.toFloat()
            return if (delta < 0 && source == NestedScrollSource.Drag) {
                performDrag(delta).toOffset()
            } else {
                Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            return if (source == NestedScrollSource.Drag) {
                performDrag(available.toFloat()).toOffset()
            } else {
                Offset.Zero
            }
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            val toFling = Offset(available.x, available.y).toFloat()
            val minBound = Float.NEGATIVE_INFINITY
            return if (toFling < 0 && offset.value > minBound) {
                performFling(velocity = toFling)
                // since we go to the anchor with tween settling, consume all for the best UX
                available
            } else {
                Velocity.Zero
            }
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            performFling(velocity = Offset(available.x, available.y).toFloat())
            return available
        }

        private fun Float.toOffset(): Offset = Offset(0f, this)

        private fun Offset.toFloat(): Float = this.y
    }

@ExperimentalMaterialApi
internal val <T> SwipeableState<T>.LoadMoreNestedScroll: NestedScrollConnection
    get() = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

            val delta = available.toFloat()
            return if (delta > 0 && source == NestedScrollSource.Drag) {
                performDrag(delta).toOffset()
            } else {
                Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            return if (source == NestedScrollSource.Drag) {
                performDrag(available.toFloat()).toOffset()
            } else {
                Offset.Zero
            }
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            val toFling = Offset(available.x, available.y).toFloat()
            val minBound = Float.NEGATIVE_INFINITY
            return if (toFling > 0 ) {
                performFling(velocity = toFling)
                // since we go to the anchor with tween settling, consume all for the best UX
                available
            } else {
                Velocity.Zero
            }
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            performFling(velocity = Offset(available.x, available.y).toFloat())
            return available
        }

        private fun Float.toOffset(): Offset = Offset(0f, this)

        private fun Offset.toFloat(): Float = this.y
    }