package com.p4u1.cardlife.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<P: BaseContract.BasePresenter<V, P>, V: BaseContract.BaseView<P>>: AppCompatActivity(), BaseContract.BaseView<P> {

    lateinit var presenter: P

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = createPresenter()
        presenter.bindView(this as V)

        setContentView(getViewResourceId())

        presenter.onViewCreated()
        onViewLoaded(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()

        presenter.onViewStopped()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.onViewDestroyed()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        presenter.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }
}
