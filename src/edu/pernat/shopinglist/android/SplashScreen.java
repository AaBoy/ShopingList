package edu.pernat.shopinglist.android;

import java.io.IOException;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;
import edu.pernat.shopinglist.android.data.DataBaseHelperNapolniPrazno;
import edu.pernat.shopinglist.android.razredi.Artikli;
import edu.pernat.shopinglist.android.razredi.Trgovina;

public class SplashScreen extends Activity {
	
	String [] prvaRazdelitev;
	String [] naDvaDela=new String[2];
	String [] naPetDelov=new String[5];
	private Thread splashTread;
	GlobalneVrednosti app;
	private static final String SOAP_ACTION_TRGOVINA="http://izdelki.shoopinglist.pernat.edua/Trgovine";
	private static final String SOAP_ACTION_PRIDOBI_IZ_BAZE="http://izdelki.shoopinglist.pernat.edua/pridobiIzbaze";
	private static final String SOAP_ACTION_PRIDOBI_POSODOBI="http://izdelki.shoopinglist.pernat.edua/pridobiIzbazeNazadnjeSpremenjene";
	
	private static final String NAMESPACE="http://izdelki.shoopinglist.pernat.edu";
	private static final String URL="http://192.168.1.6:8080/PridobiMerkatorIzdelkeSpletniServis/services/MainClass?wsdl";
	private static final String METHOD_NAME_PRIDOBI_IZ_BAZE="pridobiIzbaze";
	private static final String METHOD_NAME_TRGOVINE="Trgovine";
	private static final String METHOD_NAME_NAZADNJE_SPREMENJENE="pridobiIzbazeNazadnjeSpremenjene";
	String imamoIzdelke;
	private Handler mHandler;
	TextView stanje;
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.splash);
	    this.setRequestedOrientation(1);
	    DataBaseHelperNapolniPrazno myDbHelper = new DataBaseHelperNapolniPrazno(this);
        
	    
        try {
 
        	myDbHelper.createDataBase();
 
        } catch (IOException ioe) {
 
 		throw new Error("Unable to create database");
        }
 
	    
	    app=(GlobalneVrednosti) getApplication();
	    final SplashScreen sPlashScreen = this; 
	    app.seznamArtiklov.clear();
    	app.vsiSeznami.ustvarjeniSeznami.clear();
    	mHandler = new Handler();

    	
    	
		SharedPreferences shIzdelki =this.getSharedPreferences("IZDELKI", MODE_PRIVATE);
		imamoIzdelke =shIzdelki.getString("IZDELKI", "");
		
		stanje=(TextView)findViewById(R.id.textViewSplash);
		mHandler= new Handler(){
            
			@Override
            public void  handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.arg1) {
				case 1:
					stanje.setText("Pridobivam seznam izdelkov iz interneta.");
					break;
				case 2:
					stanje.setText("Pridobivam seznam trgovin iz interneta.");
					break;
				case 3:
					stanje.setText("Posodabljam izdelke iz interneta.");
					break;
				case 4:
					stanje.setText("Pridobivam izdelke iz interneta, lahko traja nekoliko več časa.");
					break;
				case 5:
					stanje.setText("Pridobivam podatke iz lokalne baze.");
					break;
				default:
					break;
				}
               
            }
        };

        PridobiOsnovnePodatke ps=new PridobiOsnovnePodatke();
        ps.execute(10);
       
	}
	//Asinhroni task 

	private class PridobiOsnovnePodatke extends AsyncTask<Integer, Void, String> {
		protected String doInBackground(Integer... prviArgument) {
			
			try {
					if(isNetworkAvailable())
    	            	 {
    	            		 Log.e("Omrežje ", "jee");
    	            		 Message msg= Message.obtain();
    	            		 msg.arg1=2;
    	                     mHandler.sendMessage(msg);
    	            		 kakoSTrgovinami();
//    	            		 stanje.setText("Pridobivam seznam izdelkov iz interneta.");
    	            		 Message msg1= Message.obtain();
    	                     msg1.arg1=1;
    	                     mHandler.sendMessage(msg1);
    	                        
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
    	            		 Message msg1= Message.obtain();
    	                     msg1.arg1=5;
    	                     mHandler.sendMessage(msg1);
    	            		 stanje.setText("Nalagam iz lokalne baze");
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
    	            	 
    	            
				}catch(Exception  e)
				{
					Log.e("SplashScreen + Asinhroni taks", e.toString());
				}
				
			
			return  "";
		}

		protected void onPostExecute(String tretjiArgument) {
			finish();
			pozeniActivity();
			
		}
	}

	private void pozeniActivity()
	{
		Intent moj=new Intent(this, SeznamNarocil.class);
		startActivity(moj);
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
					AndroidHttpTransport aht=new AndroidHttpTransport(URL,35000);
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
					Log.e("Napak v polnjenju trgovin ",e.toString());
					}
					
				} catch (Exception e) {
					System.out.println("XML Pasing Excpetion = " + e);
					
					
				}finally {
	               
	            }
			}
	}

	private void kakoZIZdelki()
	{
		Log.e("Preverim ali je tabela polna", "Preverim ali je tabela polna");
		Message msg= Message.obtain();
		
		if(imamoIzdelke!=null)
		{
				try {
					
					 msg.arg1=3;
                     mHandler.sendMessage(msg);
                     Log.e("Posodobim podatke", "posodobim podatke");
                     SoapObject Request =new SoapObject(NAMESPACE,METHOD_NAME_NAZADNJE_SPREMENJENE);
                     Request.addProperty("datum",imamoIzdelke);	
					
                     SoapSerializationEnvelope soapEnvelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
                     soapEnvelope.dotNet=false;
                     soapEnvelope.setOutputSoapObject(Request);	
                     AndroidHttpTransport aht=new AndroidHttpTransport(URL,3000);	
                     
					try{
					
						aht.call(SOAP_ACTION_PRIDOBI_POSODOBI,soapEnvelope);	
						SoapPrimitive result =(SoapPrimitive)soapEnvelope.getResponse(); 	
					
						String vmesni=result.toString();
						prvaRazdelitev=vmesni.split("\n");
					
						SharedPreferences sharedPreferences = this.getSharedPreferences("IZDELKI", MODE_PRIVATE);
						SharedPreferences.Editor editor1 = sharedPreferences.edit();
						prvaRazdelitev[prvaRazdelitev.length-1]=prvaRazdelitev[prvaRazdelitev.length-1].replace('-', '/');
					
						editor1.putString("IZDELKI", prvaRazdelitev[prvaRazdelitev.length-1]);
						editor1.commit();
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
}


	



