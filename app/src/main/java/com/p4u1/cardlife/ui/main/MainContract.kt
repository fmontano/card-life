package com.p4u1.cardlife.ui.main

import android.os.Bundle
import com.p4u1.cardlife.ui.base.BaseContract

class MainContract {

    interface MainPresenter : BaseContract.BasePresenter<MainView, MainPresenter> {
        /**
         * Resets total life
         *
         * @param lifeStart new total life. Defaults to [DEFAULT_LIFE_START]
         */
        fun reset(lifeStart: Int? = null)

        /**
         * Adds a life to player on top
         */
        fun onTopPlusClick()

        /**
         * Removes a life from player on top
         */
        fun onTopMinusClick()

        /**
         * Adds a life to player on bottom
         */
        fun onBottomPlusClick()

        /**
         * Removes a life from player on bottom
         */
        fun onBottomMinusClick()

        /**
         * Initializes the presenter. If savedInstanceState is not null, it will load the values
         * from it to restore previous state of the app before it was destroyed
         *
         * @param savedInstanceState previous state of the app
         */
        fun initialize(savedInstanceState: Bundle?)
    }

    interface MainView : BaseContract.BaseView<MainPresenter> {

        /**
         * Sets new total life of top player
         *
         * @param totalLife new total life for player
         */
        fun setTopTotalLife(totalLife: Int)

        /**
         * Sets new total life of bottom player
         *
         * @param totalLife new total life for player
         */
        fun setBottomTotalLife(totalLife: Int)

        /**
         * Used to show or hide the system UI (status bar, action bar, and bottom navigation)
         *
         * @param show True to show it, false to hide it
         */
        fun showSystemUI(show: Boolean)
    }
}
