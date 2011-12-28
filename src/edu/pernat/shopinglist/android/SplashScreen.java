package edu.pernat.shopinglist.android;

import edu.pernat.shopinglist.android.razredi.Artikli;
import edu.pernat.shopinglist.android.razredi.Trgovina;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

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
	private static final String METHOD_NAME="NajcenejsiSeznam";
	private static final String NAMESPACE="http://projektIzdelki.pernat.edu";
	private static final String URL="http://164.8.118.232:8080/projketIzdelki/services/NajboljsiSeznam?wsdl";
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splash);
	    app=(GlobalneVrednosti) getApplication();
	    final SplashScreen sPlashScreen = this; 

	    // thread for displaying the SplashScreen
	    splashTread = new Thread() {
	        @Override
	        public void run() {
	            try {
	            	

	            		if(app.obstajaTrgovinaTabela())
	            		{
	            			app.sprazniBazoTrgovina();
	            			prvaRazdelitev=podatki.split("\n");
	            			for(int i=0;i<prvaRazdelitev.length;i++)
	            			{
	            				naDvaDela=prvaRazdelitev[i].split(";");
	            				app.dodajTrgovinoNaSeznam(new Trgovina(naDvaDela[0], naDvaDela[1]));
	            			}
	            		}
	            		else
	            		{
	            			app.fillFromDBTrgovina();
	            		}	
	            	
	            		if(app.obstajaIzdelkiTabela())
	            		{
	            			app.sprazniBazoTrgovina();
	            			prvaRazdelitev=izdelki.split("\n");
	            			for(int i=0;i<prvaRazdelitev.length;i++)
	            			{
	            				naPetDelov=prvaRazdelitev[i].split(";");
	            				app.artikelNaSeznamInBazo(new Artikli( Double.parseDouble( naPetDelov[2].replace(",", ".")), 
	            						naPetDelov[1], naPetDelov[3], naPetDelov[4],i));
	            			}
	            		}
	            		else
	            		{
	            			
	            			app.fillFromDBIzdelki();
	            			
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
	            finally {
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
	

}







