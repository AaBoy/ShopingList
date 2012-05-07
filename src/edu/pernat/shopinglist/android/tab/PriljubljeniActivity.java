package edu.pernat.shopinglist.android.tab;

import java.util.ArrayList;

import edu.pernat.shopinglist.android.GlobalneVrednosti;
import edu.pernat.shopinglist.android.IskanjeArrajAdapter;
import edu.pernat.shopinglist.android.IskanjePriljubljeneArrajAdapter;
import edu.pernat.shopinglist.android.R;
import edu.pernat.shopinglist.android.razredi.Artikli;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;

public class PriljubljeniActivity extends ListActivity implements OnItemClickListener{
	private EditText et;
	int textlength=0;
	GlobalneVrednosti app;
	private ArrayList<Artikli> array_sort= new ArrayList<Artikli>();
	private ArrayList<Artikli> Sort_vmesni= new ArrayList<Artikli>();
	ArrayList<Artikli> seznamArtiPomozno;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    	setContentView(R.layout.iskanje_layout);
	   	    app=(GlobalneVrednosti) getApplication();  
	   	    seznamArtiPomozno=new ArrayList<Artikli>();
	   	    app.fillFromDBPriljubljeni();
	   	    
	   	    for (int i = 0; i < app.priljubljeniIndeks.size(); i++) {
	   	    	seznamArtiPomozno.add(app.seznamArtiklov.get(app.priljubljeniIndeks.get(i)-1));	
			}
	   	    Log.e("Velikost priljubljeniIndeks", app.priljubljeniIndeks.size()+"");
	   	    
	   	    app.iskanjePriljubljeneList=new IskanjePriljubljeneArrajAdapter(this, R.layout.iskanje_seznam, seznamArtiPomozno, app);
	   	    setListAdapter(app.iskanjePriljubljeneList);
	   	    this.setRequestedOrientation(1);
	   	    registerForContextMenu(getListView());
	   	    app.izbraniPriljubljen=new boolean[seznamArtiPomozno.size()+1];
	   	    this.getListView().setOnItemClickListener(this);
	   	    final Context vmesni=this;
	   	 	et = (EditText) findViewById(R.id.EditText01);
	   		et.addTextChangedListener(new TextWatcher()
	   		{
		   	 public void afterTextChanged(Editable s)
				{  
		   			textlength = et.getText().length();
		   			array_sort=new ArrayList<Artikli>();
		   			
		   			
		   			array_sort=app.iskanjeIzdelki(et.getText().toString().toUpperCase());
			   		ArrayList<Artikli>tmp=new ArrayList<Artikli>();
		   			for(int i=0;i<array_sort.size();i++)
		   			{
		   				
		   				for(int j=0;j<app.priljubljeniIndeks.size();j++)
		   				{
		   					if(array_sort.get(i).getIdBaze()==app.priljubljeniIndeks.get(j))
		   					{
		   						tmp.add(array_sort.get(i));
		   						break;
		   					}
		   				}
		   				
		   			}
		   			
		   			app.iskanjeList=new IskanjeArrajAdapter(vmesni, R.layout.iskanje_seznam, tmp, app);
	
		   			
		   			
		   			setListAdapter(app.iskanjeList);
					// Abstract Method of TextWatcher Interface.
				}
				public void beforeTextChanged(CharSequence s,int start, int count, int after)
				{
				// Abstract Method of TextWatcher Interface.
				}
				public void onTextChanged(CharSequence s,int start, int before, int count)
				{
	
			}
			});
	   
	 
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		
		
		for(int i=0;i<app.izbraniPriljubljen.length;i++)
		{
			if(app.izbraniPriljubljen[i]==true)
			{
				//naredi v da dobiš dobiš ineks artikla, pa kliči app.seznmaartiklov
				app.dodajArtikelNaSeznam(app.seznamArtiklov.get(app.idDBArtikelIzPriljubljeni(app.priljubljeniIndeks.get(i))));
	    		app.novSeznamList.add(app.seznamArtiklov.get(app.idDBArtikelIzPriljubljeni(app.priljubljeniIndeks.get(i))));
//				Log.e("Sezm označo kot true", i+"   "+app.seznamArtiklov.get( app.idDBArtikelIzPriljubljeni(i+1)).getIme());
			}
		}
		
		app.izbraniPriljubljen=new boolean[seznamArtiPomozno.size()+1];
		super.onStop();
	}
	


	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		app.izbraniPriljubljen[arg2]=!app.izbraniPriljubljen[arg2];
		Log.e("Nekaj napisši", "Sem napiso");
	}
	
}
