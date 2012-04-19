package edu.pernat.shopinglist.android;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;

import edu.pernat.shopinglist.android.quickaction.ActionItem;
import edu.pernat.shopinglist.android.quickaction.QuickAction;
import edu.pernat.shopinglist.android.razredi.NovSeznamArtiklov;

public class NovSeznam extends ListActivity implements OnClickListener,OnItemClickListener, OnItemLongClickListener{
    /** Called when the activity is first created. */
	/*Globalne*/
	
	GlobalneVrednosti app;
	Button spreIzde,dodajIzdelek;
	CheckBox oznaceno;
	private Menu mMenu;  //ni nujno
	public static final int DIALOG_SPREMENI=0;
	public static final int DIALOG_POSLJI=1;
	public static final int DIALOG_DODAJ_IZDELEK=2;
	public static final int DIALOG_IME_SEZNAMA=4;
	int izbranIzdelek;
	
	private static final int ID_BRISI = 1;
	private static final int ID_SPREMENI_CENO = 2;
	private int mSelectedRow = 0;
	QuickAction mQuickAction;
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
        registerForContextMenu(getListView());
        this.getListView().setOnItemClickListener(this);
		this.getListView().setOnItemLongClickListener(this);
        
        dodajIzdelek=(Button)findViewById(R.id.dodajIzdelek);
        dodajIzdelek.setOnClickListener(this);
		if(app.stSeznama!=-1)
		{
				napolniSeznam();
		}
		else{
			app.novSeznam=new NovSeznamArtiklov();
		}

	     ActionItem addItem 		= new ActionItem(ID_BRISI, "Izbriši", getResources().getDrawable(R.drawable.file_delete_icon));
	     ActionItem uploadItem 	= new ActionItem(ID_SPREMENI_CENO, "Spremeni ceno", getResources().getDrawable(R.drawable.name_help_con));
	     
	     mQuickAction 	= new QuickAction(this);	
	     mQuickAction.addActionItem(addItem);
	     mQuickAction.addActionItem(uploadItem);
	   //setup the action item click listener
	     mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
				
