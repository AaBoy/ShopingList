package edu.pernat.shopinglist.android.maps;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import edu.pernat.shopinglist.android.GlobalneVrednosti;
import edu.pernat.shopinglist.android.R;

public class KjeSemActivity extends MapActivity {
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	//debug
	//keytool -list -alias androiddebugkey -keystore /Users/matej/.android/debug.keystore -storepass android -keypass android
	//8D:22:34:2A:C0:70:9C:0C:B4:A1:AC:B3:C7:12:2D:1C
	//http://code.google.com/android/maps-api-signup.html
	private static final int GLAVNO_OKNO_ID = 0;
	private static boolean FLAG_PRVIC=true;
	
	MapController mapController;
	ArrayList<Location> locations;
	GeoPoint konec, zacetek;
	MapView myMapView;
	LocationManager locationManager;
	Boolean lokacijaFlag=false;
	GeoPoint prvaLokacija;
	GeoPoint drugaLokacija;
	String infoOPoti="";
	private Menu mMenu;  //ni nujno
	GlobalneVrednosti app;
	Location loca,locb;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_main);
        loca=new Location("Location A");
        locb=new Location("location b");
        this.setRequestedOrientation(1);
		app=(GlobalneVrednosti)getApplication();
		locations=new ArrayList<Location>();
		try{
		//-----------------------------------------maps--
			myMapView = (MapView)findViewById(R.id.myMapView);
			mapController = myMapView.getController();
			
			
			myMapView.displayZoomControls(false);
	
	
			mapController.setZoom(17);
				
			String context = Context.LOCATION_SERVICE;
			locationManager = (LocationManager)getSystemService(context);
	
			Criteria criteria1 = new Criteria();
			criteria1.setAccuracy(Criteria.ACCURACY_FINE);
			criteria1.setAltitudeRequired(false);
			criteria1.setBearingRequired(false);
			criteria1.setCostAllowed(true);
			criteria1.setPowerRequirement(Criteria.POWER_LOW);
			String provider1 = locationManager.getBestProvider(criteria1, true);
	
			Location location1 = locationManager.getLastKnownLocation(provider1);
			
			my_updateWithNewLocation(location1);
			locationManager.requestLocationUpdates(provider1, 0, 0, locationListener);
		

		}catch (Exception e) {
			// TODO: handle exception
		}
		
		showDialog(1);
		}

	public boolean onCreateOptionsMenu(Menu menu) {

	      mMenu = menu; //ni nujno
	      MenuInflater inflater = getMenuInflater();
	      inflater.inflate(R.menu.maps_menu, mMenu);
	      return true;
	    }

	    
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	      case R.id.novaTrgovina:
	    	  showDialog(1);
	    	 
	    	  return true;
	    	  
	      case R.id.Clear:
	    	  
	      default:// Generic catch all for all the other menu resources
	        if (!item.hasSubMenu()) {
	          Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
	          return true;
	        }
	        break;
	      }
	      return false;

	    }
	
	  protected Dialog onCreateDialog(int id) {
	    	
	        switch(id) {
	        case 1:
	        	Context mContext1 = this;
	        	IzberiTrgovino dialog1 = new IzberiTrgovino(mContext1,this,app);     
	        	
				return dialog1;
	        default:
	            break;
	        }
	        
	        return null;
	    }
	
	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			if(FLAG_PRVIC==true){//nastavim prvo točko
				locations.clear();
				Double geoLat = location.getLatitude()*1E6;
				Double geoLng = location.getLongitude()*1E6;
				GeoPoint point = new GeoPoint(geoLat.intValue(),geoLng.intValue());
				mapController.animateTo(point);
				locations.add(location);
			}
			my_updateWithNewLocation(location);
			FLAG_PRVIC=false;

	}
		

		public void onProviderDisabled(String provider){
			my_updateWithNewLocation(null);
		}

		public void onProviderEnabled(String provider){ }
		public void onStatusChanged(String provider, int status, 
				Bundle extras){ }
	};

	public void my_updateWithNewLocation(Location location) {


		if (location != null) {
			Double geoLat = location.getLatitude()*1E6;
			Double geoLng = location.getLongitude()*1E6;
			GeoPoint point = new GeoPoint(geoLat.intValue(),geoLng.intValue());

			mapController.animateTo(point);
			locations.add(location);
			if(lokacijaFlag==false){
				Double lat= location.getLatitude()*1E6;
				Double lng= location.getLongitude()*1E6;
				prvaLokacija= new GeoPoint(lat.intValue(),lng.intValue());
				lokacijaFlag=true;
				loca.setLatitude(lat);
				loca.setLongitude(lng);
			}
	    	GeoPoint srcGeoPoint = new GeoPoint((int) (location.getLatitude() * 1E6),(int) (location.getLongitude() * 1E6)); 
	    	myMapView.getController().animateTo(srcGeoPoint);
		}		 
		 else
			{
			 try {
				 if(app.stSeznama!=-1)
				 {
					Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
				    List<Address> addresses =
				    geocoder.getFromLocationName(app.seznamTrgovin.get(app.stSeznama).print(), 5);
//					geocoder.getFromLocationName(app.seznamTrgovin.get(app.stSeznama).print(), 1, 46.44896008877663, 15.4852294921875, 46.629384155883876, 15.835418701171875); 
					app.stSeznama=-1;
					
					
				  if(addresses != null) {
				   Address returnedAddress = addresses.get(0);
				   
				   StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
				   for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
				    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
				   
					   Double lat1=returnedAddress.getLatitude()*1E6;
					   Double log1=returnedAddress.getLongitude()*1E6;
					   
					   drugaLokacija= new GeoPoint(lat1.intValue(), log1.intValue());
					   
					   Log.e("Sem notri pri lokaciji" , lat1+" " +log1);
					   mapController.setZoom(17);
						 
					   locb.setLatitude(lat1);
					   locb.setLongitude(log1);
					  // Toast.makeText(this, ""+(loca.distanceTo(locb)/1000) +" m" , Toast.LENGTH_LONG).show();
					   
					   DrawPath(prvaLokacija, new GeoPoint(lat1.intValue(),log1.intValue()), Color.RED, myMapView);
			           
				   }
				  }
				   else
				   {
					   Double lat1=46.312202*1E6;
					   Double log1=15.866029*1E6;
					   DrawPath(prvaLokacija, new GeoPoint(lat1.intValue(),log1.intValue()), Color.RED, myMapView);
			          
				   }
				   

				  }
			
				 } catch (Exception e) {
				Log.e("Napka v drugi poti", e.toString());
			}
			}
	}
	private void DrawPath(GeoPoint src,GeoPoint dest, int color, MapView mMapView01) 
	{ 
		
		
		// connect to map web service 
		StringBuilder urlString = new StringBuilder(); 
		urlString.append("http://maps.google.com/maps?f=d&hl=en"); 
		urlString.append("&saddr=");//from 
		urlString.append( Double.toString((double)src.getLatitudeE6()/1.0E6 )); 
		urlString.append(","); 
		urlString.append( Double.toString((double)src.getLongitudeE6()/1.0E6 )); 
		urlString.append("&daddr=");//to 
		urlString.append( Double.toString((double)dest.getLatitudeE6()/1.0E6 )); 
		urlString.append(","); 
		urlString.append( Double.toString((double)dest.getLongitudeE6()/1.0E6 )); 
		urlString.append("&ie=UTF8&0&om=0&output=kml"); 
		Log.d("xxx","URL="+urlString.toString()); 
		// get the kml (XML) doc. And parse it to get the coordinates(direction route). 
		Document doc = null; 
		HttpURLConnection urlConnection= null; 
		URL url = null; 
		try 
		{ 
			url = new URL(urlString.toString()); 
			urlConnection=(HttpURLConnection)url.openConnection(); 
			urlConnection.setRequestMethod("GET"); 
			urlConnection.setDoOutput(true); 
			urlConnection.setDoInput(true); 
			urlConnection.connect(); 
		
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			doc = db.parse(urlConnection.getInputStream()); 
		
			if(doc.getElementsByTagName("GeometryCollection").getLength()>0) 
			{ 
				
		
				//String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getNodeName(); 
				String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getFirstChild().getNodeValue() ; 
				Log.d("xxx","path="+ path); 
				String [] pairs = path.split(" "); 
				String[] lngLat = pairs[0].split(","); // lngLat[0]=longitude lngLat[1]=latitude lngLat[2]=height 
				// src 
				GeoPoint startGP = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6)); 
				mMapView01.getOverlays().add(new MyPositionOverlay(startGP,startGP,1));
				GeoPoint gp1; 
				GeoPoint gp2 = startGP; 
				loca=new Location("lokacija 1");
				locb=new Location("location 2");
				
				for(int i=1;i<pairs.length;i++) // the last one would be crash 
				{ 
					lngLat = pairs[i].split(","); 
					gp1 = gp2; 
					// watch out! For GeoPoint, first:latitude, second:longitude 
					
					gp2 = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6)); 
					mMapView01.getOverlays().add(new MyPositionOverlay(gp1,gp2,2,color)); 
