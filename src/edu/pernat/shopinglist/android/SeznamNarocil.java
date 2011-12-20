package edu.pernat.shopinglist.android;

import java.util.ArrayList;

import edu.pernat.shopinglist.android.razredi.NovSeznamArtiklov;
import edu.pernat.shopinglist.android.razredi.Seznami;
import android.R.bool;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
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
        kolikoIzbranih();
        setListAdapter(app.seznamList);
		this.getListView().setOnItemClickListener(this);

	}
    
    void kolikoIzbranih()
    {
    	for(int i=0;i<app.vsiSeznami.getUstvarjeniSezname().size();i++)
    	{
    		app.vsiSeznami.getSteviloOznacenihArtiklov(i);
    	}
    	
    }
    
    @Override
    public void onStop()
    {
    	super.onStop();
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
	    SharedPreferences.Editor editor1 = sharedPreferences.edit();
	    editor1.putString("BAZA", "polna");
	    editor1.commit();

    	//myName.println(app.ime);
    }
    @Override   
    public void onStart()
    {
    	super.onStart();
    	SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
	    String strSavedMem1 = sharedPreferences.getString("BAZA", "");
	    //Toast.makeText(SeznamNarocil.this,strSavedMem1,Toast.LENGTH_LONG).show();
	    app.seznamList.notifyDataSetChanged();
    
    }
    
	@Override
	public void onPause() { //pref uporabnik ali OS zapusti pogled, potrebno shranit
		super.onPause();
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
	    SharedPreferences.Editor editor1 = sharedPreferences.edit();
	    editor1.putString("BAZA", "prazna");
	    editor1.commit();
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
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
	    String strSavedMem1 = sharedPreferences.getString("BAZA", "");
	    //Toast.makeText(SeznamNarocil.this,strSavedMem1,Toast.LENGTH_LONG).show();
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
	    	  app.stSeznama=-1;
	    	  Intent moj=new Intent(this, NovSeznam.class);
  			this.startActivity(moj);
	    	  
	    	  return true;
	    	  
	      default:// Generic catch all for all the other menu resources
	        if (!item.hasSubMenu()) {
	          //Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
	          return true;
	        }
	        break;
	      }
	      return false;

	    }

}
    

