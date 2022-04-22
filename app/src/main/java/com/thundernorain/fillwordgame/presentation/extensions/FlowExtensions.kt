package com.thundernorain.fillwordgame.presentation.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

inline fun <T> Flow<T>.collectLatestOn(
    owner: LifecycleOwner,
    crossinline onCollect: suspend (T) -> Unit
) = owner.lifecycleScope.launchWhenStarted {
    collectLatest { onCollect(it) }
}