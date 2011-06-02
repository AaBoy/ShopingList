package edu.pernat.shopinglist.android;

import java.util.ArrayList;

import edu.pernat.shopinglist.android.razredi.Seznam;
import edu.pernat.shopinglist.android.razredi.Seznami;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NovSeznam extends ListActivity implements OnItemClickListener,OnClickListener{
    /** Called when the activity is first created. */
	/*Globalne*/
	
	GlobalneVrednosti app;
	Button spreIzde,dodajIzdelek;
	private Menu mMenu;  //ni nujno
	public static final int DIALOG_SPREMENI=0;
	public static final int DIALOG_POSLJI=1;
	public static final int DIALOG_DODAJ_IZDELEK=2;
	int izbranIzdelek;
	/*Konec globalnih*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nov_seznam_list_activity);
        //spreIzde=(Button)findViewById(R.id.spremeniIzdelek);
        app=(GlobalneVrednosti) getApplication();
        dodajIzdelek=(Button)findViewById(R.id.dodajIzdelek);
        dodajIzdelek.setOnClickListener(this);
        
        /*for(int i=0;i<10;i++)
    	app.dodaj(new RazredBaza("Kuku","lele"));*/
        
        setListAdapter(app.novSeznamList);
		this.getListView().setOnItemClickListener(this);
		setResult(RESULT_OK);
		
		
		
		
		if(app.stSeznama!=-1)
			{
				app.newNovSeznam();
				napolniSeznam();
				
			}
		
	
    }
    
    
    public void napolniSeznam()
    {
    	app.newNovSeznam();	
    	
    	int meja=app.vsiSeznami.get(app.stSeznama).getSize(0);
    	for(int x=0;x<meja;x++)
    	{
    		
    		
    		//Toast.makeText(this, "Število elementov: "+app.vsiSeznami.get(app.stSeznama).getSize(0), Toast.LENGTH_LONG).show();
    		app.dodajIzdelek(app.vsiSeznami.get(app.stSeznama).vrsniSeznam(x).getArtikel());
    		//app.novSeznam.add(app.vsiSeznami.get(app.stSeznama).vrsniSeznam(1));
    		
    	}
    	
    	
    	if(app.novSeznam.size()!=0) app.novSeznam.get(0).imeSeznama="Janez kaj";
    	
    	app.novSeznamList.notifyDataSetChanged();
    }
    
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		Toast.makeText(this, "Pritisnili ste:"+position, Toast.LENGTH_LONG).show();
		izbranIzdelek=position;
		showDialog(DIALOG_SPREMENI);
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
    	  
    	  if( app.novSeznam.size()>0)
    	  {
    		  if(app.stSeznama!=-1)
    		  app.vsiSeznami.remove(app.stSeznama);
    		  
    		  
    		  app.novSeznam.get(0).imeSeznama="Novi seznam";
    		  app.vsiSeznami.add(new Seznami( app.novSeznam));
    		  //app.novSeznam.clear();
    	  }
    	  
    	  
    	  
    	  this.finish();
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
        case DIALOG_SPREMENI:
        	Context mContext = this;
        	SpremeniIzdelek dialog = new SpremeniIzdelek(mContext,app,izbranIzdelek );
        	
			return dialog;
		
        case DIALOG_POSLJI:
        	Context mContext1 = this;
        	Dostava dialog1 = new Dostava(mContext1,app);      	
        	return dialog1;
			
        case DIALOG_DODAJ_IZDELEK:
        	Context mContext2=this;
        	DodajIzdelek dialog2=new DodajIzdelek(mContext2,app);
        	
        	return dialog2;
        	
        default:
            break;
        }
        
        return null;
    }


	public void onClick(View v) {
		
		switch (v.getId())
		{
			case R.id.dodajIzdelek:
				showDialog(DIALOG_DODAJ_IZDELEK);
		
		}
		
	}
	
	@Override
	public void finish()
	{
		super.finish();
		app.stSeznama=-1;
		app.newNovSeznam();
		app.novSeznamList.notifyDataSetChanged();
		app.seznamList.notifyDataSetChanged();
		
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		app.stSeznama=-1;
		app.newNovSeznam();
		app.novSeznamList.notifyDataSetChanged();
		app.seznamList.notifyDataSetChanged();
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		app.stSeznama=-1;
		
		
		app.newNovSeznam();
		app.novSeznamList.notifyDataSetChanged();
		app.seznamList.notifyDataSetChanged();
	}





























    
}
