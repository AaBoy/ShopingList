package edu.pernat.shopinglist.android;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import edu.pernat.shopinglist.android.razredi.Artikli;
import edu.pernat.shopinglist.android.razredi.Trgovina;

public class SplashScreen extends Activity {

	//how long until we go to the next activity
	protected int _splashTime = 5000; 

	String podatki="Medvoška c. 3;1215 Medvode\n" +
			"Opekarniška cesta 9;3000 Celje\n" +
			"Cesta talcev 4;1230 Domžale\n" +
			"Spodnji plavž 5;4270 Jesenice\n" +
			"Kovinarska cesta 36;1241 Kamnik\n" +
			"Dolinska cesta 1A;6000 Koper\n" +
			"Kolodvorska c. 4;6000 Koper\n" +
			"Cesta Staneta Žagarja 69;4000 Kranj\n" +
			"Stara cesta 25;4000 Kranj\n";
	
	String izdelki="1;SMOKIJI;1,59 ;400g;SMOKIJI SMOKI\n" +
	"1;POSEBA;4,69 ;Kg;PIŠČANČJA\n" +
	"1;KRUH;1,99 ;KG;BELI PEČJAK\n" +
	"1;ČIPS;1,09 ;Kg;DOMAČ KROMPIR\n" +
	"1;SOK;0,54 ;1l;MRELICA 50% SADNI DELEŽ\n" +
	"1;TUNA;1,99 ;160g;TUNA V PARADIŽNIKOVI OMAKI Z OLIVAMI\n"+
			"1;VINO;3,49 ;1L;BORDO VINO\n" +
			"1;REZANCI;1,49 ;1kg;POLNOZRNATI\n";
	
	String [] prvaRazdelitev;
	String [] naDvaDela=new String[2];
	String [] naPetDelov=new String[5];
	private Thread splashTread;
	GlobalneVrednosti app;
	private static final String SOAP_ACTION="http://projektIzdelki.pernat.edu/NajcenejsiSeznam";
	
