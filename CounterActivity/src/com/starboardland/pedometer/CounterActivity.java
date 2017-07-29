package com.starboardland.pedometer;


import com.starboardland.pedometer.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.*;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;




public class CounterActivity extends Activity implements SensorEventListener {

    private static final String MY_PREFS_NAME = null;
	private SensorManager sensorManager;
    private TextView count;
    boolean activityRunning;
   
    private DrawView drawView;
    private Button button;
    private TextView mText;
	private SensorManager mSensorManager;
    private float mCurrentDegree = 0f;
    private int stepsInSensor = 0;
    private int stepsAtReset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE); 
        stepsAtReset = prefs.getInt("stepsAtReset", 0);
        count = (TextView) findViewById(R.id.count);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
 
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		
	    mText = (TextView) findViewById(R.id.mText);
	    
	 
	    
	    
	    
  	  drawView = new DrawView(this);
      drawView.setBackgroundColor(Color.WHITE);
    
	  
	    
    }

    
    
    
    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME);
     
                
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
        

    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting step events
//        sensorManager.unregisterListener(this); 
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    	
    	if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) 
        {
    	

            float degree = Math.round(event.values[0]);
            
            int deg = -(Math.round(event.values[0]));
                     
            mText.setText("North: " + Float.toString(degree) + " degrees");
     
            // create a rotation animation (reverse turn degree degrees)
            RotateAnimation ra = new RotateAnimation(
            		mCurrentDegree, 
                    -degree,
                    Animation.RELATIVE_TO_SELF, 0.5f, 
                    Animation.RELATIVE_TO_SELF,
                    0.5f);
     
            // how long the animation will take place
            ra.setDuration(210);
     
            // set the animation after the end of the reservation status
            ra.setFillAfter(true);
     
            // Start the animation
            mCurrentDegree = -degree;
            drawView.setDegreesToNorth(deg);
            
        }
    	else
    	{
        
    		 if (activityRunning) {
    			 
    			    int step = Math.round(event.values[0]);
    			    drawView.setSteps(step);
    	            count.setText(String.valueOf(Math.round(event.values[0])));
    	            setContentView(drawView);
    	        }
    		 else{
    		        event.values[0] = 0;
    		    }
        
    	}
    	
    	


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    	// TODO Auto-generated method stub
    }
}
