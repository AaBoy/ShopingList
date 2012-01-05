package edu.pernat.shopinglist.android.widget;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import edu.pernat.shopinglist.android.GlobalneVrednosti;
import edu.pernat.shopinglist.android.R;

public class UpdateWidgetService extends Service {

	//moje funkcije
	private static final String SOAP_ACTION="http://izdelki.shoopinglist.pernat.edua/ZadnjihPet";
	private static final String METHOD_NAME="ZadnjihPet";
	private static final String NAMESPACE="http://izdelki.shoopinglist.pernat.edu";
	private static final String URL="http://192.168.1.5:8080/PridobiMerkatorIzdelke/services/MainClass?wsdl";
	public String pridobiPodatke()
	{
		String odgovor="";
		
			SoapObject Request =new SoapObject(NAMESPACE,METHOD_NAME);
			
			SoapSerializationEnvelope soapEnvelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
			soapEnvelope.dotNet=false;
			soapEnvelope.setOutputSoapObject(Request);				
			AndroidHttpTransport aht=new AndroidHttpTransport(URL,10000);
			
			try{
				aht.call(SOAP_ACTION,soapEnvelope);
				SoapPrimitive result =(SoapPrimitive)soapEnvelope.getResponse();
				odgovor=result.toString();
				Log.e("Odgovor widget", odgovor);
			}catch(Exception e){
				e.printStackTrace();
				Log.e("Napak v widget", e.toString());
				return "MUSLI;2,29;375g;BIO SADNI MUSLI IZ EKOLOŠKE PRIDELAVE ŽITNI KOSMIČI IN SUHO SADJE\n"+
				"ČIPS;0,62;150g;SLANE KROMPIRJEVE REZINE V VREČKI\n"+
				"ČAJ LEDENI;0,30;0,5L;NEGAZIRANA BREZALKOHOLNA PIJAČA V PVC BRESKEV\n"+
				"SOK;0,35;500ml;NEGAZIRANA BREZALKOHOLNA PIJAČA V PLASTENKI CITRUS MIX\n"+
				"SIR;0,95;100g;POLTRDI SIR S PLEMENITO MODRO PLESNIJO";
			}
		
		return odgovor;
			
		
	}
	
	@Override
	public void onStart(Intent intent, int startId) {

		
		String s =pridobiPodatke();
		
		String []prvaRudna=s.split("\n");
		
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
				.getApplicationContext());

		int[] appWidgetIds = intent
				.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
		
		Log.e("Weidget appweidgetmanager", appWidgetManager.toString());
		Log.e("appwidgetIDS", appWidgetIds[0]+"");
		Log.e("appwidgetIDS", R.id.TextViewCena1+"");
		
		if (appWidgetIds.length > 0) {
			for (int widgetId : appWidgetIds) {
				RemoteViews remoteViews = new RemoteViews(getPackageName(),
						R.layout.widget_layout);
				String[] podatki=prvaRudna[0].split(";");
				
				remoteViews.setTextViewText(R.id.TextViewCena1, (podatki[1]).replace(".", ",")+" €");
				remoteViews.setTextViewText(R.id.TextViewIme1,podatki[0]);
				remoteViews.setTextViewText(R.id.TextViewKolicina1,podatki[2]);
				remoteViews.setTextViewText(R.id.TextViewOpis1,podatki[3]);
				podatki=prvaRudna[1].split(";");
				remoteViews.setTextViewText(R.id.TextViewCena2, (podatki[1]).replace(".", ",")+" €");
				remoteViews.setTextViewText(R.id.TextViewIme2,podatki[0]);
				remoteViews.setTextViewText(R.id.TextViewKolicina2,podatki[2]);
				remoteViews.setTextViewText(R.id.TextViewOpis2,podatki[3]);
				podatki=prvaRudna[2].split(";");
				remoteViews.setTextViewText(R.id.TextViewCena3, (podatki[1]).replace(".", ",")+" €");
				remoteViews.setTextViewText(R.id.TextViewIme3,podatki[0]);
				remoteViews.setTextViewText(R.id.TextViewKolicina3,podatki[2]);
				remoteViews.setTextViewText(R.id.TextViewOpis3,podatki[3]);
				podatki=prvaRudna[3].split(";");
				remoteViews.setTextViewText(R.id.TexViewCena4, (podatki[1]).replace(".", ",")+" €");
				remoteViews.setTextViewText(R.id.TextViewIme4,podatki[0]);
				remoteViews.setTextViewText(R.id.TextViewKolicna4,podatki[2]);
				remoteViews.setTextViewText(R.id.TextViewOpis4,podatki[3]);
				podatki=prvaRudna[4].split(";");
				remoteViews.setTextViewText(R.id.TextViewCena5, (podatki[1]).replace(".", ",")+" €");
				remoteViews.setTextViewText(R.id.TextViewIme5,podatki[0]);
				remoteViews.setTextViewText(R.id.TextViewKolicina5,podatki[2]);
				remoteViews.setTextViewText(R.id.TextViewOpis5,podatki[3]);
				
				
				appWidgetManager.updateAppWidget(widgetId, remoteViews);
			}
			stopSelf();
		}

		super.onStart(intent, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
