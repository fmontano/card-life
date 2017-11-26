package com.p4u1.cardlife.ui.main

import android.os.Bundle
import com.p4u1.cardlife.ui.base.BaseContractPresenter

const val DEFAULT_LIFE_START = 20

class MainPresenter: BaseContractPresenter<MainContract.MainView, MainContract.MainPresenter>(), MainContract.MainPresenter {

    private var lifeStart: Int = DEFAULT_LIFE_START
    private var topLife: Int = lifeStart
    private var bottomLife: Int = lifeStart

    override fun initialize(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            lifeStart = savedInstanceState.getInt(EXTRA_STARTING_LIFE)
            topLife = savedInstanceState.getInt(EXTRA_TOP_PLAYER_LIFE)
            bottomLife = savedInstanceState.getInt(EXTRA_BOTTOM_PLAYER_LIFE)
            refreshPlayersLives()
        } else {
            reset()
        }
    }

    override fun reset(lifeStart: Int?) {
        if (lifeStart != null) {
            this.lifeStart = lifeStart
        }

        topLife = this.lifeStart
        bottomLife = this.lifeStart
        refreshPlayersLives()
    }

    private fun refreshPlayersLives() {
        view.setTopTotalLife(topLife)
        view.setBottomTotalLife(bottomLife)
    }

    override fun onTopPlusClick() {
        view.showSystemUI(false)
        view.setTopTotalLife(++topLife)
    }

    override fun onTopMinusClick() {
        view.showSystemUI(false)
        view.setTopTotalLife(--topLife)
    }

    override fun onBottomPlusClick() {
        view.showSystemUI(false)
        view.setBottomTotalLife(++bottomLife)
    }

    override fun onBottomMinusClick() {
        view.showSystemUI(false)
        view.setBottomTotalLife(--bottomLife)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.let {
            it.putInt(EXTRA_STARTING_LIFE, lifeStart)
            it.putInt(EXTRA_TOP_PLAYER_LIFE, topLife)
            it.putInt(EXTRA_BOTTOM_PLAYER_LIFE, bottomLife)
        }
    }
}
