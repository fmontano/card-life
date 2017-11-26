package com.p4u1.cardlife.ui.base

import android.support.annotation.CallSuper

abstract class BaseContractPresenter<V: BaseContract.BaseView<P>, P: BaseContract.BasePresenter<V, P>>: BaseContract.BasePresenter<V, P> {

    protected lateinit var view: V

    @CallSuper
    override fun bindView(view: V) {
        this.view = view
    }

    @CallSuper
    open fun onViewLoaded() {
        // left empty for now
    }

    override fun unbindView() {
        // Cleanup?
    }

    override fun onViewCreated() {
        // Intentionally left empty for children to override when necessary
    }

    override fun onViewStarted() {
        // Intentionally left empty for children to override when necessary
    }

    override fun onViewStopped() {
        // Intentionally left empty for children to override when necessary
    }

    override fun onViewDestroyed() {
        // Intentionally left empty for children to override when necessary
    }
}
