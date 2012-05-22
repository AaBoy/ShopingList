package edu.pernat.shopinglist.android.prijava;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.pernat.shopinglist.android.GlobalneVrednosti;
import edu.pernat.shopinglist.android.R;
import edu.pernat.shopinglist.android.razredi.PrijavniPodatki;

public class RegistracijaActivity extends Activity{
	private static final String SOAP_ACTION_REGISTRACIJA="http://izdelki.shoopinglist.pernat.edua/Registriraj";
	private static final String METHOD_NAME_REGISTRACIJA="Registriraj";
	Button prijava, preklic;
	EditText uporabnisko, geslo;
	GlobalneVrednosti app;
	String kriptiranoGeslo;
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.registriraj_okno);
	        this.setRequestedOrientation(1);
	        final Context zaToast=this;
	        uporabnisko=(EditText)findViewById(R.id.editTextUpo);
	        geslo=(EditText)findViewById(R.id.editTextGeslo);
	        app=(GlobalneVrednosti)getApplication();
	        prijava=(Button)findViewById(R.id.buttonPrijava);
	        preklic=(Button)findViewById(R.id.buttonPreklici);
	        prijava.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					
					if(uporabnisko.getText().toString()!="" && geslo.getText().toString()!="")
					{
						if(uporabnisko.getText().toString().length()<4)
						{
							Toast.makeText(zaToast,"Imaste premalo uporabniško, vsaj 4 znake", Toast.LENGTH_SHORT).show();
						}
						else if(geslo.getText().toString().length()<4)
						{
							Toast.makeText(zaToast,"Imaste premalo geslo, vsaj 4  znake", Toast.LENGTH_SHORT).show();
						}
						else if((uporabnisko.getText().toString().length()>=4)&& (geslo.getText().toString().length()>=4))
						{
							kriptiranoGeslo=kriptiraj(geslo.getText().toString());
							
//							app.setPrijavniPodatki(new PrijavniPodatki(uporabnisko.getText().toString(), kriptiraj(geslo.getText().toString())));
							RegistracijaUporabnika tt=new  RegistracijaUporabnika();
							tt.execute(0);
						}
					}
					
				}
			});
	        preklic.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					uporabnisko.setText("");
					geslo.setText("");
					finish();
					
				}
			});
	 }
	private String kriptiraj(String geslo)
	{
			
			DESKeySpec keySpec;String encrypedPwd="";
				try {

					String uid = "1301305579";
					
					keySpec = new DESKeySpec(uid.getBytes("UTF8"));
					SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
					SecretKey key = keyFactory.generateSecret(keySpec);
					
				
					// ENCODE plainTextPassword String
					byte[] cleartext = geslo.getBytes("UTF8");      
			
					Cipher cipher = Cipher.getInstance("DES"); // cipher is not thread safe
					cipher.init(Cipher.ENCRYPT_MODE, key);
					encrypedPwd = Base64.encodeToString(cipher.doFinal(cleartext), 0);

				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			return encrypedPwd;
	}
	
	private class RegistracijaUporabnika extends AsyncTask<Integer, Void, String> {
		protected String doInBackground(Integer... prviArgument) {
			
			try {
				 SoapObject Request =new SoapObject(app.NAMESPACE,METHOD_NAME_REGISTRACIJA);
                 Request.addProperty("uporabnisko",uporabnisko.getText().toString());
                 Request.addProperty("geslo",kriptiranoGeslo);
                
                 SoapSerializationEnvelope soapEnvelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
                 soapEnvelope.dotNet=false;
                 soapEnvelope.setOutputSoapObject(Request);	
                 AndroidHttpTransport aht=new AndroidHttpTransport(app.URL,3000);	
                 
				try{
				
					aht.call(SOAP_ACTION_REGISTRACIJA,soapEnvelope);	
					SoapPrimitive result =(SoapPrimitive)soapEnvelope.getResponse(); 	
				
					if(result.toString()=="DA")
					{
						app.shraniGesloUporabniskoVShare(uporabnisko.getText().toString(), kriptiraj(geslo.getText().toString()));
						return "DA";
					}
					else
					{
						return "NE";
					}
				}catch(Exception e){
					e.printStackTrace();
				}
    	            
				}catch(Exception  e)
				{
					Log.e("SplashScreen + Asinhroni taks", e.toString());
				}
				
			
			return  "";
		}
		protected void onProgressUpdate(Integer integers) {
			if(integers==0);
			Toast.makeText(getApplicationContext(), "Uporabnisko že obstaja!", Toast.LENGTH_SHORT).show();
			}

		protected void onPostExecute(String tretjiArgument) {
			
			if(tretjiArgument=="NE")
			onProgressUpdate(0);
			else 
			{
				onProgressUpdate(1);
				koncaj();
			}
		}
	}
	
	private void koncaj()
	{
		this.finish();
	}
}