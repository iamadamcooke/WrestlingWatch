package com.getpebble.WrestlingWatch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.PebbleKit.PebbleDataReceiver;
import com.getpebble.android.kit.util.PebbleDictionary;

public class MainActivity extends Activity {
	

	

	
	private Handler handler = new Handler();
	private PebbleDataReceiver appMessageReciever;
	private Match match;
    private RelativeLayout matchLayout;
    private View decorView;
    private String player1Name;
    private String player2Name;
    private String refName;
    private int weightClass;
    private Club club1;
    private Club club2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //decorView = getWindow().getDecorView();
        // Hide the status bar.
        //int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        //decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        Intent intent = getIntent();
        player1Name = intent.getStringExtra("W1");
        player2Name = intent.getStringExtra("W2");
        refName = intent.getStringExtra("REF");
        weightClass = intent.getIntExtra("WC", 0);
        club1 = (Club) intent.getSerializableExtra("Club1");
        club2 = (Club) intent.getSerializableExtra("Club2");
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Match");
        //actionBar.hide();
		setContentView(R.layout.activity_main);


		

		matchLayout = (RelativeLayout) findViewById(R.id.match_layout);
        match = new Match(this, matchLayout, player1Name, player2Name, club1, club2, refName, weightClass);



        setupButtons();


	}


    public void setupButtons() {

        //player 1
        Button player1_t = (Button) findViewById(R.id.player1_t);
        player1_t.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                match.player1Point(new Takedown(match.getPeriod()));
            }
        });

        Button player1_nf = (Button) findViewById(R.id.player1_nf);
        player1_nf.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                match.player1Point(new NearFall(match.getPeriod()));
            }
        });

        Button player1_e = (Button) findViewById(R.id.player1_e);
        player1_e.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                match.player1Point(new Escape(match.getPeriod()));
            }
        });

        Button player1_r = (Button) findViewById(R.id.player1_r);
        player1_r.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                match.player1Point(new Reversal(match.getPeriod()));
            }
        });

        Button player1_undo = (Button) findViewById(R.id.player1_undo);
        player1_undo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                match.player1UndoPoint();
            }
        });

        //player2
        Button player2_t = (Button) findViewById(R.id.player2_t);
        player2_t.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                match.player2Point(new Takedown(match.getPeriod()));
            }
        });

        Button player2_nf = (Button) findViewById(R.id.player2_nf);
        player2_nf.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                match.player2Point(new NearFall(match.getPeriod()));
            }
        });

        Button player2_e = (Button) findViewById(R.id.player2_e);
        player2_e.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                match.player2Point(new Escape(match.getPeriod()));
            }
        });

        Button player2_r = (Button) findViewById(R.id.player2_r);
        player2_r.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                match.player2Point(new Reversal(match.getPeriod()));
            }
        });

        Button player2_undo = (Button) findViewById(R.id.player2_undo);
        player2_undo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                match.player2UndoPoint();
            }
        });

    }

	@Override
	protected void onResume() {
		super.onResume();
		
		// Define AppMessage behavior
		if(appMessageReciever == null) {
			appMessageReciever = new PebbleDataReceiver(PebbleInfo.WATCHAPP_UUID) {
				
				@Override
				public void receiveData(Context context, int transactionId, PebbleDictionary data) {
					// Always ACK
					PebbleKit.sendAckToPebble(context, transactionId);
					
					// What message was received?
					if(data.getInteger(PebbleInfo.KEY_BUTTON) != null) {
						// KEY_BUTTON was received, determine which button
						final int button = data.getInteger(PebbleInfo.KEY_BUTTON).intValue();
						
						// Update UI on correct thread
						handler.post(new Runnable() {
							
							@Override
							public void run() {
								switch(button) {
								case PebbleInfo.BUTTON_UP:
									//whichButtonView.setText("UP");
									break;
								case PebbleInfo.BUTTON_SELECT:
									if(match.isPaused()) {
                                       match.startMatch();
                                    }
                                    else {
                                        match.pauseMatch();
                                    }
									break;
								case PebbleInfo.BUTTON_DOWN:
									//whichButtonView.setText("DOWN");
									break;
								default:
									Toast.makeText(getApplicationContext(), "Unknown button: " + button, Toast.LENGTH_SHORT).show();
									break;
								}
							}
							
						});
					} 
				}
			};
		
			// Add AppMessage capabilities
			PebbleKit.registerReceivedDataHandler(this, appMessageReciever);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		// Unregister AppMessage reception
		if(appMessageReciever != null) {
			unregisterReceiver(appMessageReciever);
			appMessageReciever = null;
		}
	}
	
	/**
     * Alternative sideloading method
     * Source: http://forums.getpebble.com/discussion/comment/103733/#Comment_103733 
     */
    public static void sideloadInstall(Context ctx, String assetFilename) {
        try {
            // Read .pbw from assets/
        	Intent intent = new Intent(Intent.ACTION_VIEW);    
            File file = new File(ctx.getExternalFilesDir(null), assetFilename);
            InputStream is = ctx.getResources().getAssets().open(assetFilename);
            OutputStream os = new FileOutputStream(file);
            byte[] pbw = new byte[is.available()];
            is.read(pbw);
            os.write(pbw);
            is.close();
            os.close();
             
            // Install via Pebble Android app
            intent.setDataAndType(Uri.fromFile(file), "application/pbw");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        } catch (IOException e) {
            Toast.makeText(ctx, "App install failed: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_end_match:
                endMatch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void endMatch() {
        match.endMatch();
        Intent intent = new Intent(getApplicationContext(), MatchSummary.class);
        intent.putExtra("REF", refName);
        intent.putExtra("P", match.getPeriod());
        intent.putExtra("WC", weightClass);
        intent.putExtra("1Name", match.getPlayer1().getName());
        intent.putExtra("2Name", match.getPlayer2().getName());
        intent.putExtra("1Score", match.getPlayer1().getScore());
        intent.putExtra("2Score", match.getPlayer2().getScore());
        intent.putExtra("1Points", match.getPlayer1().getPointsAsString());
        intent.putExtra("2Points", match.getPlayer2().getPointsAsString());
        startActivity(intent);


    }

}
