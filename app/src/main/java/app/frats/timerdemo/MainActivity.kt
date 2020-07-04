package app.frats.timerdemo

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var timerObj:Timer? = null
    private val timerHandler: Handler = Handler()
    private var secondsRemaining:Int = 0
    private var totalMin:Int = 0
    private var isClassStart:Boolean = false
    private var totalMinAttended:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startTimerButton.setOnClickListener {
            onTimerStart()
        }
    }

    override fun onPause() {
        super.onPause()
        if (isClassStart) {
            onTimerStop()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isClassStart) {
            onTimerStart()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isClassStart = false
        totalMin = 0
    }

    private fun onTimerStart() {
        isClassStart = true
        totalMinAttended = 0
        timerObj = Timer()
        val timerTaskObj: TimerTask = object : TimerTask() {
            override fun run() {

                timerHandler.post(Runnable {
                    if (secondsRemaining <= 59) {
                        secondsRemaining += 1
                    } else {
                        secondsRemaining = 0
                        totalMin += 1
                        totalMinAttended += 1
                    }

                    timerTextView.text = "$totalMin:$secondsRemaining".toString()
                    //Log.d("TotalMin=", totalMin.toString())
                    Log.d("TotalMinAttended=", totalMinAttended.toString())
                })
            }
        }
        timerObj!!.schedule(timerTaskObj, 0, 1000)
    }

    private fun onTimerStop(){
        if(timerObj != null){
            timerObj!!.cancel();
            timerObj!!.purge();
        }
    }





}