				public void onItemClick(QuickAction quickAction, int pos, int actionId) {
//					ActionItem actionItem = quickAction.getActionItem(pos);
					
					if (actionId == ID_BRISI) { //Add item selected

			      		
			      		
			      		if(app.stSeznama!=-1)
			      		{
				      		app.novSeznam.getNovSeznamArtiklov().remove(izbranIzdelek);
				      		NovSeznamArtiklov ns= app.novSeznam;
					  	   
				      		app.vsiSeznami.removNovSeznam(app.stSeznama);   
				      		app.vsiSeznami.replaceSeznam(app.stSeznama, ns);
				      		app.novSeznam=new NovSeznamArtiklov();
				      		
				      		if(!app.novSeznamList.isEmpty())
				    			app.novSeznamList.clear();
				      		napolniSeznam();
	
				      		app.novSeznamList.notifyDataSetChanged();
			      		}
			      		else
			      		{
			      			
			      			app.novSeznamList.remove(app.novSeznam.getNovSeznamArtiklov().get(izbranIzdelek));
			      			app.novSeznam.getNovSeznamArtiklov().remove(izbranIzdelek);
			      			app.novSeznamList.notifyDataSetChanged();
			      		}
					}else if(actionId==ID_SPREMENI_CENO)
					{
						
						showDialog(DIALOG_SPREMENI);
			      		
					}
				}
			});
			
		//setup on dismiss listener, set the icon back to normal
		mQuickAction.setOnDismissListener(new PopupWindow.OnDismissListener() {			
				public void onDismiss() {
				}
			});
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
			
		if(app.novSeznam.getImeSeznama()=="")
			actionBar.setTitle("Ustvari nov seznam");
		else
			actionBar.setTitle(app.novSeznam.getImeSeznama());
		
		actionBar.setHomeAction(new DomovAction());
		actionBar.addAction(new Poslji());
		actionBar.addAction(new Izbrisi());
		actionBar.addAction(new Shrani());
			
    }

    
    
    protected Dialog onCreateDialog(int id) {
    	
        switch(id) {
        case DIALOG_SPREMENI:
//        	app.stSeznama=-1;
        	Log.e("Grem spremenit seznam", "Velikost seznama: "+app.novSeznam.getVelikostSeznamaArtiklov() +" Velikost izbrani  izdelki: "+ izbranIzdelek );
        	Context mContext = this;
        	SpremeniIzdelek dialog = new SpremeniIzdelek(mContext,app,izbranIzdelek );
        	
			return dialog;
		
        case DIALOG_POSLJI:
        	Context mContext1 = this;
        	Dostava dialog1 = new Dostava(mContext1,app);      	
        	return dialog1;
        	
        case DIALOG_DODAJ_IZDELEK:
        	Context mContex2 =this;
        	DodajIzdelek dialog2=new DodajIzdelek(mContex2, app);
        	return dialog2;
        	
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
//				showDialog(DIALOG_DODAJ_IZDELEK);
				
    			try {
    				Intent moj=new Intent(this, Iskanje.class);
        			this.startActivity(moj);
				} catch (Exception e) {
					Log.e("Napak ", e.toString());
					// TODO: handle exception
				}
		}
		
		// TODO Auto-generated method stub
		
		
	}
	
	public void onFinish()
	{
		super.finish();
		app.stSeznama=-1;
	
	}
    @Override
    public void onStop()
    {
    	super.onStop();
    	app.napolniVmesno();
    	
    }
	
    @Override
    protected void onRestart() {
    	// TODO Auto-generated method stub
    	super.onRestart();
    	
    }
    
	@Override
	public void onBackPressed()
	{
//		super.onBackPressed();

		
	  if(app.novSeznam.getVelikostSeznamaArtiklov()==0)
	  {
		  super.onBackPressed();
	  }
	  else
	  {
	  	  if(app.stSeznama!=-1)
	  	  {
	  		 
	  		 app.vsiSeznami.removNovSeznam(app.stSeznama);
	  		 app.vsiSeznami.replaceSeznam(app.stSeznama, app.novSeznam);
	  		 finish();
	
			 if(!app.novSeznamList.isEmpty())  
			  		app.novSeznamList.clear();
			 
			 
	  	  }
	  	  else
	  	  {
	  		show_alert();
	  	  }
	  }
  	 
	
	}
	 private void show_alert() {
	    	// TODO Auto-generated method stub
	    	 AlertDialog.Builder alert_box=new AlertDialog.Builder(this);
	    	 alert_box.setMessage("Izgubili boste seznam, ga želite shraniti?");
	    	 alert_box.setPositiveButton("NE",new DialogInterface.OnClickListener() {
	    	 public void onClick(DialogInterface dialog, int which) {
	    		 app.newNovSeznam();
	    		 if(!app.novSeznamList.isEmpty())  
	    		  		app.novSeznamList.clear();
	    		 finish();
	    		 Toast.makeText(getApplicationContext(), "Izbrisan element", Toast.LENGTH_LONG).show();
	    		}
	    	 });
	    	 alert_box.setNegativeButton("DA", new DialogInterface.OnClickListener() {
	    	 public void onClick(DialogInterface dialog, int which) {
	    		 showDialog(DIALOG_IME_SEZNAMA);
	    		 Toast.makeText(getApplicationContext(), "Nisem izbrisal seznama", Toast.LENGTH_LONG).show();
	    	 }
	    	 });

	    	 alert_box.show();
	    }
	@Override
    public void onResume() {
		super.onResume();
		Log.e("Velikost onResume",app.novSeznam.getNovSeznamArtiklov().size()+"");
		
		
    }
	
	public String zaNajboljsiIzdelek()
	{
		  String vmesni="";
		  String sumniki="";
		  
    	  for(int i=0;i<app.novSeznam.getNovSeznamArtiklov().size();i++)
    	  {
    		
    		  sumniki=app.novSeznam.getNovSeznamArtiklov().get(i).getIme()+";";
    		 
    		  sumniki= sumniki.replace("č", "c");
    		  sumniki= sumniki.replace("Č", "C");
    		  sumniki= sumniki.replace("ž", "z");
    		  sumniki= sumniki.replace("Ž", "Z");
    		  sumniki= sumniki.replace("š", "s");
    		  sumniki= sumniki.replace("Š", "S");
    		  vmesni+=sumniki;
    	  }
		  //Toast.makeText(this, vmesni, Toast.LENGTH_LONG).show();
		  Log.e("Izdelki",vmesni);
    	  return vmesni;
	}

    public void napolniSeznam()
    {
    	app.novSeznam=new NovSeznamArtiklov();   	
    	int meja=(int) app.vsiSeznami.getUstvarjeniSezname().get(app.stSeznama).getNovSeznamArtiklov().size();	

    	for(int x=0;x<meja;x++)
    	{
    		app.dodajArtikelNaSeznam(app.vsiSeznami.getUstvarjeniSezname().get(app.stSeznama).getNovSeznamArtiklov().get(x),app.vsiSeznami.getUstvarjeniSezname().get(app.stSeznama).jeOznacen(x));
    		app.novSeznamList.add(app.vsiSeznami.getUstvarjeniSezname().get(app.stSeznama).getNovSeznamArtiklov().get(x));
    		
    	}
    	app.novSeznam.setImeSeznama(app.vsiSeznami.getUstvarjeniSezname().get(app.stSeznama).getImeSeznama());
    }

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		app.novSeznam.Oznaci(arg2);
	}

	public boolean onItemLongClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
