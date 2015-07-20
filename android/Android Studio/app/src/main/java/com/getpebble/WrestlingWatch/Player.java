package com.getpebble.WrestlingWatch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adamcooke on 6/28/15.
 */
public class Player implements Serializable {

    private String name;
    private TextView nameText;
    private TextView scoreText;
    private int score = 0;
    private Context context;
    private ArrayList<Point> points;
    private Club club;

    public Player(Context context, TextView nameText, TextView scoreText, String name, Club club) {
        this.name = name;
        this.nameText = nameText;
        this.scoreText = scoreText;
        this.context = context;
        this.club = club;
        scoreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewPointsViewDialog();
            }
        });
        this.nameText.setText(name);
        points = new ArrayList<Point>();
    }

    public void addPoint(Point point) {
        score += point.getValue();
        scoreText.setText("" + score);
        points.add(point);

    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void undoPoint() {
        if(points.size() > 0) {
            score -=  points.get(points.size()-1).getValue();
            scoreText.setText("" + score);
            points.remove(points.size() - 1);
        }

    }




    public void openNewPointsViewDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK);



        // Setting Dialog Title
        builder.setTitle("Points for  " + name);

        RelativeLayout layout = new RelativeLayout(context);


        Map<String, String> textMap = getPointsAsString();

        TextView periodText = new TextView(context);
        periodText.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        periodText.setText(textMap.get("Periods"));
        periodText.setTextSize(23);
        periodText.setLayoutParams(params1);

        TextView pointText = new TextView(context);
        pointText.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.CENTER_HORIZONTAL);

        pointText.setText(textMap.get("Points"));
        pointText.setTextSize(23);
        pointText.setLayoutParams(params2);

        TextView typeText = new TextView(context);
        typeText.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        typeText.setText(textMap.get("Types"));
        typeText.setTextSize(23);

        typeText.setLayoutParams(params3);

        ScrollView sv = new ScrollView(context);


        layout.addView(periodText);
        layout.addView(pointText);
        layout.addView(typeText);
        sv.addView(layout);
        builder.setView(sv);



        // Setting Negative "NO" Button
        builder.setNegativeButton("Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        // closed

        // Showing Alert Message
        AlertDialog dialog = builder.show();
        //TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        //messageText.setGravity(Gravity.CENTER);
        dialog.show();


    }

    public HashMap<String,String> getPointsAsString() {
        HashMap<String,String> textMap = new HashMap<String, String>();

        String periodText = "Period\n";
        String pointText = "Points\n";
        String typeText = "Type\n";

        for(Point point: points){
            periodText = periodText + "  " + point.getPeriod() + "\n";
            pointText = pointText + " " + point.getValue() + "\n";
            typeText = typeText + point.getName() + "\n";

        }

        textMap.put("Periods", periodText);
        textMap.put("Points", pointText);
        textMap.put("Types", typeText);

        return textMap;
    }
}
