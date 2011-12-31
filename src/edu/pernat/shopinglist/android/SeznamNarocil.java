package edu.pernat.shopinglist.android;

import java.util.List;

import edu.pernat.shopinglist.android.razredi.NovSeznamArtiklov;
import edu.pernat.shopinglist.android.razredi.Seznami;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SeznamNarocil extends ListActivity implements OnItemClickListener  {
	
	GlobalneVrednosti app;
	private Menu mMenu;  //ni nujno
	public static final int DIALOG_GLAVNI_MENI=0;
	public static final int DIALOG_USTVARI_SEZNAM=1;
	public static final int DIALOG_PREIMENUJ=1;
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.stevec_list_activity);
        this.setRequestedOrientation(1);
        app=(GlobalneVrednosti) getApplication();
        setListAdapter(app.seznamList);
		this.getListView().setOnItemClickListener(this);
		//this.getListView().setOnItemLongClickListener(this);
		registerForContextMenu(getListView());

	}

    @Override
    public void onStop()
    {
    	super.onStop();
//    	app.napolniVmesno();
    	
    }
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
//    	app.newNovSeznam();
//    	app.newVsiArtikli();
//    	app.newVsiSeznami();
    }
    
    @Override   
    public void onStart()
    {
    	super.onStart();
    	app.seznamList.notifyDataSetChanged();
    }
    
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		//Toast.makeText(this, "Pritisnili ste: "+, Toast.LENGTH_LONG).show();
		app.stSeznama=position;
		Log.e("jej", app.vsiSeznami.size()+"");
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
///////////****************************************************context menu
	    @Override
	    public boolean onContextItemSelected(MenuItem item) {
	      AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	      	
	      	
	      	if(item.getTitle()=="Preimenuj")
	      	{
	      		app.stSeznama=info.position;
	      		napolniSeznam();
	      		Log.e("Position",""+ info.position);
	      		showDialog(DIALOG_PREIMENUJ);
	      		app.seznamList.notifyDataSetChanged();
	      		
	      		
	      	}
	      	else
	      	{
	      		app.stSeznama=info.position;
	      		show_alert();
	      	}
	      
	      return true;
	    }
	    @Override
	    public void onCreateContextMenu(ContextMenu menu, View v,
	        ContextMenuInfo menuInfo) {
	      if (v.getId()==getListView().getId()) {
	        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	        menu.setHeaderTitle(app.vsiSeznami.getImeNovegaSeznama(info.position));
	        String[] menuItems = {"Izbriši","Preimenuj"};
	        for (int i = 0; i<menuItems.length; i++) {
	          menu.add(Menu.NONE, i, i, menuItems[i]);
	        }
	      }
	    }
	    
	    protected Dialog onCreateDialog(int id) {
	    	
	        switch(id) {   	
	        case DIALOG_PREIMENUJ:  
	        	Context mContext4=this;
	        	ShraniImeSeznama dialog4=new ShraniImeSeznama(mContext4, app);
	        	return dialog4;
	        
	        default:
	            break;
	        }
	        
	        return null;
	    }
	    
	    
	    private void show_alert() {
	    	// TODO Auto-generated method stub
	    	 AlertDialog.Builder alert_box=new AlertDialog.Builder(this);
	    	 alert_box.setMessage("Zares želite izbrisast seznam?");
	    	 alert_box.setPositiveButton("DA",new DialogInterface.OnClickListener() {
	    	 public void onClick(DialogInterface dialog, int which) {
	    		 app.vsiSeznami.removNovSeznam(app.stSeznama);
	    		 app.stSeznama=-1;
		      	 app.seznamList.notifyDataSetChanged();
		      	 Toast.makeText(getApplicationContext(), "Izbrisan element", Toast.LENGTH_LONG).show();
	    		}
	    	 });
	    	 alert_box.setNegativeButton("NE", new DialogInterface.OnClickListener() {
	    	 public void onClick(DialogInterface dialog, int which) {
	    		 Toast.makeText(getApplicationContext(), "Nisem izbrisal seznama", Toast.LENGTH_LONG).show();
	    	 }
	    	 });

	    	 alert_box.show();
	    }
	    public void napolniSeznam()
	    {
	    	app.novSeznam=new NovSeznamArtiklov();   	
	    	int meja=(int) app.vsiSeznami.getUstvarjeniSezname().get(app.stSeznama).getNovSeznamArtiklov().size();	

	    	for(int x=0;x<meja;x++)
	    	{
	    		app.dodajArtikelNaSeznam(app.vsiSeznami.getUstvarjeniSezname().get(app.stSeznama).getNovSeznamArtiklov().get(x),app.vsiSeznami.getUstvarjeniSezname().get(app.stSeznama).jeOznacen(x));
	    		//app.novSeznamList.add(app.vsiSeznami.getUstvarjeniSezname().get(app.stSeznama).getNovSeznamArtiklov().get(x));
	    		
	    	}
	    	app.novSeznam.setImeSeznama(app.vsiSeznami.getUstvarjeniSezname().get(app.stSeznama).getImeSeznama());
	    }
}
    

