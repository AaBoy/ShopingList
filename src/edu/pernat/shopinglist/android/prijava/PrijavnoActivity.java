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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.pernat.shopinglist.android.GlobalneVrednosti;
import edu.pernat.shopinglist.android.R;
import edu.pernat.shopinglist.android.razredi.PrijavniPodatki;

public class PrijavnoActivity extends Activity implements OnClickListener{
	private static final String SOAP_ACTION_TRGOVINA="http://izdelki.shoopinglist.pernat.edua/Trgovine";
	private static final String SOAP_ACTION_PRIDOBI_IZ_BAZE="http://izdelki.shoopinglist.pernat.edua/pridobiIzbaze";
	private static final String SOAP_ACTION_PRIDOBI_POSODOBI="http://izdelki.shoopinglist.pernat.edua/pridobiIzbazeNazadnjeSpremenjene";
	
	private static final String NAMESPACE="http://izdelki.shoopinglist.pernat.edu";
	private static final String URL="http://192.168.1.6:8080/PridobiMerkatorIzdelkeSpletniServis/services/MainClass?wsdl";
	private static final String METHOD_NAME_PRIDOBI_IZ_BAZE="pridobiIzbaze";
	private static final String METHOD_NAME_TRGOVINE="Trgovine";
	private static final String METHOD_NAME_NAZADNJE_SPREMENJENE="pridobiIzbazeNazadnjeSpremenjene";
	private final String PRIJAVA_SHARE_PREF="UPORABNISKI_PODATKI";
	private final String UPORABNIŠKO="UPORABNISKO";
	private final String GESLO="GESLO";
	
	Button prijava, preklic;
	EditText uporabnisko, geslo;
	GlobalneVrednosti app;
	TextView registriraj;
	String kriptiranoGeslo;
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.prijavno_okno);
	        this.setRequestedOrientation(1);
	        final Context zaToast=this;
	        uporabnisko=(EditText)findViewById(R.id.editTextUpo);
	        geslo=(EditText)findViewById(R.id.editTextGeslo);
	        app=(GlobalneVrednosti)getApplication();
	        prijava=(Button)findViewById(R.id.buttonPrijava);
	        preklic=(Button)findViewById(R.id.buttonPreklici);
	        registriraj=(TextView)findViewById(R.id.textViewRegistriraj);
	        registriraj.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					finish();
					pozeniRegistrirajActivity();
				}
			});
	        
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
							kriptiranoGeslo=kriptiraj(geslo.toString());
							app.setPrijavniPodatki(new PrijavniPodatki(uporabnisko.getText().toString(), kriptiraj(geslo.getText().toString())));
							PrijaviUporabnika tt=new PrijaviUporabnika();
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
	public void pozeniRegistrirajActivity()
	{
		startActivity(new Intent(this,RegistracijaActivity.class));
	}
	public void onClick(View v) {

		
	}

	private String kriptiraj(String geslo)
	{
		
		DESKeySpec keySpec;
		String encrypedPwd="";
			try {
				TelephonyManager tManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
				String uid = tManager.getDeviceId();
				
				keySpec = new DESKeySpec(uid.getBytes("UTF8"));
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				SecretKey key = keyFactory.generateSecret(keySpec);
				
			
				// ENCODE plainTextPassword String
				byte[] cleartext = geslo.getBytes("UTF8");      
		
				Cipher cipher = Cipher.getInstance("DES"); // cipher is not thread safe
				cipher.init(Cipher.ENCRYPT_MODE, key);
				encrypedPwd = Base64.encodeToString(cipher.doFinal(cleartext), 0);
				
			

				SharedPreferences sharedPreferences = this.getSharedPreferences(PRIJAVA_SHARE_PREF, MODE_PRIVATE);
				SharedPreferences.Editor editor1 = sharedPreferences.edit();			
				editor1.putString(UPORABNIŠKO, app.getPrijavniPodatki().getUporabnisko());
				editor1.putString(GESLO, encrypedPwd);
				editor1.commit();
				
				Toast.makeText(this,encrypedPwd+"   "+geslo, Toast.LENGTH_SHORT).show();
				
				
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
	
	
	
	private class PrijaviUporabnika extends AsyncTask<Integer, Void, String> {
		protected String doInBackground(Integer... prviArgument) {
			
//			try {
//				 SoapObject Request =new SoapObject(NAMESPACE,METHOD_NAME_NAZADNJE_SPREMENJENE);
//                 Request.addProperty("uporabnisko",imamoIzdelke);	
//				
//                 SoapSerializationEnvelope soapEnvelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
//                 soapEnvelope.dotNet=false;
//                 soapEnvelope.setOutputSoapObject(Request);	
//                 AndroidHttpTransport aht=new AndroidHttpTransport(URL,3000);	
//                 
//				try{
//				
//					aht.call(SOAP_ACTION_PRIDOBI_POSODOBI,soapEnvelope);	
//					SoapPrimitive result =(SoapPrimitive)soapEnvelope.getResponse(); 	
//				
//					SharedPreferences sharedPreferences = this.getSharedPreferences("IZDELKI", MODE_PRIVATE);
//					SharedPreferences.Editor editor1 = sharedPreferences.edit();
//					
//				
//					editor1.putString("IZDELKI", prvaRazdelitev[prvaRazdelitev.length-1]);
//					editor1.commit();
//				
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//    	            
//				}catch(Exception  e)
//				{
//					Log.e("SplashScreen + Asinhroni taks", e.toString());
//				}
				
			
			return  "";
		}

		protected void onPostExecute(String tretjiArgument) {
		
			
		}
	}
	
	
}