	private static final String NAMESPACE="http://projektIzdelki.pernat.edu";
	private static final String URL="http://164.8.118.232:8080/projketIzdelki/services/NajboljsiSeznam?wsdl";
	private static final String METHOD_NAME_PRIDOBI_IZ_BAZE="pridobiIzbaze";
	private static final String METHOD_NAME_TRGOVINE="Trgovine";
	private static final String METHOD_NAME_NAZADNJE_SPREMENJENE="pridobiIzbazeNazadnjeSpremenjene";
	String imamoIzdelke;
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splash);
	    app=(GlobalneVrednosti) getApplication();
	    final SplashScreen sPlashScreen = this; 
		
		SharedPreferences shIzdelki = getPreferences(MODE_PRIVATE);
		imamoIzdelke =shIzdelki.getString("IZDELKI", "");
	    // thread for displaying the SplashScreen
	    splashTread = new Thread() {
	        @Override
	        public void run() {
	            try {
	            	
	            	 if(isNetworkAvailable())
	            	 {
	            		 Log.e("Omrežje ", "jee");
	            		 kakoSTrgovinami();
	            		 kakoZIZdelki();
	            		 if(app.obstajaTabelaSeznami())
	            		  {
	            			app.fillFromDBSeznami();
	            		  }	
	            		 if(app.obstajaVmensaTabela())
	            		 	{
	            			 app.fillFromDBVmesni();
	            			}
	            		 if(app.obstajaEmailTabela())
	            			{
	            			 app.fillFromDB();
	            			}
	                 }
	            			
	            	 else
	            	 {
	            		 Log.e("Omrežje ", "nii");
	            		 if(app.obstajaIzdelkiTabela())
		            		{	            			
		            			app.fillFromDBIzdelki();
		            		}
	            		
	            		 if(app.obstajaTrgovinaTabela())
		            		{
		            			app.fillFromDBTrgovina();
		            		}
	            		 
	            		 if(app.obstajaTabelaSeznami())
		            		{
		            			app.fillFromDBSeznami();
		            		}
		            		
	            		 if(app.obstajaVmensaTabela())
		            		{
		            			app.fillFromDBVmesni();
		            		}
	            		 if(app.obstajaEmailTabela())
		            		{
		            			app.fillFromDB();
		            		}
	            	 }
	            	 
	            } finally {
	                finish();

	                //start a new activity
	                //app.poskusni();
	                Intent i = new Intent();
	                i.setClass(sPlashScreen, SeznamNarocil.class);
	        		startActivity(i);
	        		
	                stop();
	            }
	        } 
	        
	    };
	    splashTread.start();
	   
	}

	//Function that will handle the touch
	
	public boolean onTouchEvent(MotionEvent event) {
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	    	synchronized(splashTread){
	    		splashTread.notifyAll();
	    	}
	    }
	    return true;
	    
	}
	
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager
		= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null;
		}
	
	private void kakoSTrgovinami()
	{
		 
        	 
				if(app.obstajaTrgovinaTabela())
				{
					app.fillFromDBTrgovina();
				}
				else
				{
				try {
					SoapObject Request =new SoapObject(NAMESPACE,METHOD_NAME_TRGOVINE);
					SoapSerializationEnvelope soapEnvelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
					soapEnvelope.dotNet=false;
					soapEnvelope.setOutputSoapObject(Request);		
					HttpTransportSE aht=new HttpTransportSE(URL);	
					
					try{
					aht.call(SOAP_ACTION,soapEnvelope);
					SoapPrimitive result =(SoapPrimitive)soapEnvelope.getResponse();	        			
					app.sprazniBazoTrgovina();
					prvaRazdelitev=result.toString().split("\n");
					for(int i=0;i<prvaRazdelitev.length;i++)
					{
						naDvaDela=prvaRazdelitev[i].split(";");
						app.dodajTrgovinoNaSeznam(new Trgovina(naDvaDela[0], naDvaDela[1]));
					}
					}catch(Exception e){
					e.printStackTrace();
					
					}
					
				} catch (Exception e) {
					System.out.println("XML Pasing Excpetion = " + e);
					
				}finally {
	               
	            }
			}
	}

	private void kakoZIZdelki()
	{
		if(app.obstajaIzdelkiTabela())
		{
		
			if(imamoIzdelke!="")
			{
				try {
					SoapObject Request =new SoapObject(NAMESPACE,METHOD_NAME_NAZADNJE_SPREMENJENE);
					Request.addProperty("datum",imamoIzdelke);	
					
					SoapSerializationEnvelope soapEnvelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
					soapEnvelope.dotNet=false;
					soapEnvelope.setOutputSoapObject(Request);	
					HttpTransportSE aht=new HttpTransportSE(URL);	
					
					try{
					aht.call(SOAP_ACTION,soapEnvelope);
					SoapPrimitive result =(SoapPrimitive)soapEnvelope.getResponse(); 	
					
					app.sprazniBazoTrgovina();
					naDvaDela=result.toString().split("|");
					
					SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
					SharedPreferences.Editor editor1 = sharedPreferences.edit();
					editor1.putString("IZDELKI", naDvaDela[1]);
					
					
					prvaRazdelitev=naDvaDela[0].split("\n");
					for(int i=0;i<prvaRazdelitev.length;i++)
					{
						naPetDelov=prvaRazdelitev[i].split(";");
						
						app.updateArtili(new Artikli( Double.parseDouble( naPetDelov[2].replace(",", ".")), 
								naPetDelov[1], naPetDelov[3], naPetDelov[4],i));	
					}
					app.fillFromDBIzdelki();
					}catch(Exception e){
					e.printStackTrace();
					
					}
					
				} catch (Exception e) {
					System.out.println("Posodobi bazo = " + e);
					app.fillFromDBIzdelki();
				}
			}
			
			else
				app.fillFromDBIzdelki();
			
		
		}
		else
		{
			try {
				SoapObject Request =new SoapObject(NAMESPACE,METHOD_NAME_PRIDOBI_IZ_BAZE);	
				SoapSerializationEnvelope soapEnvelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
				soapEnvelope.dotNet=false;
				soapEnvelope.setOutputSoapObject(Request);	
				HttpTransportSE aht=new HttpTransportSE(URL);	
				
				try{
				aht.call(SOAP_ACTION,soapEnvelope);
				SoapPrimitive result =(SoapPrimitive)soapEnvelope.getResponse(); 	
				
				app.sprazniBazoTrgovina();
				naDvaDela=result.toString().split("|");
				
				SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor1 = sharedPreferences.edit();
				editor1.putString("IZDELKI", naDvaDela[1]);
				prvaRazdelitev=naDvaDela[0].split("\n");
				for(int i=0;i<prvaRazdelitev.length;i++)
				{
				naPetDelov=prvaRazdelitev[i].split(";");
				app.artikelNaSeznamInBazo(new Artikli( Double.parseDouble( naPetDelov[2].replace(",", ".")), 
				naPetDelov[1], naPetDelov[3], naPetDelov[4],i));
				}
				}catch(Exception e){
				e.printStackTrace();
				
				}
				
			} catch (Exception e) {
				System.out.println("Napolni bazo = " + e);
				app.fillFromDBIzdelki();
			}
		}
	}
}


	




