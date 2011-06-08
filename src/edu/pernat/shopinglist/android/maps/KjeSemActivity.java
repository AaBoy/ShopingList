package edu.pernat.shopinglist.android.maps;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.http.util.LangUtils;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import edu.pernat.shopinglist.android.DodajIzdelek;
import edu.pernat.shopinglist.android.Dostava;
import edu.pernat.shopinglist.android.IzberiTrgovino;
import edu.pernat.shopinglist.android.R;
import edu.pernat.shopinglist.android.SpremeniIzdelek;
import edu.pernat.shopinglist.android.UstvariNovIzdelek;
import edu.pernat.shopinglist.android.maps.MyPositionOverlay;
import edu.pernat.shopinglist.android.razredi.Seznami;
import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class KjeSemActivity extends MapActivity {
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	//debug
	//keytool -list -alias androiddebugkey -keystore /Users/matej/.android/debug.keystore -storepass android -keypass android
	//8D:22:34:2A:C0:70:9C:0C:B4:A1:AC:B3:C7:12:2D:1C
	//http://code.google.com/android/maps-api-signup.html
	MapController mapController;
	MyPositionOverlay positionOverlay;

	TextView novi ;
	double LATITUDE = 37.42233;
	double LONGITUDE = -122.083;
	private Menu mMenu;  //ni nujno
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("Delo!");
		setContentView(R.layout.maps_main);
		
		
		MapView myMapView = (MapView)findViewById(R.id.myMapView);
		mapController = myMapView.getController();

		myMapView.setSatellite(true);
		myMapView.setStreetView(true);
		myMapView.displayZoomControls(false);

		mapController.setZoom(17);


		LocationManager locationManager;
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager)getSystemService(context);
		// Add the MyPositionOverlay
		positionOverlay = new MyPositionOverlay();
		List<Overlay> overlays = myMapView.getOverlays();
		overlays.add(positionOverlay);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);

		Location location = locationManager.getLastKnownLocation(provider);
		my_updateWithNewLocation(location);

		locationManager.requestLocationUpdates(provider, 2000, 10,   
				locationListener);
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
	        	IzberiTrgovino dialog1 = new IzberiTrgovino(mContext1);      	
				return dialog1;

	        
	        default:
	            break;
	        }
	        
	        return null;
	    }

	
	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			my_updateWithNewLocation(location);
		}

		public void onProviderDisabled(String provider){
			my_updateWithNewLocation(null);
		}

		public void onProviderEnabled(String provider){ }
		public void onStatusChanged(String provider, int status, 
				Bundle extras){ }
	};

	private void my_updateWithNewLocation(Location location) {
		String latLongString;
		TextView myLocationText;
		myLocationText = (TextView)findViewById(R.id.myLocationText);

		if (location != null) {
			positionOverlay.setLocation(location);

			Double geoLat = location.getLatitude()*1E6;
			Double geoLng = location.getLongitude()*1E6;


			double lat = location.getLatitude();
			double lng = location.getLongitude();
			latLongString = "Lat:" + lat + "\nLong:" + lng;

			myLocationText.setText("Trenutni položaj je:" + 
					latLongString); 
			
			/*novi=(TextView)findViewById(R.id.nekajTrgovina);*/
			 Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
			 
			 
			 
		       try {
		  List<Address> addresses =
			  geocoder.getFromLocationName("TRŽAŠKA 65 2000 Maribor", 1, 46.44896008877663, 15.4852294921875, 46.629384155883876, 15.835418701171875); 
			  
		 
		  if(addresses != null) {
		   Address returnedAddress = addresses.get(0);
		   
		   StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
		   for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
		    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
		   }
		   Double lat1=returnedAddress.getLatitude()*1E6;
		   Double log1=returnedAddress.getLongitude()*1E6;
		   
			GeoPoint point = new GeoPoint(lat1.intValue(), log1.intValue());

			mapController.animateTo(point);
		   
		   //novi.setText(lat1.toString()+"  "+log1.toString());
		  }
		  else{
		 
		  }
		 } catch (IOException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
		
		 }
		}
	}
}