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

			      		app.novSeznam.getNovSeznamArtiklov().remove(izbranIzdelek);
			      		NovSeznamArtiklov ns= app.novSeznam;
				  	   
			      		app.vsiSeznami.removNovSeznam(app.stSeznama);   
			      		app.vsiSeznami.replaceSeznam(app.stSeznama, ns);
			      		app.novSeznam=new NovSeznamArtiklov();
			      		
			      		if(!app.novSeznamList.isEmpty())
			    			app.novSeznamList.clear();
			      		napolniSeznam();

			      		app.novSeznamList.notifyDataSetChanged();
				
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

    	  showDialog(DIALOG_IME_SEZNAMA);
    	  return true;
    	  
      case R.id.Clear:
    	  
    	  if(app.stSeznama!=-1)
        	  app.vsiSeznami.removNovSeznam(app.stSeznama);

    	  app.stSeznama=-1;
    	  app.novSeznamList.clear();
    	  this.finish();
    	  Toast.makeText(this,"Izbrisano!", Toast.LENGTH_SHORT)
          .show();
    	  return true; 
    	  
      case R.id.Poslji:
      
    	  showDialog(DIALOG_POSLJI);
    	  
    	  return true;

      case R.id.CenejsiSeznam:
    	  
    	  CenejsaTrgovinaTask task=new CenejsaTrgovinaTask();
 		task.execute(1);
    	  
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
//		super.onBackPressed();

		
	  if(app.novSeznam.getVelikostSeznamaArtiklov()==0)
	  {
		  finish();
	  }
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
	 private void show_alert() {
	    	// TODO Auto-generated method stub
	    	 AlertDialog.Builder alert_box=new AlertDialog.Builder(this);
	    	 alert_box.setMessage("Izgubili boste seznam, ga ne želite shraniti?");
	    	 alert_box.setPositiveButton("DA",new DialogInterface.OnClickListener() {
	    	 public void onClick(DialogInterface dialog, int which) {
	    		 app.newNovSeznam();
	    		 if(!app.novSeznamList.isEmpty())  
	    		  		app.novSeznamList.clear();
	    		 finish();
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
	@Override
    public void onResume() {
		super.onResume();
		
    }

	//moje funkcije
	private static final String SOAP_ACTION="http://projektIzdelki.pernat.edu/NajcenejsiSeznam";
	private static final String METHOD_NAME="NajcenejsiSeznam";
	private static final String NAMESPACE="http://projektIzdelki.pernat.edu";
	private static final String URL="http://192.168.1.69:8080/projketIzdelki/services/NajboljsiSeznam?wsdl";

	
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
	ProgressDialog dialogWait;
	private class CenejsaTrgovinaTask extends AsyncTask<Integer, Void, String> {
		@Override
		protected void onPreExecute() {
			dialogWait = 
				ProgressDialog.show(NovSeznam.this, "", "Delam! Pridobivam cenik...", true);
		}
		protected String doInBackground(Integer... prviArgument) {
			
			int t1=prviArgument[0];
			String odgovor="";
			
			//global.novSeznam.add(new Seznam("koga", global.seznamArtiklov.get(0)));
			try {
				SoapObject Request =new SoapObject(NAMESPACE,METHOD_NAME);
				Request.addProperty("izdelki",zaNajboljsiIzdelek());	
				
				SoapSerializationEnvelope soapEnvelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
				soapEnvelope.dotNet=false;
				soapEnvelope.setOutputSoapObject(Request);				
				HttpTransportSE aht=new HttpTransportSE(URL);		
				
				try{
					aht.call(SOAP_ACTION,soapEnvelope);
					SoapPrimitive result =(SoapPrimitive)soapEnvelope.getResponse();
//					

					//Get the first property and change the label text
//					Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show(); 	
					odgovor=result.toString();
				}catch(Exception e){
					e.printStackTrace();
					return e.getMessage().toString();
				}
				
			} catch (Exception e) {
				System.out.println("XML Pasing Excpetion = " + e);
				return "XML Pasing Excpetion = " + e;
			}
	    	
			//global.init();
			return odgovor;
		}

		protected void onPostExecute(String tretjiArgument) {
			
			//global.novSeznamList.notifyDataSetChanged();
			Toast.makeText(NovSeznam.this,"Rezultat:"+tretjiArgument+" €",Toast.LENGTH_SHORT).show();
			dialogWait.cancel();
		}
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




  
}