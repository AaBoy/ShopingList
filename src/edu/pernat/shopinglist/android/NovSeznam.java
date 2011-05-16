package edu.pernat.shopinglist.android;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class NovSeznam extends Activity {
    /** Called when the activity is first created. */
	/*Globalne*/
	private Menu mMenu;  //ni nujno
	public static final int DIALOG_VPIS=0;
	public static final int DIALOG_POSLJI=1;
	/*Konec globalnih*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nov_seznam);
        
        
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

      mMenu = menu; //ni nujno
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.nov_seznam_meni, mMenu);
      return true;

    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.Shrani:
    	  Toast.makeText(this,"Shranjeno", Toast.LENGTH_SHORT).show();
    	  return true;
    	  
      case R.id.Clear:
    	  Toast.makeText(this,"Izbrisano!", Toast.LENGTH_SHORT)
          .show();
    	  return true;

      case R.id.Poslji:
      
    	  showDialog(DIALOG_POSLJI);
      return true;
    	  
      default:// Generic catch all for all the other menu resources
        if (!item.hasSubMenu()) {
          Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
          return true;
        }
        break;
      }
      return false;

    }
    
    
  protected Dialog onCreateDialog(int id) {
    	
        switch(id) {
        case DIALOG_VPIS:
        	
        	//setContentView(R.layout.vpis);
        	Context mContext = this;
        	Dialog dialog = new Dialog(mContext);

        	dialog.setContentView(R.layout.spremeni_izdelek);
        	dialog.setTitle("Spremeni izdelek!");
        	
			return dialog;
		
        case DIALOG_POSLJI:
        	Context mContext1 = this;
        	Dialog dialog1 = new Dialog(mContext1);

        	dialog1.setContentView(R.layout.dostava);
        	dialog1.setTitle("Spremeni izdelek!");
        	
        	return dialog1;
			
        default:
            break;
        }
        
        return null;
    }

    
    public boolean odpriArtikel(View v) {
      switch (v.getId()) {
      case R.id.button1:
      {  
    	  showDialog(DIALOG_VPIS);
    	  return true;
      }

      default:// Generic catch all for all the other menu resources
          return true;
          
        }

    }
    /*Konec menija*/
    
}
