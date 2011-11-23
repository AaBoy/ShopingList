package edu.pernat.shopinglist.android;

import java.util.ArrayList;

import edu.pernat.shopinglist.android.razredi.Seznam;
import edu.pernat.shopinglist.android.razredi.Seznami;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NovSeznam extends ListActivity implements OnClickListener{
    /** Called when the activity is first created. */
	/*Globalne*/
	
	GlobalneVrednosti app;
	Button spreIzde,dodajIzdelek;
	CheckBox oznaceno;
	private Menu mMenu;  //ni nujno
	public static final int DIALOG_SPREMENI=0;
	public static final int DIALOG_POSLJI=1;
	public static final int DIALOG_DODAJ_IZDELEK=2;
	public static final int DIALOG_USTVARI_NOV_IZDELEK=3;
	public static final int DIALOG_IME_SEZNAMA=4;
	int izbranIzdelek;
	/*Konec globalnih*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nov_seznam_list_activity);
        //spreIzde=(Button)findViewById(R.id.spremeniIzdelek);
        app=(GlobalneVrednosti) getApplication();       
        //oznaceno=(CheckBox)findViewById(R.id.checkBox1);
        setListAdapter(app.novSeznamList);
        this.setRequestedOrientation(1);
		//this.getListView().setOnItemClickListener(this);
		
			
		if(app.stSeznama!=-1)
		{
			
				napolniSeznam();
		}
	
		dodajIzdelek=(Button)findViewById(R.id.dodajIzdelek);
        dodajIzdelek.setOnClickListener(this);
		
    }
    


    
    
    public void napolniSeznam()
    {
    	
    	/** Tukaj se zalomi  ali pa v SeznamNaročil**/
    	app.novSeznam=new ArrayList<Seznam>();
    	
    	int meja=(int) app.vsiSeznami.get(app.stSeznama).getSize(0);
    	
    	//app.novSeznam.clear();
    	for(int x=0;x<meja;x++)
    	{
    		app.dodajArtikelNaSeznam(app.vsiSeznami.get(app.stSeznama).vrsniSeznam(x).getArtikel());
    		app.novSeznamList.add(new Seznam("", app.vsiSeznami.get(app.stSeznama).vrsniSeznam(x).getArtikel()));
    	}
    	
    	app.novSeznam.get(0).imeSeznama=app.vsiSeznami.get(app.stSeznama).getImeSeznama(0);
    	app.novSeznam.get(0).imeSeznama="Nov seznam";
    	//app.novSeznamList.setNotifyOnChange(true);
       /// app.novSeznamList.notifyDataSetChanged();
    }
    
//    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
//		
//		izbranIzdelek=position;
//		
//		showDialog(DIALOG_SPREMENI);
//	}
    

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

    	  showDialog(DIALOG_IME_SEZNAMA);
    	  return true;
    	  
      case R.id.Clear:
    	  
    	  if(app.stSeznama!=-1)
        	  app.vsiSeznami.remove(app.stSeznama);

    	  app.stSeznama=-1;
    	  app.novSeznamList.clear();
    	  this.finish();
    	  Toast.makeText(this,"Izbrisano!", Toast.LENGTH_SHORT)
          .show();
    	  return true;

      case R.id.Poslji:
      
    	  showDialog(DIALOG_POSLJI);
    	  
      return true;
      
      case R.id.ustvariIzdelek:
    	   showDialog(DIALOG_USTVARI_NOV_IZDELEK);
    	  
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
        	app.stSeznama=-1;
        	
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
        	
        case DIALOG_USTVARI_NOV_IZDELEK:
        	Context mContext3=this;
        	UstvariNovIzdelek dialog3=new UstvariNovIzdelek(mContext3, app);
        	
        	return dialog3;
        	
        case DIALOG_IME_SEZNAMA:  
        	Context mContext4=this;
        	ShraniImeSeznama dialog4=new ShraniImeSeznama(mContext4, app,this);
        	Toast.makeText(this,"Shranjeno", Toast.LENGTH_SHORT).show();
        	
        	return dialog4;
        
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
		
		// TODO Auto-generated method stub
		
		
	}
	
	public void onFinish()
	{
		super.finish();
		app.stSeznama=-1;
		
		
	}
	@Override
	public void onBackPressed()
	{
		
		super.onBackPressed();
  	  if(!app.novSeznamList.isEmpty())
			app.novSeznamList.clear();
	}
	
	@Override
    public void onResume() {
		super.onResume();
    }



    
}