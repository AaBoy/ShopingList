package edu.pernat.shopinglist.android.maps;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MyPositionOverlay extends Overlay {

  private final int mRadius = 8;

  public MyPositionOverlay() {
	  super();
	  locations = new ArrayList<Location>();
  }
  Location location;
  ArrayList<Location> locations;
 
  public Location getLocation() {
    return location;
  }
  public void setLocation(Location location) {
    this.location = location;
  }
	
  @Override
  public boolean onTap(GeoPoint point, MapView mapView) {
    return false;
  }
  
  @Override
  public void draw(Canvas canvas, MapView mapView, boolean shadow) {
    Projection projection = mapView.getProjection();

    if ((location!=null)&&(shadow == false)) {
    	
    	
      // Get the current location    
      Double latitude = location.getLatitude()*1E6;
      Double longitude = location.getLongitude()*1E6;
      GeoPoint geoPoint; 
      geoPoint = new 
        GeoPoint(latitude.intValue(),longitude.intValue());
      
      
      locations.add(new Location(location));
      
      // Convert the location to screen pixels     
      Point point = new Point();
      projection.toPixels(geoPoint, point);

      RectF oval = new RectF(point.x - mRadius, point.y - mRadius, 
                             point.x + mRadius, point.y + mRadius);

      // Setup the paint
      Paint paint = new Paint();
      paint.setARGB(250,255 , 0, 0);
      paint.setAntiAlias(true);
      paint.setFakeBoldText(true);

      Paint backPaint = new Paint();
      backPaint.setARGB(175, 50, 50, 50);
      backPaint.setAntiAlias(true);

      RectF backRect = new RectF(point.x + 2 + mRadius, 
                                 point.y - 3*mRadius,
                                 point.x + 65, point.y + mRadius);

      // Draw the marker    
      canvas.drawOval(oval, paint);
      canvas.drawRoundRect(backRect, 5, 5, backPaint);

     
      canvas.drawText("TUKAJ"+locations.size(), 
              point.x + 2*mRadius+2, point.y, 
              paint);
      
      for(int i=0;i<locations.size()-1;i++)
      {
    	  
    	 Double lat= locations.get(i).getLatitude()*1E6;
    	 Double lon=locations.get(i).getLongitude()*1E6;
    	 
    	 GeoPoint geoPoint1; 
         geoPoint1 = new 
         GeoPoint(lat.intValue(),lon.intValue());
         Point point1 = new Point();
         projection.toPixels(geoPoint1, point1);

         RectF oval1 = new RectF(point1.x - mRadius, point1.y - mRadius, 
                                point1.x + mRadius, point1.y + mRadius);

         paint.setARGB(250,0 , 255, 0);    
         canvas.drawOval(oval1, paint);


    	  Double lat2 = locations.get(i+1).getLatitude()*1E6;
    	  Double lon2 = locations.get(i+1).getLongitude()*1E6;

    	  GeoPoint gp1= new GeoPoint(lat.intValue(),lon.intValue());  
    	  GeoPoint gp2= new GeoPoint(lat2.intValue(),lon2.intValue());

    	  Path path = new Path();
    	  Point p1=new Point();
    	  Point p2=new Point();

    	  projection.toPixels(gp1, p1);
    	  projection.toPixels(gp2, p2);
    	  path.moveTo(p2.x, p2.y);
    	  path.lineTo(p1.x,p1.y);
    	  Paint crta=new Paint();
    	  crta.setARGB(250, 0, 0, 255);
    	  canvas.drawLine(p1.x, p1.y, p2.x, p2.y, crta);
      }

      
    }
    super.draw(canvas, mapView, shadow);
  }
}