package com.getpebble.WrestlingWatch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.getpebble.WrestlingWatch.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MatchSummary extends Activity {

    private TextView ref;
    private TextView wc;
    private TextView periods;
    private String player1Name;
    private int player1Score;
    private HashMap<String, String> player1Points;
    private String player2Name;
    private int player2Score;
    private HashMap<String, String> player2Points;
    private TextView player1;
    private TextView player2;
    private ScrollView player2sv;
    private ScrollView player1sv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_summary);
        Intent intent = getIntent();
        player1Name = intent.getStringExtra("1Name");
        player2Name = intent.getStringExtra("2Name");
        player1Score = intent.getIntExtra("1Score", 0);
        player2Score = intent.getIntExtra("2Score", 0);
        player1Points = (HashMap<String, String>) intent.getSerializableExtra("1Points");
        player2Points = (HashMap<String, String>) intent.getSerializableExtra("2Points");


        ref = (TextView) findViewById(R.id.ref);
        wc = (TextView) findViewById(R.id.wc);
        periods = (TextView) findViewById(R.id.periods);
        player1 = (TextView) findViewById(R.id.player1_final);
        player2 = (TextView) findViewById(R.id.player2_final);
        player1sv = (ScrollView) findViewById(R.id.player1_points);
        player2sv = (ScrollView) findViewById(R.id.player2_points);

        ref.setText("Referee: " + intent.getStringExtra("REF"));
        wc.setText("Weight Class: " + intent.getIntExtra("WC", 0));
        periods.setText("Periods: " + intent.getIntExtra("P", 0));
        player1.setText(player1Name + ": " + player1Score);
        player2.setText(player2Name + ": " + player2Score);

        RelativeLayout layout1 = new RelativeLayout(this);



        TextView periodText = new TextView(this);
        periodText.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        periodText.setText(player1Points.get("Periods"));
        periodText.setTextSize(23);
        periodText.setLayoutParams(params1);

        TextView pointText = new TextView(this);
        pointText.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.CENTER_HORIZONTAL);

        pointText.setText(player1Points.get("Points"));
        pointText.setTextSize(23);
        pointText.setLayoutParams(params2);

        TextView typeText = new TextView(this);
        typeText.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        typeText.setText(player1Points.get("Types"));
        typeText.setTextSize(23);

        typeText.setLayoutParams(params3);



        layout1.addView(periodText);
        layout1.addView(pointText);
        layout1.addView(typeText);
        player1sv.addView(layout1);


        RelativeLayout layout2 = new RelativeLayout(this);



        TextView periodText2 = new TextView(this);
        periodText2.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params4.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        periodText2.setText(player2Points.get("Periods"));
        periodText2.setTextSize(23);
        periodText2.setLayoutParams(params1);

        TextView pointText2 = new TextView(this);
        pointText2.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params5.addRule(RelativeLayout.CENTER_HORIZONTAL);

        pointText2.setText(player2Points.get("Points"));
        pointText2.setTextSize(23);
        pointText2.setLayoutParams(params2);

        TextView typeText2 = new TextView(this);
        typeText2.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams params6 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params6.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        typeText2.setText(player2Points.get("Types"));
        typeText2.setTextSize(23);

        typeText2.setLayoutParams(params3);



        layout2.addView(periodText2);
        layout2.addView(pointText2);
        layout2.addView(typeText2);
        player2sv.addView(layout2);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_match_summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new_match) {
            newMatch();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void newMatch() {

        //restart the app
        Intent i = getApplicationContext().getPackageManager()
                .getLaunchIntentForPackage(getApplicationContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }
}
