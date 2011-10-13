package edu.pernat.shopinglist.android;

import java.util.ArrayList;

import edu.pernat.shopinglist.android.razredi.Seznam;
import edu.pernat.shopinglist.android.razredi.Seznami;
import android.R.bool;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SeznamNarocil extends ListActivity implements OnItemClickListener  {
	
	GlobalneVrednosti app;
	private Menu mMenu;  //ni nujno
	public static final int DIALOG_GLAVNI_MENI=0;
	public static final int DIALOG_USTVARI_SEZNAM=1;
	
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stevec_list_activity);
        this.setRequestedOrientation(1);
        app=(GlobalneVrednosti) getApplication();
        setListAdapter(app.seznamList);
		this.getListView().setOnItemClickListener(this);
		

		//Toast.makeText(this, "Pritisnili ste:", Toast.LENGTH_LONG).show();

	}
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		//Toast.makeText(this, "Pritisnili ste: "+, Toast.LENGTH_LONG).show();
		app.stSeznama=position;
		Intent moj=new Intent(this, NovSeznam.class);
		this.startActivity(moj);
	}


	@Override
    public void onResume() {
		super.onResume();
        app.seznamList.notifyDataSetChanged();
    }
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {

	      mMenu = menu; //ni nujno
	      MenuInflater inflater = getMenuInflater();
	      inflater.inflate(R.menu.glavni_menu, mMenu);
	      return true;

	    }

	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	      
	      case R.id.glavni_meni:
	      
	    	  Intent moj1=new Intent(this, MainActivity.class);
  			this.startActivity(moj1);
  			
	      return true;
	      
	      case R.id.nov_seznam2:
	    	  Intent moj=new Intent(this, NovSeznam.class);
  			this.startActivity(moj);
	    	  
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

}
    

