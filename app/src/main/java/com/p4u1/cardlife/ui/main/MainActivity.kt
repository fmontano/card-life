package com.p4u1.cardlife.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.view.*
import com.p4u1.cardlife.R
import com.p4u1.cardlife.ui.base.BaseActivity
import com.p4u1.cardlife.ui.selectcolor.SelectColorActivity
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_STARTING_LIFE = "extraStartingLife"
const val EXTRA_TOP_PLAYER_LIFE = "extraTopPlayerLife"
const val EXTRA_BOTTOM_PLAYER_LIFE = "extraBottomPlayerLife"

class MainActivity : BaseActivity<MainContract.MainPresenter, MainContract.MainView>(), MainContract.MainView, View.OnSystemUiVisibilityChangeListener {

    private var topColor = 0
    private var bottomColor = 0
    private lateinit var gestureDetector: GestureDetectorCompat

    override fun getViewResourceId() = R.layout.activity_main

    override fun createPresenter(): MainContract.MainPresenter = MainPresenter()

    override fun onViewLoaded(savedInstanceState: Bundle?) {
        gestureDetector = GestureDetectorCompat(this, TapGestureDetector())

        window.decorView.setOnSystemUiVisibilityChangeListener(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.app_name)

        top_plus.setOnClickListener { presenter.onTopPlusClick() }
        bottom_plus.setOnClickListener { presenter.onBottomPlusClick() }
        top_minus.setOnClickListener { presenter.onTopMinusClick() }
        bottom_minus.setOnClickListener { presenter.onBottomMinusClick() }

        presenter.initialize(savedInstanceState)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            showSystemUI(false)
        } else {
            showSystemUI(true)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.card_life_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> showSystemUI(false)
            R.id.totals -> { }
            R.id.reset -> presenter.reset()
            R.id.start_forty -> { presenter.reset(40) }
            R.id.start_twenty -> presenter.reset(20)
            R.id.start_ten -> presenter.reset(10)
            R.id.start_zero -> presenter.reset(0)
            R.id.colors -> showSelectColorActivity()
        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun setTopTotalLife(totalLife: Int) {
        top_life_label.text = totalLife.toString()
    }

    override fun setBottomTotalLife(totalLife: Int) {
        bottom_life_label.text = totalLife.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode : Int, data :Intent?) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    topColor = it.getIntExtra("topColor", 0)
                    bottomColor = it.getIntExtra("bottomColor", 0)
                    top_life_label.setBackgroundColor(SelectColorActivity.getColors()[topColor])
                    bottom_life_label.setBackgroundColor(SelectColorActivity.getColors()[bottomColor])
                    showSystemUI(false)
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    override fun onSystemUiVisibilityChange(visibility: Int) {
        if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
            supportActionBar?.show()
        } else {
            supportActionBar?.hide()
        }
    }

    override fun showSystemUI(show: Boolean) {
        if (show) {
            window.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        } else {
            window.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    or View.SYSTEM_UI_FLAG_IMMERSIVE)
        }
    }

    private fun showSelectColorActivity() {
        val i = Intent(this, SelectColorActivity::class.java)
        i.putExtra("topColor", topColor)
        i.putExtra("bottomColor", bottomColor)
        startActivityForResult(i, 1)
    }

    inner class TapGestureDetector: GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            showSystemUI(false)
            return true
        }
    }
}
