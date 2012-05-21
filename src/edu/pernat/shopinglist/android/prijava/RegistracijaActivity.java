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

import edu.pernat.shopinglist.android.GlobalneVrednosti;
import edu.pernat.shopinglist.android.R;
import edu.pernat.shopinglist.android.razredi.PrijavniPodatki;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistracijaActivity extends Activity{
	Button prijava, preklic;
	EditText uporabnisko, geslo;
	GlobalneVrednosti app;
	private final String PRIJAVA_SHARE_PREF="UPORABNISKI_PODATKI";
	private final String UPORABNIŠKO="UPORABNISKO";
	private final String GESLO="GESLO";
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
							shraniVSharePref();
							app.setPrijavniPodatki(new PrijavniPodatki(uporabnisko.getText().toString(), kriptiraj(geslo.getText().toString())));
							
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
			
			DESKeySpec keySpec;
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
					String encrypedPwd = Base64.encodeToString(cipher.doFinal(cleartext), 0);
					
				

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
				
			
			return"";
	}
	private void shraniVSharePref()
	{
			SharedPreferences sharedPreferences =this.getSharedPreferences("UPORABNISKI_PODATKI", MODE_PRIVATE);
			SharedPreferences.Editor editor1 = sharedPreferences.edit();
			editor1.putString("UPORABNISKO", uporabnisko.toString()); 
			editor1.putString("GESLO", kriptiraj(geslo.toString()));
	}
}
