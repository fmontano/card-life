package com.p4u1.cardlife.ui.selectcolor

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.p4u1.cardlife.R
import kotlinx.android.synthetic.main.activity_select_color.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SelectColorActivity : AppCompatActivity() {
    private val mHideHandler = Handler()

    var startLife = 20;

    var topLife = startLife;
    var bottomLife = startLife;
    val colors = getColors()
    var topColorIndex : Int = 0
    var bottomColorIndex : Int = 0

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
        nextColor()
        false
    }

    private val mBottomLifeTouchListener = View.OnTouchListener { _, _ ->
        nextColorBottom()
        false
    }

    private val mTopDeathTouchListener = View.OnTouchListener { _, _ ->
        prevColor()
        false
    }

    private fun nextColor() {
        topColorIndex++;
        if(topColorIndex>=colors.size){
            topColorIndex = 0
        }
        setColors()
    }
    private fun prevColor() {
        topColorIndex--;
        if(topColorIndex<0){
            topColorIndex = colors.size - 1
        }
        setColors()
    }

    private fun nextColorBottom() {
        bottomColorIndex++;
        if(bottomColorIndex>=colors.size){
            bottomColorIndex = 0
        }
        setColors()
    }
    private fun prevColorBottom() {
        bottomColorIndex--;
        if(bottomColorIndex<0){
            bottomColorIndex = colors.size - 1
        }
        setColors()
    }

    private fun setColors() {
        top_life_label.setBackgroundColor(colors.get(topColorIndex))
        bottom_life_label.setBackgroundColor(colors.get(bottomColorIndex))
    }


    private fun toggle(){
        if (mVisible){
            hide()
        } else {
            show()
        }
    }

    private fun done() {
        val returnIntent = Intent()
        returnIntent.putExtra("topColor", topColorIndex)
        returnIntent.putExtra("bottomColor", bottomColorIndex)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }


    private val mBottomDeathTouchListener = View.OnTouchListener { _, _ ->
        prevColorBottom()
        false
    }

    private val mMiddleTouchListener = View.OnTouchListener { _, _ ->
        done()
        false
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                done()
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

        setContentView(R.layout.activity_select_color)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Back")
        topColorIndex = intent.getIntExtra("topColor", 0)
        bottomColorIndex = intent.getIntExtra("bottomColor", 0)
        setColors()

        mVisible = true
       top_life_label.text = "01"
        bottom_life_label.text = "01"
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
    }
    private fun show() {
        supportActionBar?.show()
        mVisible = true;
    }

    val colorSet : HashMap<String, Color> = hashMapOf()

    companion object {
        fun getColors() : MutableList<Int> {
            var result = mutableListOf<Int>()
            result.add(Color.BLACK)
            result.add(Color.WHITE)
            result.add(Color.BLUE)
            result.add(Color.CYAN)
            result.add(Color.DKGRAY)
            result.add(Color.GRAY)
            result.add(Color.GREEN)
            result.add(Color.LTGRAY)
            result.add(Color.MAGENTA)
            result.add(Color.RED)
            result.add(Color.YELLOW)
            return result
        }
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