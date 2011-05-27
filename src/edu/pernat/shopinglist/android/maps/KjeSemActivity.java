package edu.pernat.shopinglist.android.maps;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import edu.pernat.shopinglist.android.R;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
	Spinner imeTrgovin, naslovTrgovin;
	String array_spinner[];
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
		
		
		//*Dodaj spinerje*/
		
		imeTrgovin=(Spinner)findViewById(R.id.spinerNazivTrgovine);
		naslovTrgovin=(Spinner)findViewById(R.id.spinerNasloTrgovine);
		
		array_spinner=new String [5];
		array_spinner[0]="Merkator";
		array_spinner[1]="Spar";
		array_spinner[2]="Tuš";
		array_spinner[3]="Žerak";
		array_spinner[4]="Eurospin";
		
		
		
		ArrayAdapter pinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array_spinner);
		imeTrgovin.setAdapter(pinnerArrayAdapter);
		
		array_spinner=new String [5];
		array_spinner[0]="Ptujska 33";
		array_spinner[1]="Miheličeva 55";
		array_spinner[2]="Nova Gorica 5b";
		array_spinner[3]="Tezno 12";
		array_spinner[4]="Žirovnik ul. 5";
		
		ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array_spinner);
		naslovTrgovin.setAdapter(spinnerArrayAdapter);



	/*Konec dodajanja*/

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
			GeoPoint point = new GeoPoint(geoLat.intValue(), 
					geoLng.intValue());

			mapController.animateTo(point);

			double lat = location.getLatitude();
			double lng = location.getLongitude();
			latLongString = "Lat:" + lat + "\nLong:" + lng;

			myLocationText.setText("Trenutni položaj je:" + 
					latLongString); 
		}
	}
}