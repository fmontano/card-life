package com.p4u1.cardlife.ui.base

import android.os.Bundle
import android.support.annotation.LayoutRes

interface BaseContract<P: BaseContract.BasePresenter<V, P>, V: BaseContract.BaseView<P>> {

    interface BaseView<out P> {
        /**
         * Gets the resource id of the layout to be inflated for this Activity or Fragment
         */
        @LayoutRes
        fun getViewResourceId(): Int

        /**
         * Used to get an instance of the presenter tied to this view. Should be replaced with Dagger
         * at some point
         */
        fun createPresenter(): P

        /**
         * Called when the view is ready to be displayed. Use this callback to set any listeners
         * or initialize any UI elements
         *
         * @param savedInstanceState Bundled passed to the activity to restore its state
         */
        fun onViewLoaded(savedInstanceState: Bundle?)
    }

    interface BasePresenter<V: BaseContract.BaseView<P>, P: BaseContract.BasePresenter<V, P>> {
        /**
         * Binds a view to this presenter
         *
         * @param view sets the view tied to the presenter
         */
        fun bindView(view: V)

        /**
         * Called after the activity called onCreate, or a framgnet calls onCreateView
         */
        fun onViewCreated()

        /**
         * Called after an activity calls onStart
         */
        fun onViewStarted()

        /**
         * Called after an activity calls onStopped
         */
        fun onViewStopped()

        /**
         * Called after an activity calls onDestroy
         */
        fun onViewDestroyed()

        /**
         * Useful for cleanup. Release any resources here
         */
        fun unbindView()

        /**
         * Used to save any attributes needed to restore current state
         *
         * @param outState bundle used to save state when view is destroyed by the system
         */
        fun onSaveInstanceState(outState: Bundle?)
    }
}