//		app.stSeznama=position;
		izbranIzdelek=position;
		mQuickAction.show(view);
		return false;
		
	}

	private class Izbrisi implements Action {

	       
        public int getDrawable() {
            return R.drawable.delet;
        }

        
        public void performAction(View view) {
      	  if(app.novSeznam.getVelikostSeznamaArtiklov()==0)
      	  {
      		  if(app.stSeznama!=-1)
            	  app.vsiSeznami.removNovSeznam(app.stSeznama);

        	  app.stSeznama=-1;
        	  app.novSeznamList.clear();
        	  
        	  NovSeznam.this.finish();
        	  Toast.makeText(NovSeznam.this,"Izbrisano!", Toast.LENGTH_SHORT)
              .show();
      	  }
      	  else
      	  {
      	  	  if(app.stSeznama!=-1)
      	  	  {
      	  			 app.vsiSeznami.removNovSeznam(app.stSeznama);
      	  			 app.stSeznama=-1;
         	  		 finish(); 
      	  		 
      			 if(!app.novSeznamList.isEmpty())  
      			  		app.novSeznamList.clear();
      	  	  }
      	  	  else
      	  	  {
      	  		show_alert();
      	  	  }
      	  }
        }

    }
	private class Poslji implements Action {

	       
        public int getDrawable() {
            return R.drawable.sent;
        }

        
        public void performAction(View view) {
        	 showDialog(DIALOG_POSLJI);
        }

    }
	private class Shrani implements Action {

	       
        public int getDrawable() {
            return R.drawable.save;
        }

        
        public void performAction(View view) {
        	showDialog(DIALOG_IME_SEZNAMA);
        }

    }
	private class DomovAction implements Action {

	    
	    public int getDrawable() {
	        return R.drawable.home;
	    }

	    public void performAction(View view) {
	     if(app.novSeznam.getVelikostSeznamaArtiklov()==0)
	   	  {
	    		 Intent moj=new Intent(NovSeznam.this, MainActivity.class);
		    	 finish();
		    	 startActivity(moj);
	   	  }
	   	  else
	   	  {
	   	  	  if(app.stSeznama!=-1)
	   	  	  {
	   	  		  
	   	  		  Intent moj=new Intent(NovSeznam.this, MainActivity.class);
			      finish();
			      startActivity(moj);
	   	  		  app.vsiSeznami.removNovSeznam(app.stSeznama);
	   	  		  app.vsiSeznami.replaceSeznam(app.stSeznama, app.novSeznam);
	   	  		 
	   	
	   			 if(!app.novSeznamList.isEmpty())  
	   			  		app.novSeznamList.clear();
	   			 
	   			 
	   	  	  }
	   	  	  else
	   	  	  {
	   	  		show_alert();
	   	  	  }
	   	  }
	    	
	    }

	}
  
}