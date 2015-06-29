package com.getpebble.WrestlingWatch;

/**
 * Created by adamcooke on 6/22/15.
 */

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

/**
 * This class uses the native CountDownTimer to
 * create a timer which could be paused and then
 * started again from the previous point. You can
 * provide implementation for onTick() and onFinish()
 * then use it in your projects.
 */
public class CountDownTimerPausable {
    private long millisInFuture = 0;
    private long countDownInterval = 0;
    private long millisRemaining =  0;
    private static long DEFAULT_TIME = 120000;
    private static long DEFAULT_INTERVAL = 10;

    private EndPeriod period;

    private CountDownTimer countDownTimer = null;

    private boolean isPaused = true;

    private TextView timeText;
    private static String DEFAULT_TIME_TEXT = "02:00";
    private Context context;

    public CountDownTimerPausable(Context context, TextView timeText, long millisInFuture, long countDownInterval) {
        super();
        this.millisInFuture = millisInFuture;
        this.countDownInterval = countDownInterval;
        this.millisRemaining = this.millisInFuture;
        this.timeText = timeText;
        this.timeText.setText(DEFAULT_TIME_TEXT);
        this.context = context;


    }

    public void addPeriodCallback(EndPeriod period) {
        this.period = period;
    }

    public CountDownTimerPausable(Context context, TextView timeText) {
        this(context, timeText, DEFAULT_TIME, DEFAULT_INTERVAL);
    }

    private void createCountDownTimer(){
        countDownTimer = new CountDownTimer(millisRemaining,countDownInterval) {

            @Override
            public void onTick(long millisUntilFinished) {
                millisRemaining = millisUntilFinished;
                CountDownTimerPausable.this.onTick(millisUntilFinished);

            }

            @Override
            public void onFinish() {
                CountDownTimerPausable.this.onFinish();

            }
        };
    }
    /**
     * Callback fired on regular interval.
     *
     * @param millisUntilFinished The amount of time until finished.
     */
    public void onTick(long millisUntilFinished) {
            timeText.setText(TimeHelpers.millisToStringTime(millisUntilFinished));
        if(millisUntilFinished < 6000 && millisUntilFinished >= 1000) {
            // Send COUNTDOWN to Pebble
            PebbleDictionary out = new PebbleDictionary();
            int seconds = (int) (millisUntilFinished / 1000);
            out.addString(PebbleInfo.COUNTDOWN, ""+seconds);
            PebbleKit.sendDataToPebble(context, PebbleInfo.WATCHAPP_UUID, out);
        }

    }
    /**
     * Callback fired when the time is up.
     */
    public void onFinish() {
            //timeText.setText("End Period!");
        // Send KEY_VIBRATE to Pebble
        PebbleDictionary out = new PebbleDictionary();
        out.addInt32(PebbleInfo.KEY_VIBRATE, 0);
        PebbleKit.sendDataToPebble(context, PebbleInfo.WATCHAPP_UUID, out);
        isPaused = true;
        period.endPeriod();
    }
    /**
     * Cancel the countdown.
     */
    public final void cancel(){
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
        this.millisRemaining = 0;
    }
    /**
     * Start or Resume the countdown.
     * @return CountDownTimerPausable current instance
     */
    public synchronized final CountDownTimerPausable start(){
        createCountDownTimer();
        if(isPaused){
            countDownTimer.start();
            isPaused = false;
        }
        return this;
    }
    /**
     * Pauses the CountDownTimerPausable, so it could be resumed(start)
     * later from the same point where it was paused.
     */
    public void pause()throws IllegalStateException{
        if(isPaused==false){
            countDownTimer.cancel();
        } else{
            throw new IllegalStateException("CountDownTimerPausable is already in pause state, start counter before pausing it.");
        }
        isPaused = true;
    }
    public boolean isPaused() {
        return isPaused;
    }

    public void reset() {
        this.millisRemaining = DEFAULT_TIME;
        this.countDownInterval = DEFAULT_INTERVAL;
        timeText.setText(DEFAULT_TIME_TEXT);
    }


}
