package edu.pernat.shopinglist.android.service;

import java.util.ArrayList;
import java.util.Timer;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import edu.pernat.shopinglist.android.GlobalneVrednosti;
import edu.pernat.shopinglist.android.NovSeznam;
import edu.pernat.shopinglist.android.R;
import edu.pernat.shopinglist.android.razredi.PrijavniPodatki;
import edu.pernat.shopinglist.android.razredi.SeznamIzBaze;

public class PreveriSeznam extends Service {
	private static final String TAG = "MyService";
	private Timer timer = new Timer(); 
	GlobalneVrednosti app;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		
		app=(GlobalneVrednosti)getApplication();
		
		try {
			SharedPreferences shIzdelki =this.getSharedPreferences("UPORABNISKI_PODATKI", MODE_PRIVATE);
			app.setPrijavniPodatki(new PrijavniPodatki(shIzdelki.getString("UPORABNISKO", ""), shIzdelki.getString("GESLO", "")));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

	@Override
	public void onDestroy() {
	
	}
	
	//moje funkcije
		private static final String SOAP_ACTION="http://izdelki.shoopinglist.pernat.edua/vrniZaNotification";
		private static final String METHOD_NAME="vrniZaNotification";
		
		
		public ArrayList<SeznamIzBaze> pridobiPodatke()
		{
			String odgovor="";
			
			SoapObject Request =new SoapObject(app.NAMESPACE,METHOD_NAME);
			Request.addProperty("uporabnik","miha");	
			SoapSerializationEnvelope soapEnvelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
			soapEnvelope.dotNet=false;
			soapEnvelope.setOutputSoapObject(Request);				
			AndroidHttpTransport aht=new AndroidHttpTransport(app.URL,5000);
			ArrayList<SeznamIzBaze> seznamIzBaze=new ArrayList<SeznamIzBaze>();	
			try{
				
				aht.call(SOAP_ACTION,soapEnvelope);
				SoapPrimitive result =(SoapPrimitive)soapEnvelope.getResponse(); 	
				String tmp=result.toString();
				String [] prvaRazdelitev=tmp.split(";");
				for(int i=1;i<prvaRazdelitev.length;i++)
				{
					String [] imeUporabnik=prvaRazdelitev[i].split("#");
					seznamIzBaze.add(new SeznamIzBaze(imeUporabnik[0], imeUporabnik[1]));
					
				}
			}catch(Exception e){
					
			}
			
			return seznamIzBaze;
				
			
		}
	
	@Override
	public void onStart(Intent intent, int startid) {
		
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = R.drawable.adroid_gospa;
		CharSequence tickerText = "Pošiljajo vam nov seznam";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		ArrayList<SeznamIzBaze> tmp=pridobiPodatke();
		
		if(tmp.size()==0)
		{
			
		}
		else if(tmp.size()==1)
		{
			Context context = getApplicationContext();
			CharSequence contentTitle = "Dobili ste seznam: "+tmp.get(0).getImeSeznama();
			CharSequence contentText = "Pošilja vam ga: "+tmp.get(0).getImePosiljatelja();
			Intent notificationIntent = new Intent(this, NovSeznam.class);
			notificationIntent.putExtra("ime", tmp.get(0).getImeSeznama());
			String [] dva=tmp.get(0).getImePosiljatelja().split("   ");
			notificationIntent.putExtra("posiljatel", dva[0]);
//		    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
			Log.e("Sem ko je vecji od 1", "ime je _"+tmp.get(0).getImeSeznama());
			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
			final int HELLO_ID = 1;

			mNotificationManager.notify(HELLO_ID, notification);
		}
		else if(tmp.size()>1)
		{
			Context context = getApplicationContext();
			CharSequence contentTitle = "Dobili ste sezname";
			CharSequence contentText = "Poslali vam jih je eden ali več uporabnikov";
			Intent notificationIntent = new Intent(this, NovSeznam.class);
			notificationIntent.putExtra("ime", "");
			Log.e("Sem ko je vecji od 1", "ime je _");
			notificationIntent.putExtra("posiljatel", "");
//		    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
			final int HELLO_ID = 1;
			
			mNotificationManager.notify(HELLO_ID, notification);
		}

	}
	
	
}
