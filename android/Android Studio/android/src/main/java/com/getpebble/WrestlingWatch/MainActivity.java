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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        actionBar.hide();
		setContentView(R.layout.activity_main);


		

		matchLayout = (RelativeLayout) findViewById(R.id.match_layout);
        match = new Match(this, matchLayout, "Adam", "Michael");



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

}
