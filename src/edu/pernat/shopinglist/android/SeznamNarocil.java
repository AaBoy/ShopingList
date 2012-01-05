package edu.pernat.shopinglist.android;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import edu.pernat.shopinglist.android.quickaction.ActionItem;
import edu.pernat.shopinglist.android.razredi.NovSeznamArtiklov;
import edu.pernat.shopinglist.android.quickaction.QuickAction;

public class SeznamNarocil extends ListActivity implements OnItemClickListener,OnItemLongClickListener  {
	
	GlobalneVrednosti app;
	private Menu mMenu;  //ni nujno
	public static final int DIALOG_GLAVNI_MENI=0;
	public static final int DIALOG_USTVARI_SEZNAM=1;
	public static final int DIALOG_PREIMENUJ=1;
	public static final int DIALOG_POSLJI=2;
	//za quickaction
	private static final int ID_BRISI = 1;
	private static final int ID_POSLJI = 2;
	private static final int ID_SPREMENI_IME = 3;
	private int mSelectedRow = 0;
	QuickAction mQuickAction;
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.stevec_list_activity);
        this.setRequestedOrientation(1);
        app=(GlobalneVrednosti) getApplication();
        setListAdapter(app.seznamList);
		this.getListView().setOnItemClickListener(this);
		this.getListView().setOnItemLongClickListener(this);
		//this.getListView().setOnItemLongClickListener(this);
		registerForContextMenu(getListView());
		
	     ActionItem addItem 		= new ActionItem(ID_BRISI, "Izbriši", getResources().getDrawable(R.drawable.file_delete_icon));
		 ActionItem acceptItem 	= new ActionItem(ID_POSLJI, "Pošlji", getResources().getDrawable(R.drawable.email_icon));
	     ActionItem uploadItem 	= new ActionItem(ID_SPREMENI_IME, "Spremeni ime", getResources().getDrawable(R.drawable.name_help_con));
	     
	     mQuickAction 	= new QuickAction(this);	
	     mQuickAction.addActionItem(addItem);
	     mQuickAction.addActionItem(acceptItem);
	     mQuickAction.addActionItem(uploadItem);
	   //setup the action item click listener
		 mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
				
				public void onItemClick(QuickAction quickAction, int pos, int actionId) {
					ActionItem actionItem = quickAction.getActionItem(pos);
					
					if (actionId == ID_BRISI) { //Add item selected
						show_alert();
					} else if(actionId==ID_POSLJI){
						showDialog(DIALOG_POSLJI);
					}else if(actionId==ID_SPREMENI_IME)
					{
						napolniSeznam();
			      		showDialog(DIALOG_PREIMENUJ);
			      		app.seznamList.notifyDataSetChanged();
					}
				}
			});
			
			//setup on dismiss listener, set the icon back to normal
			mQuickAction.setOnDismissListener(new PopupWindow.OnDismissListener() {			
				public void onDismiss() {
				}
			});
	}

    
    @Override
    public void onStop()
    {
    	super.onStop(); 	
    }
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    }
    
    @Override   
    public void onStart()
    {
    	super.onStart();
    	
    	if(app.stSeznama!=-1)
    	{
    		app.vsiSeznami.getUstvarjeniSezname().get(app.stSeznama).sestejCeno();
    	}

         if(app.vsiSeznami.size()==0 && app.seznamArtiklov.size()==0)
         {
        	app.seznamArtiklov.clear();
         	app.vsiSeznami.ustvarjeniSeznami.clear();
         	app.seznamTrgovin.clear();
          	
         		if(app.obstajaIzdelkiTabela())
         		{
         			app.fillFromDBIzdelki();
         		}
         		if(app.obstajaTrgovinaTabela())
         		{
         			app.fillFromDBTrgovina();
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
 
	    protected Dialog onCreateDialog(int id) {
	    	
	        switch(id) {   	
	        case DIALOG_PREIMENUJ:  
	        	Context mContext4=this;
	        	ShraniImeSeznama dialog4=new ShraniImeSeznama(mContext4, app,this);
	        	return dialog4;
	        case DIALOG_POSLJI:  
	        	Context mContext=this;
	        	Dostava dialog=new Dostava(mContext, app);
	        	return dialog;
	        
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

	   
		public boolean onItemLongClick(AdapterView<?> arg0, View view,int position, long arg3) {
			app.stSeznama=position;
			mSelectedRow=position;
			mQuickAction.show(view);
			return false;
		}
}
    

