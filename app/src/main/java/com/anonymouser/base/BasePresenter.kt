package com.anonymouser.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent

/**
 *
 * @what
 * @author
 * @date 2018/11/19
 *
 */
interface BasePresenter: LifecycleObserver {
    fun loadData()
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()
}