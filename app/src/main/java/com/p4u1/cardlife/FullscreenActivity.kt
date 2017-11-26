package com.p4u1.cardlife

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.*
import kotlinx.android.synthetic.main.activity_fullscreen.*
import android.content.Intent
import com.p4u1.cardlife.R.id.fullscreen_content
import android.app.Activity




/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {
    private val mHideHandler = Handler()

    var startLife = 20;

    var topLife = startLife;
    var bottomLife = startLife;
    var topColor = 0;
    var bottomColor = 0;

    private val mShowPart2Runnable = Runnable {
        // Delayed display of UI elements
        supportActionBar?.show()
        fullscreen_content_controls.visibility = View.VISIBLE
    }


    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private val mDelayHideTouchListener = View.OnTouchListener { _, _ ->

        false
    }

    private val mTopLifeTouchListener = View.OnTouchListener { _, _ ->
        topLife++;
        top_life_label.text = topLife.toString()
        false
    }

    private val mBottomLifeTouchListener = View.OnTouchListener { _, _ ->
        bottomLife++;
        bottom_life_label.text = bottomLife.toString()
        hide()
        false
    }

    private val mTopDeathTouchListener = View.OnTouchListener { _, _ ->
        topLife--;
        top_life_label.text = topLife.toString()
        hide()
        false
    }


    private fun toggle(){
        if (mVisible){
            hide()
        } else {
            show()
        }
    }
    private val mBottomDeathTouchListener = View.OnTouchListener { _, _ ->
        bottomLife--;
        bottom_life_label.text = bottomLife.toString()
        hide()
        false
    }

    private val mMiddleTouchListener = View.OnTouchListener { _, _ ->
        toggle()
        false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.card_life_menu, menu)
        hide()
        return true
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                hide()
            }
            R.id.totals -> {

            }
            R.id.reset -> {
                reset()
            }
            R.id.start_forty -> {
                setStartandReset(40)
            }
            R.id.start_twenty -> {
                setStartandReset(20)
            }
            R.id.start_ten -> {
                setStartandReset(10)
            }
            R.id.start_zero -> {
                setStartandReset(0)
            }
            R.id.colors -> {
                val i = Intent(this, SelectColorActivity::class.java)
                i.putExtra("topColor", topColor)
                i.putExtra("bottomColor", bottomColor)
                startActivityForResult(i, 1)
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

    fun setStartandReset(life : Int) {
        startLife = life
        reset()
    }

    fun reset() {
        topLife = startLife
        bottomLife = startLife
        top_life_label.text = topLife.toString()
        bottom_life_label.text = bottomLife.toString()
        hide()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("")

        mVisible = true
        hide()
        // Set up the user interaction to manually show or hide the system UI.
        //fullscreen_content.setOnClickListener { toggle() }

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        top_plus.setOnTouchListener(mTopLifeTouchListener)
        bottom_plus.setOnTouchListener(mBottomLifeTouchListener)
        top_minus.setOnTouchListener(mTopDeathTouchListener)
        bottom_minus.setOnTouchListener(mBottomDeathTouchListener)
        middle.setOnTouchListener(mMiddleTouchListener)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

    }

    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
        fullscreen_content_controls.visibility = View.GONE
        mVisible = false
    }
    private fun show() {
        supportActionBar?.show()
        mVisible = true;
    }

    override fun onActivityResult(requestCode: Int, resultCode : Int, data :Intent) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                topColor = data.getIntExtra("topColor", 0)
                bottomColor = data.getIntExtra("bottomColor", 0)
                top_life_label.setBackgroundColor(SelectColorActivity.getColors().get(topColor))
                bottom_life_label.setBackgroundColor(SelectColorActivity.getColors().get(bottomColor))
                hide()
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 300
    }
}
