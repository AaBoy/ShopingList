package edu.pernat.shopinglist.android;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
import edu.pernat.shopinglist.android.R.string;
import edu.pernat.shopinglist.android.razredi.Artikli;
import edu.pernat.shopinglist.android.razredi.Trgovina;

public class SplashScreen extends Activity {

	//how long until we go to the next activity
	protected int _splashTime = 5000; 

//	String podatki="ormoška cesta 30;2250 Ptuj\n" +
//			"Opekarniška cesta 9;3000 Celje\n" +
//			"Cesta talcev 4;1230 Domžale\n" +
//			"Spodnji plavž 5;4270 Jesenice\n" +
//			"Kovinarska cesta 36;1241 Kamnik\n" +
//			"Dolinska cesta 1A;6000 Koper\n" +
//			"Kolodvorska c. 4;6000 Koper\n" +
//			"Cesta Staneta Žagarja 69;4000 Kranj\n" +
//			"Stara cesta 25;4000 Kranj\n";
//	
//	String izdelki="1;SMOKIJI;1,59 ;400g;SMOKIJI SMOKI\n" +
//	"1;POSEBA;4,69 ;Kg;PIŠČANČJA\n" +
//	"1;KRUH;1,99 ;KG;BELI PEČJAK\n" +
//	"1;ČIPS;1,09 ;Kg;DOMAČ KROMPIR\n" +
//	"1;SOK;0,54 ;1l;MRELICA 50% SADNI DELEŽ\n" +
//	"1;TUNA;1,99 ;160g;TUNA V PARADIŽNIKOVI OMAKI Z OLIVAMI\n"+
//			"1;VINO;3,49 ;1L;BORDO VINO\n" +
//			"1;REZANCI;1,49 ;1kg;POLNOZRNATI\n";
	
	String [] prvaRazdelitev;
	String [] naDvaDela=new String[2];
	String [] naPetDelov=new String[5];
	private Thread splashTread;
	GlobalneVrednosti app;
	private static final String SOAP_ACTION_TRGOVINA="http://izdelki.shoopinglist.pernat.edua/Trgovine";
	private static final String SOAP_ACTION_PRIDOBI_IZ_BAZE="http://izdelki.shoopinglist.pernat.edua/pridobiIzbaze";
	private static final String SOAP_ACTION_PRIDOBI_POSODOBI="http://izdelki.shoopinglist.pernat.edua/pridobiIzbazeNazadnjeSpremenjene";
	
	private static final String NAMESPACE="http://izdelki.shoopinglist.pernat.edu";
	private static final String URL="http://192.168.1.5:8080/PridobiMerkatorIzdelke/services/MainClass?wsdl";
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
	    app.seznamArtiklov.clear();
    	app.vsiSeznami.ustvarjeniSeznami.clear();
    	
		SharedPreferences shIzdelki = getPreferences(MODE_PRIVATE);
		imamoIzdelke =shIzdelki.getString("IZDELKI", "");
		
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor1 = sharedPreferences.edit();
		editor1.putString("NEKO SRANJE", "Miha");
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
					AndroidHttpTransport aht=new AndroidHttpTransport(URL,10000);
//					
//					
					try{
						
						aht.call(SOAP_ACTION_TRGOVINA,soapEnvelope);
					
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
					Toast.makeText(this, "XML Pasing Excpetion = " + e, Toast.LENGTH_LONG).show();
					
				}finally {
	               
	            }
			}
	}

	private void kakoZIZdelki()
	{
		Log.e("Preverim ali je tabela polna", "Preverim ali je tabela polna");
		if(app.obstajaIzdelkiTabela())
		{
			Log.e("Tabela je polna", "Tabela je polna");
			
			if(imamoIzdelke!=null)
			{
				try {
					Log.e("Posodobim podatke", "posodobim podatke");
					SoapObject Request =new SoapObject(NAMESPACE,METHOD_NAME_NAZADNJE_SPREMENJENE);
					Request.addProperty("datum",imamoIzdelke);	
					
					SoapSerializationEnvelope soapEnvelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
					soapEnvelope.dotNet=false;
					soapEnvelope.setOutputSoapObject(Request);	
					AndroidHttpTransport aht=new AndroidHttpTransport(URL,10000);	
					try{
					
					aht.call(SOAP_ACTION_PRIDOBI_POSODOBI,soapEnvelope);	
					SoapPrimitive result =(SoapPrimitive)soapEnvelope.getResponse(); 	
					
					String vmesni=result.toString();
					prvaRazdelitev=vmesni.split("\n");
					
					SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
					SharedPreferences.Editor editor1 = sharedPreferences.edit();
					editor1.putString("IZDELKI", prvaRazdelitev[prvaRazdelitev.length-1]);
					Log.e("Dobim datum", prvaRazdelitev[prvaRazdelitev.length-1]);

					for(int i=0;i<prvaRazdelitev.length-1;i++)
					{
						naPetDelov=prvaRazdelitev[i].split(";");
						
						app.updateArtili(new Artikli( Double.parseDouble( naPetDelov[1].replace(",", ".")), 
								naPetDelov[0], naPetDelov[2], naPetDelov[3],i));	
					}
					app.fillFromDBIzdelki();
					}catch(Exception e){
					e.printStackTrace();
					app.fillFromDBIzdelki();
					}
					
				} catch (Exception e) {
					System.out.println("Posodobi bazo = " + e);
					Toast.makeText(this, "Posodobi bazo = " + e, Toast.LENGTH_LONG).show();
					app.fillFromDBIzdelki();
				}
			}
			
			else
			{
				Log.e("Sem v izdelki, ko tabela ni prazna, nimamo nič v share", "Sem v izdelki, ko tabela ni prazna, nimamo nič v share");
				app.fillFromDBIzdelki();
			}
		
		}
		else
		{
			try {
				Log.e("Sem v izdelki, ko tabela je prazna", "Sem v izdelki, ko tabela je prazna");
				SoapObject Request =new SoapObject(NAMESPACE,METHOD_NAME_PRIDOBI_IZ_BAZE);	
				SoapSerializationEnvelope soapEnvelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
				soapEnvelope.dotNet=false;
				soapEnvelope.setOutputSoapObject(Request);	
				AndroidHttpTransport aht=new AndroidHttpTransport(URL,30000);
				
				try{
				aht.call(SOAP_ACTION_PRIDOBI_IZ_BAZE,soapEnvelope);
				SoapPrimitive result =(SoapPrimitive)soapEnvelope.getResponse(); 	
				
				String vmesni=result.toString();
				prvaRazdelitev=vmesni.split("\n");
				
				

				SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor1 = sharedPreferences.edit();
				editor1.putString("IZDELKI", prvaRazdelitev[prvaRazdelitev.length-1]);
				
				Log.e("Dobim datum", prvaRazdelitev[prvaRazdelitev.length-1]);
				for(int i=0;i<prvaRazdelitev.length-1;i++)
				{
				naPetDelov=prvaRazdelitev[i].split(";");
				app.artikelNaSeznamInBazo(new Artikli( Double.parseDouble( naPetDelov[1].replace(",", ".")), 
				naPetDelov[0], naPetDelov[2], naPetDelov[3],i));
				}
				}catch(Exception e){
				e.printStackTrace();
				
				}
				
			} catch (Exception e) {
				System.out.println("Napolni bazo = " + e);
				Toast.makeText(this, "Napolni bazo = " + e, Toast.LENGTH_LONG).show();
				
			}
		}
	}
}


	



