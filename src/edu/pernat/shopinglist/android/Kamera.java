package edu.pernat.shopinglist.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;
import edu.pernat.shopinglist.android.razredi.Artikli;
import edu.pernat.shopinglist.android.razredi.Seznam;
import edu.pernat.shopinglist.android.razredi.Seznami;



public class Kamera extends Activity {
   
	GlobalneVrednosti app;
	Boolean prvic=true;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        app=(GlobalneVrednosti) getApplication();  
        if(prvic==true)
        {
	        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
	    	intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
	    	startActivityForResult(intent, 0);
	    	prvic=false;
        }

    }
    
    private void ustvariSeznam(Intent tmp)
    {
    	 String contents = tmp.getStringExtra("SCAN_RESULT");
         String format = tmp.getStringExtra("SCAN_RESULT_FORMAT");
         
         String [] izdelki=contents.split(";");
         app.novSeznam.clear();
         for(int i=0;i<izdelki.length;i++)
         {
        	 
        	 app.novSeznam.add(new Seznam("Jiha", new Artikli(1, 2.1, izdelki[i], "250 g")));
         }
         app.novSeznam.get(0).imeSeznama="Recept";
         app.vsiSeznami.add(new Seznami(app.novSeznam));
         app.novSeznam.clear();
         Log.d("Izpis", contents);
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
 	   if (requestCode == 0) {
 	      if (resultCode == RESULT_OK) {
 	    	 ustvariSeznam(intent);
 	    	 
 	    	Intent moj=new Intent(this, MainActivity.class);
			this.startActivity(moj);
			
 	         // Handle successful scan
 	      } else if (resultCode == RESULT_CANCELED) {
 	         // Handle cancel
 	    	 Toast.makeText(Kamera.this,"Trenutno ne deluje",Toast.LENGTH_LONG).show();
 	    	
 	      }
 	   }
 	}
    
}