//					Log.d("xxx","pair:" + pairs[i]); 
				}

				if(doc.getElementsByTagName("description").getLength()>0) 
				{ 
				  path = doc.getElementsByTagName("description").item(doc.getElementsByTagName("description").getLength()-1).getFirstChild().getNodeValue();
				  infoOPoti=path.replace("&#160;", "");
				  infoOPoti=infoOPoti.replace("Distance: ", "Dolžina ");
				  infoOPoti=infoOPoti.replace(" (about ", ", približno ");
				  infoOPoti=infoOPoti.replace(" mins", "minut");
				  infoOPoti=infoOPoti.replace(" hour", "ura");
				  int zacetek = infoOPoti.indexOf("<br/>");
				  infoOPoti =infoOPoti.substring(0,zacetek-1);
				  Log.e("Description ", infoOPoti);
				  
//				  Toast.makeText(this,  infoOPoti , Toast.LENGTH_LONG).show();
				}
				Toast.makeText(this,  infoOPoti , Toast.LENGTH_LONG).show();
				mMapView01.getOverlays().add(new MyPositionOverlay(dest,dest, 3,infoOPoti)); // use the default color 
			} 
		} 
		catch (MalformedURLException e) 
		{ 
			e.printStackTrace(); 
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
		} 
		catch (ParserConfigurationException e) 
		{ 
			e.printStackTrace(); 
		} 
		catch (SAXException e) 
		{ 
			
			e.printStackTrace(); 
		} 
	}
	
	@Override 
	public void onBackPressed(){
		super.onBackPressed();
		locationManager.removeUpdates(locationListener);	
		}
	
	
	
	
	
	
	
}
	