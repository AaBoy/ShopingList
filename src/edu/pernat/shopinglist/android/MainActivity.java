package edu.pernat.shopinglist.android;



import java.io.PrintWriter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import edu.pernat.shopinglist.android.maps.KjeSemActivity;


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
    			 global.stSeznama=-1;
    			Intent moj=new Intent(this, NovSeznam.class);
    			this.startActivity(moj);
    			break;
    		}
    		
    		case R.id.gumbKamera:
    		
    		{
    			
    			
    			Intent moj=new Intent(this, Kamera.class);
    			this.startActivity(moj);
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
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.Vpis:
    	  
    	  showDialog(DIALOG_VPIS);
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