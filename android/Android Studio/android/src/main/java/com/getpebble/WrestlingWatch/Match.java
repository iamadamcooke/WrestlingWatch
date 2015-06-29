package com.getpebble.WrestlingWatch;

import android.content.Context;
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

    public Match(Context context, RelativeLayout matchLayout, String player1name, String player2name) {
        periodText = (TextView) matchLayout.findViewById(R.id.period_text);
        period = 1;
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
        player1 = new Player(context, (TextView) matchLayout.findViewById(R.id.player1_name), (TextView) matchLayout.findViewById(R.id.player1_score), player1name);
        player2 = new Player(context, (TextView) matchLayout.findViewById(R.id.player2_name), (TextView) matchLayout.findViewById(R.id.player2_score), player2name);


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

    public void player2Point(Point point) {
        player2.addPoint(point);
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



}
