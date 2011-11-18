package edu.pernat.shopinglist.android;



import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;


import edu.pernat.shopinglist.android.maps.KjeSemActivity;
import edu.pernat.shopinglist.android.razredi.Seznam;
import edu.pernat.shopinglist.android.razredi.Uporabniki;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.AlteredCharSequence;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	ProgressDialog dialogWait;

	GlobalneVrednosti global=new GlobalneVrednosti();
	/** Called when the activity is first created. */
	public static final String FILE_NAME_USER="uporabnisko";
	public static final String FILE_NAME_PASS="geslo";
	public static final String PIME="NAME";
	public static final String PPASS="PASSWORD";
	public static final int DIALOG_VPIS=0;
	private Menu mMenu; 
	private  PrintWriter  myUsername,myPass;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.setRequestedOrientation(1);
    	global=(GlobalneVrednosti) getApplication();
    	
    	global.fillFromDBIzdelki();
		//global.init();
    	
//    	if(isNetworkAvailable())
//    	{
//    		Toast.makeText(MainActivity.this,"Je internet",Toast.LENGTH_LONG).show();
//    		MojTask mt = new MojTask();
//    		mt.execute(1);
//    	}else
//    	{
//    		global.fillFromDBIzdelki();
//    		global.init();
//    	}

    }

	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
    
	private class MojTask extends AsyncTask<Integer, Void, Long> {
		@Override
		protected void onPreExecute() {
			dialogWait = 
				ProgressDialog.show(MainActivity.this, "", "Delam! Pridobivam cenik...", true);
		}
		protected Long doInBackground(Integer... prviArgument) {
			long totalSize = 0;
			int t1=prviArgument[0];
			
			totalSize = 43; //namišljeni rezultat 
			//global.novSeznam.add(new Seznam("koga", global.seznamArtiklov.get(0)));
			try {
				
				/** Handling XML */
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();

				/** Send URL to parse XML Tags */
				URL sourceUrl = new URL(
						"http://shoppinglistandroid.netii.net/izdelki.xml");

				/** Create handler to handle XML Tags ( extends DefaultHandler ) */
				MyXMLHandler myXMLHandler = new MyXMLHandler(global);
				xr.setContentHandler(myXMLHandler);
				xr.parse(new InputSource(sourceUrl.openStream()));
				
			} catch (Exception e) {
				System.out.println("XML Pasing Excpetion = " + e);
			}
	    	
			//global.init();
			return totalSize;
		}

		protected void onPostExecute(Long tretjiArgument) {
			
			//global.novSeznamList.notifyDataSetChanged();
			//Toast.makeText(Main.this,"Rezultat:"+tretjiArgument,Toast.LENGTH_LONG).show();
			dialogWait.cancel();
		}
	}
    
    
    public void odpriVpis(View v)
    {
    	
    	if(v.getId()==R.id.button1)
    	{	
    		Intent moj=new Intent(this, NovSeznam.class);
			this.startActivity(moj);
    		//this.startActivityForResult(moj, 0);
    	}
    }
    
    public void odpriOkno(View v)
    {
    	//Toast.makeText(this, "Sem stisnil gumb", Toast.LENGTH_SHORT)
        //.show();
    	switch(v.getId())
    	{
    		
    		case R.id.googleMaps:
    		{
    			Intent moj=new Intent(this, KjeSemActivity.class);
    			this.startActivity(moj);
    			break;
    		}
    	
    		case R.id.zgodovinaSeznamov:
    		{
    			
    			Intent moj=new Intent(this, SeznamNarocil.class);
    			this.startActivity(moj);
    			
    			break;
    		}
    		
    		case R.id.novSeznam:
    		{
    			/*MojTask mt = new MojTask();
    			mt.execute(50);*/
    			Intent moj=new Intent(this, NovSeznam.class);
    			this.startActivity(moj);
    			break;
    		}
    		
    		case R.id.gumbKamera:
    		
    		{
    			
    			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            	intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            	startActivityForResult(intent, 0);
//    			Intent moj=new Intent(this, Kamera.class);
//    			this.startActivity(moj);
    			break;
    			
    		}
    		
    	}
    	//Toast.makeText(this, "Sem v seznam narocil", Toast.LENGTH_SHORT).show();
    	
    }
    
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
 	   if (requestCode == 0) {
 	      if (resultCode == RESULT_OK) {
 	         String contents = intent.getStringExtra("SCAN_RESULT");
 	         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
 	         
 	         //Log.d("Izpis", contents);
 	      Toast.makeText(this, contents, Toast.LENGTH_SHORT)
 	        .show();
 	         // Handle successful scan
 	      } else if (resultCode == RESULT_CANCELED) {
 	         // Handle cancel
 	      }
 	   }
 	}

    @Override   
    public void onStart()
    {
    	super.onStart();
    	SharedPreferences settings = getSharedPreferences(FILE_NAME_USER,0); //pref odprem preferences
		global.uporabnisko = settings.getString(PIME, ""); //pref preberem staro vrednost

		settings = getSharedPreferences(FILE_NAME_PASS,0); //pref odprem preferences
		global.geslo = settings.getString(PPASS, ""); //pref preberem staro vrednost
    }
    
    @Override
	public void onResume() { //pref predno user vidi nastavim prave vrednosti
		super.onResume();
		SharedPreferences settings = getSharedPreferences(FILE_NAME_USER,0); //pref odprem preferences
		global.uporabnisko = settings.getString(PIME, ""); //pref preberem staro vrednost

		settings = getSharedPreferences(FILE_NAME_PASS,0); //pref odprem preferences
		global.geslo = settings.getString(PPASS, ""); //pref preberem staro vrednost
		
		//app.stInc.setStanje(tmp); //pref nastavim staro vrednost (od tod naprej šteje)
		
		
	}
	@Override
	public void onPause() { //pref uporabnik ali OS zapusti pogled, potrebno shranit
		super.onPause();
    	SharedPreferences settings =getSharedPreferences(FILE_NAME_USER, 0);
    	SharedPreferences.Editor editor = settings.edit();
		editor.putString(PIME, global.uporabnisko);
		editor.commit();
		
		settings =getSharedPreferences(FILE_NAME_PASS, 0);
    	editor = settings.edit();
		editor.putString(PPASS, global.geslo);
		editor.commit();
	}
    
    @Override
    public void onStop()
    {
    	super.onStop();

    	SharedPreferences settings =getSharedPreferences(FILE_NAME_USER, 0);
    	SharedPreferences.Editor editor = settings.edit();
		editor.putString(PIME, global.uporabnisko);
		editor.commit();
		
		settings =getSharedPreferences(FILE_NAME_PASS, 0);
    	editor = settings.edit();
		editor.putString(PPASS, global.geslo);
		editor.commit();

    	//myName.println(app.ime);
    }
    
    /*Menu in dialog*/
    protected Dialog onCreateDialog(int id) {
    	
        switch(id) {
        case DIALOG_VPIS:
        	
        	//setContentView(R.layout.vpis);
        	Context mContext = this;
        	Vpis dialog = new Vpis(mContext,global);


        	/*TextView text = (TextView) dialog.findViewById(R.id.text);
        	text.setText("Hello, this is a custom dialog!");
        	ImageView image = (ImageView) dialog.findViewById(R.id.image);
        	image.setImageResource(R.drawable.android);*/
        	
        	
			return dialog;
			
			
        default:
            break;
        }
        
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

      mMenu = menu; //ni nujno
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.main_menu, mMenu);
      return true;

    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.Vpis:
    	  
    	  showDialog(DIALOG_VPIS);
    	  return true;
      case R.id.ShraniSpremembeNaStreznik:
    	  
    	  Intent moj=new Intent(this, MakingXMLFIle.class);
			this.startActivity(moj);
    	  return true;
    
    
      default:// Generic catch all for all the other menu resources
        if (!item.hasSubMenu()) {
          Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT)
          .show();
          return true;
        }
        break;
      }
      return false;
    }
    /*Konec menija*/
    
    
    
    
    
    
    //Parser

    
    
    
    
    
    
}