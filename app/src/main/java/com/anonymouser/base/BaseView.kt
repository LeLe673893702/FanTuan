package com.newler.putonghuapractice.base

import android.app.Activity
import android.support.v4.app.FragmentActivity
import com.uber.autodispose.AutoDisposeConverter

/**
 *
 * @what
 * @author 17173
 * @date 2018/11/19
 *
 */
interface BaseView {
    fun <T> autoDispose(): AutoDisposeConverter<T>
    fun <T> disposeOnDestroy(): AutoDisposeConverter<T>
    fun attainActivity(): FragmentActivity?
}