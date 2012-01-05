package edu.pernat.shopinglist.android.maps;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MyPositionOverlay extends Overlay {


	private GeoPoint gp1; 
	private GeoPoint gp2; 
	private int mRadius=6; 
	private int mode=0; 
	private int defaultColor; 

	private Bitmap img = null; 
	private String podatki="";
	
	public MyPositionOverlay(GeoPoint gp1,GeoPoint gp2,int mode) // GeoPoint is a int. (6E) 
	{ 
		this.gp1 = gp1; 
		this.gp2 = gp2; 
		this.mode = mode; 
		defaultColor = 999; // no defaultColor 

	} 
	public MyPositionOverlay(GeoPoint gp1,GeoPoint gp2,int mode,String podatk) // GeoPoint is a int. (6E) 
	{ 
		this.gp1 = gp1; 
		this.gp2 = gp2; 
		this.mode = mode; 
		defaultColor = 999; // no defaultColor 
		this.podatki=podatk;
	} 
	public void setPodatki(String p)
	{
		podatki=p;
	}

	public MyPositionOverlay(GeoPoint gp1,GeoPoint gp2,int mode, int defaultColor) 
	{ 
		this.gp1 = gp1; 
		this.gp2 = gp2; 
		this.mode = mode; 
		this.defaultColor = defaultColor; 
	} 
	public void setBitmap(Bitmap bitmap) 
	{ 
		this.img = bitmap; 
	} 
	public int getMode() 
	{ 
		return mode; 
	} 

	@Override 
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) 
	{ 
		Projection projection = mapView.getProjection(); 
		if (shadow == false) 
		{ 
			Paint paint = new Paint(); 
			paint.setAntiAlias(true); 
			Point point = new Point(); 
			projection.toPixels(gp1, point); 
			// mode=1&#65306;start 
			if(mode==1) 
			{ 
				if(defaultColor==999) 
				paint.setColor(Color.BLUE); 
				else 
				paint.setColor(defaultColor); 			
				RectF oval=new RectF(point.x - mRadius, point.y - mRadius, 
				point.x + mRadius, point.y + mRadius); 
				// start point 
				canvas.drawOval(oval, paint); 
			}
			// mode=2&#65306;path 
			else if(mode==2) 
			{ 
				if(defaultColor==999) 
				paint.setColor(Color.RED); 
				else 
				paint.setColor(defaultColor); 
				
				Point point2 = new Point(); 
				projection.toPixels(gp2, point2); 
				paint.setStrokeWidth(5); 
				paint.setAlpha(120); 
				canvas.drawLine(point.x, point.y, point2.x,point2.y, paint); 
			} 
			/* mode=3&#65306;end */ 
			else if(mode==3) 
			{ 
				/* the last path */ 
				if(defaultColor==999) paint.setColor(Color.MAGENTA); 
				else 
				paint.setColor(defaultColor); 
				Point point2 = new Point(); 
				projection.toPixels(gp2, point2); 
				paint.setStrokeWidth(5); 
				paint.setAlpha(120); 
				canvas.drawLine(point.x, point.y, point2.x,point2.y, paint); 
				RectF oval=new RectF(point2.x - mRadius,point2.y - mRadius, 
				point2.x + mRadius,point2.y + mRadius); 
				/* end point */ 
				paint.setAlpha(255); 
				canvas.drawOval(oval, paint); 
				
				
				RectF rec=new RectF(5, 10, 390, 35);
				paint.setColor(Color.TRANSPARENT);
				canvas.drawRect(rec, paint);
				
				Paint strokePaint = new Paint();
				strokePaint.setARGB(255, 0, 0, 0);
				strokePaint.setTextAlign(Paint.Align.CENTER);
				strokePaint.setTextSize(20);
				strokePaint.setTypeface(Typeface.DEFAULT_BOLD);
				strokePaint.setStyle(Paint.Style.STROKE);
				strokePaint.setStrokeWidth(2);

				Paint textPaint = new Paint();
				textPaint.setARGB(255, 255, 0, 0);
				textPaint.setTextAlign(Paint.Align.CENTER);
				textPaint.setTextSize(20);
				textPaint.setTypeface(Typeface.DEFAULT_BOLD);
				canvas.drawColor(Color.TRANSPARENT);
				
				
				canvas.drawText(podatki, 195, 30, strokePaint);
				canvas.drawText(podatki, 195, 30, textPaint);


			}
			Log.e("Vrednost podatki", podatki);

		} 
	return super.draw(canvas, mapView, shadow, when); 
	}
}