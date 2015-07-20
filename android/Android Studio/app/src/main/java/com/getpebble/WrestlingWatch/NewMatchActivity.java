package com.getpebble.WrestlingWatch;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.getpebble.WrestlingWatch.R;

import java.util.ArrayList;
import java.util.Map;

public class NewMatchActivity extends Activity {

    private Button newMatchButton;
    private Spinner wrestler1club;
    private Spinner wrestler2club;
    private Spinner weightClasses;
    private ArrayList<Club> clubs;
    private String[] weights = {"106", "113", "120", "126", "132", "138", "145", "152", "160", "170", "182", "195", "220", "285"};
    private ArrayAdapter<Club> adapter;
    private ArrayAdapter<String> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("New Match");

        //get array of stuff
        clubs = new ArrayList<Club>();
        clubs.add(new Club("Club 1"));
        clubs.add(new Club("Club 2"));
        clubs.add(new Club("Club 3"));

        wrestler1club = (Spinner) findViewById(R.id.wrestler1_club);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = new ArrayAdapter<Club>(this,
                R.layout.spinner_text, clubs);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        wrestler1club.setAdapter(adapter);


        wrestler2club = (Spinner) findViewById(R.id.wrestler2_club);

        wrestler2club.setAdapter(adapter);

        weightClasses = (Spinner) findViewById(R.id.weight_classes);
        adapter2 = new ArrayAdapter<String>(this,
                R.layout.spinner_text, weights);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightClasses.setAdapter(adapter2);

        newMatchButton = (Button) findViewById(R.id.new_match_button);

        newMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                EditText w1 = (EditText) findViewById(R.id.wrestler1_name);
                EditText w2 = (EditText) findViewById(R.id.wrestler2_name);
                EditText ref = (EditText) findViewById(R.id.referee_name);
                Club club1 = (Club) wrestler1club.getSelectedItem();
                Club club2 = (Club) wrestler2club.getSelectedItem();
                int weightClass = Integer.parseInt(weightClasses.getSelectedItem().toString());
                intent.putExtra("W1", w1.getText().toString());
                intent.putExtra("W2", w2.getText().toString());
                intent.putExtra("REF", ref.getText().toString());
                intent.putExtra("Club1", club1);
                intent.putExtra("Club2", club2);
                intent.putExtra("WC", weightClass);

                startActivity(intent);

            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_new_match, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_club:
                openNewClubDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addClub(String clubName, String clubCity) {
        clubs.add(new Club(clubName, clubCity));
        adapter.notifyDataSetChanged();
    }

    public void openNewClubDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);



        // Setting Dialog Title
        builder.setTitle("Add Club");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText name= new EditText(this);
        name.setHint("Club Name");
        name.setTextSize(30);



        final EditText city= new EditText(this);
        city.setHint("City");
        city.setTextSize(30);





        layout.addView(name);
        layout.addView(city);
        builder.setView(layout);



        // Setting Negative "NO" Button
        builder.setNegativeButton("Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        // Setting Negative "NO" Button
        builder.setPositiveButton("Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        addClub(name.getText().toString(), city.getText().toString());
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

}
