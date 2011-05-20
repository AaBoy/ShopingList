package edu.pernat.shopinglist.android;

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
	public static final int DIALOG_VPIS=0;
	public static final int DIALOG_POSLJI=1;
	public static final int DIALOG_DODAJ_IZDELEK=2;
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

    }
    
    
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		Toast.makeText(this, "Pritisnili ste:"+position, Toast.LENGTH_LONG).show();
		showDialog(DIALOG_VPIS);
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
        	SpremeniIzdelek dialog = new SpremeniIzdelek(mContext,new RazredBaza("Nkeaj", "Smrdi"));

        	/*dialog.setContentView(R.layout.spremeni_izdelek);
        	dialog.setTitle("Spremeni izdelek!");*/
        	
			return dialog;
		
        case DIALOG_POSLJI:
        	Context mContext1 = this;
        	Dostava dialog1 = new Dostava(mContext1);      	
        	return dialog1;
			
        case DIALOG_DODAJ_IZDELEK:
        	Context mContext2=this;
        	DodajIzdelek dialog2=new DodajIzdelek(mContext2);
        	
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
	
	// TODO Auto-generated method stub
	
}





























    
}
