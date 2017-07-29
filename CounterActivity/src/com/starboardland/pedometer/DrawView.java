package com.starboardland.pedometer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class DrawView extends View {
    Paint paint = new Paint();
    private int degreesToNorth;
    private int olddegree = 0;
    private int steps = 0;
	float x = 0;
	float y = 0;
	float x1 = 1;
	float y1 = 1;
	float x2 = 0;
	float y2 = 0;
	int flag = 0;
	int fg=0;
	 private float scaleFactor = 1.f;
	Bitmap backGroundCheck;

    public DrawView(Context context) {
        super(context);
        paint.setColor(Color.BLACK);
    }
    
    public void setDegreesToNorth(int deg) {
		this.degreesToNorth = deg;
	}
    
    public void setSteps(int stepcount) {
  		this.steps = stepcount;
  	}

    @Override
    public void onDraw(Canvas canvas) {
    	
    	super.onDraw(canvas);
    	Path path = new Path();
    	 paint.setStyle(Paint.Style.FILL);
         paint.setColor(Color.TRANSPARENT);
         canvas.drawPaint(paint);    
        
  	    
  	    if(x != x1)
        {
       	    x = canvas.getWidth()/2;
            y = canvas.getHeight()/2; 
       	 
        }
  	    
  	  
  	     x1 = (float)(50 * Math.cos(degreesToNorth));
	     y1 = (float)(50 * Math.sin(degreesToNorth));
  	
  	     x1 = x1+x;
	     y1 = y1+y;
           
            paint.setStrokeWidth(3);
            paint.setPathEffect(null);
          
            paint.setColor(Color.BLACK);	
           
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.scale(scaleFactor, scaleFactor);
            
            if(backGroundCheck == null){
            	backGroundCheck =  Bitmap.createBitmap (1, 1, Bitmap.Config.ARGB_8888);
            }
       
            
           canvas.drawCircle(x1, y1, 5, paint);
          //  canvas.drawLine(x, y, x1, y1, paint);
            
           // canvas.drawPath(path, paint);
            
            x = x1;
            y = y1;
            
           // olddegree = degreesToNorth;
       
           
    }

}