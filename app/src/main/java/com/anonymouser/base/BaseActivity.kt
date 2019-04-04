package com.anonymouser.base

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import com.hwangjr.rxbus.RxBus
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

/**
 *
 * @what
 * @author
 * @date 2018/11/19
 *
 */
abstract class BaseActivity<T : BasePresenter> : AppCompatActivity() {
    protected var mPresenter:T? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mPresenter = getPresenter()

        initView()

        registerEvent()
        mPresenter?.run {
            RxBus.get().register(this)
            lifecycle.addObserver(this)
            this.loadData()
        }
    }

    abstract fun initView()

    abstract fun registerEvent()

    abstract fun unRegisterEvent()

    @Nullable
    abstract fun getPresenter(): T

    abstract fun getLayoutId(): Int

    fun <T> autoDispose(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this))
    }

    fun <T> disposeOnDestroy(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
    }

    fun attainActivity() = this

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.let {
            RxBus.get().unregister(it)
            lifecycle.removeObserver(it)
        }
        unRegisterEvent()
    }
}