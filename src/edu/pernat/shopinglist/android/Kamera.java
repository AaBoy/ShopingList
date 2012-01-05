package edu.pernat.shopinglist.android;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import edu.pernat.shopinglist.android.razredi.NovSeznamArtiklov;



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
         String [] izdelki=contents.split("\n");
         String [] naDvaDela=new String[2];
         app.newNovSeznam();
        
         try {
        	 for(int i=0;i<izdelki.length-1;i++)
             {
            	 naDvaDela=izdelki[i].split(";");
            	 app.selectIzdelek(naDvaDela[0].trim(), naDvaDela[1].trim());
             }
         
          
        	
	  		 final Calendar c = Calendar.getInstance();
	  	     app.novSeznam.setDatumNakupa(""+(c.get(Calendar.DATE))+". "+(c.get(Calendar.MONTH)+1)+". "+c.get(Calendar.YEAR));
	  	     app.novSeznam.setImeSeznama(izdelki[izdelki.length-1]);
	  	     app.dodajSeznamNaSeznam(app.novSeznam);
	  	     app.stSeznama=app.vsiSeznami.size()-1;
	        
	        	
      	    if(!app.novSeznamList.isEmpty())
    			app.novSeznamList = new NovSeznamArrayAdapter(this,R.layout.nov_seznam, app.novSeznam.getNovSeznamArtiklov(),app);
      	    
      	    app.napolniBazoSeznamov();
          
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(Kamera.this,"Nepravilna QR coda",Toast.LENGTH_LONG).show();
		}
        

         
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
 	   if (requestCode == 0) {
 	      if (resultCode == RESULT_OK) {
 	    	 ustvariSeznam(intent);
 	    	onBackPressed();
 	    	Intent moj=new Intent(this, NovSeznam.class);
			this.startActivity(moj);
			
 	         // Handle successful scan
 	      } else if (resultCode == RESULT_CANCELED) {
 	         // Handle cancel
 	    	  onBackPressed();
 	    	  onBackPressed();
 	    	  onStop();
 	    	  finish();
 	    	 Toast.makeText(Kamera.this,"Trenutno ne deluje",Toast.LENGTH_LONG).show();
 	    	
 	      }
 	   }
 	}
    
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	super.onBackPressed();
    }
}

