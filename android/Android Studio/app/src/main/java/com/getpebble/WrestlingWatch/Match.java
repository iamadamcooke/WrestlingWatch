package com.getpebble.WrestlingWatch;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by adamcooke on 6/28/15.
 */
public class Match implements EndPeriod{

    private CountDownTimerPausable clock;
    private int period;
    private TextView periodText;
    private Player player1;
    private Player player2;
    private Context context;
    private RelativeLayout matchLayout;
    private String refName;
    private int weightClass;

    public Match(Context context, RelativeLayout matchLayout, String player1name, String player2name, Club club1, Club club2, String refName, int weightClass) {
        periodText = (TextView) matchLayout.findViewById(R.id.period_text);
        this.context = context;
        period = 1;
        this.refName = refName;
        weightClass = weightClass;
        this.matchLayout = matchLayout;
        TextView timeText = (TextView) matchLayout.findViewById(R.id.time_text);
        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clock.isPaused()) {
                    clock.start();
                }
                else {
                    clock.pause();
                }
            }
        });
        clock = new CountDownTimerPausable(context,  timeText);
        clock.addPeriodCallback(this);
        player1 = new Player(context, (TextView) matchLayout.findViewById(R.id.player1_name), (TextView) matchLayout.findViewById(R.id.player1_score), player1name, club1);
        player2 = new Player(context, (TextView) matchLayout.findViewById(R.id.player2_name), (TextView) matchLayout.findViewById(R.id.player2_score), player2name, club2);


    }


    public void startMatch() {
        clock.start();
    }

    public void pauseMatch() {
        clock.pause();
    }

    public boolean isPaused() {
        return clock.isPaused();
    }

    public void player1Point(Point point) {
        player1.addPoint(point);
    }

    public void player1UndoPoint() {
        player1.undoPoint();
    }

    public void player2UndoPoint() {
        player2.undoPoint();
    }

    public void player2Point(Point point) {
        player2.addPoint(point);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void endPeriod() {
        if(period < 3) {
            period++;
            periodText.setText("Period " + period);
            clock.reset();
        }
    }

    public int getPeriod() {
        return this.period;
    }

    public boolean endMatch() {
        if(!clock.isPaused()) {
            clock.pause();
        }


        //save old match stuff here

        //save successful
        return true;
    }



}
