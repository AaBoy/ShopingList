package edu.pernat.shopinglist.android;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import edu.pernat.shopinglist.android.razredi.Artikli;

public class Iskanje extends ListActivity implements OnItemClickListener
{
	
	private EditText et;
	int textlength=0;
	GlobalneVrednosti app;
	private ArrayList<Artikli> array_sort= new ArrayList<Artikli>();
	private ArrayList<Artikli> Sort_vmesni= new ArrayList<Artikli>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	   
	    	setContentView(R.layout.iskanje_layout);
	   	    app=(GlobalneVrednosti) getApplication();
	   	    app.iskanjeList=new IskanjeArrajAdapter(this, R.layout.iskanje_seznam, app.seznamArtiklov, app);
	   	    setListAdapter(app.iskanjeList);
	   	    this.setRequestedOrientation(1);
	   	    registerForContextMenu(getListView());
	   	    app.izbrani=new boolean[app.seznamArtiklov.size()];
	   	    this.getListView().setOnItemClickListener(this);
	   	    final Context vmesni=this;
	   		et = (EditText) findViewById(R.id.EditText01);
	   		et.addTextChangedListener(new TextWatcher()
	   		{
	   			public void afterTextChanged(Editable s)
	   			{  
		   			textlength = et.getText().length();
		   		
		   			array_sort=new ArrayList<Artikli>();
//		   			if(et.getText().length()>0 && Sort_vmesni.size()>0)
//		   			{
//		   				for (int i = 0; i < Sort_vmesni.size(); i++)
//			   			{
//			   				if (textlength <= Sort_vmesni.get(i).getIme().length())
//			   				{
////			   					Log.e("Izpisi ime", app.seznamArtiklov.get(i).getIme());
////			   					if(et.getText().toString().equalsIgnoreCase((String)app.seznamArtiklov.get(i).getIme().subSequence(0,textlength)))
////			   					{
////			   					  array_sort.add(app.seznamArtiklov.get(i));
////			   					}
//			   					String zdruzi=Sort_vmesni.get(i).getIme()+"  "+Sort_vmesni.get(i).getOpis();
//			   					
//			   					if(zdruzi.indexOf(et.getText().toString().toUpperCase())!=-1)
//			   					{
//			   					 
//			   					  array_sort.add(Sort_vmesni.get(i));
//			   					}
//			   				 }
//			   			}
//		   				Sort_vmesni=array_sort;
//			   			app.iskanjeList=new IskanjeArrajAdapter(vmesni, R.layout.iskanje_seznam, array_sort, app);
//		   			}
//		   			else
//		   			{
			   			for (int i = 0; i < app.seznamArtiklov.size(); i++)
			   			{
			   				if (textlength <= app.seznamArtiklov.get(i).getIme().length())
			   				{
//			   					Log.e("Izpisi ime", app.seznamArtiklov.get(i).getIme());
//			   					if(et.getText().toString().equalsIgnoreCase((String)app.seznamArtiklov.get(i).getIme().subSequence(0,textlength)))
//			   					{
//			   					  array_sort.add(app.seznamArtiklov.get(i));
//			   					}
			   					String zdruzi=app.seznamArtiklov.get(i).getIme()+"  "+app.seznamArtiklov.get(i).getOpis();
			   					
			   					if(zdruzi.indexOf(et.getText().toString().toUpperCase())!=-1)
			   					{
			   					 
			   					  array_sort.add(app.seznamArtiklov.get(i));
			   					}
			   				 }
			   			}
//			   			Sort_vmesni=array_sort;
			   			app.iskanjeList=new IskanjeArrajAdapter(vmesni, R.layout.iskanje_seznam, array_sort, app);
			   			
//		   			}
		   			

		   			
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
		super.onStop();
		int v=0;
		for(int i=0;i<app.izbrani.length;i++)
		{
			if(app.izbrani[i]==true)
			{
				app.dodajArtikelNaSeznam(app.seznamArtiklov.get(i));
	    		app.novSeznamList.add(app.seznamArtiklov.get(i));
				Log.e("Sezm označo kot true", i+"");
			}
		}
		
//		app.izbrani=new boolean[app.seznamArtiklov.size()];
	}
	


	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		app.izbrani[arg2]=!app.izbrani[arg2];
		Log.e("Nekaj napisši", "Sem napiso");
	}
	
	
	
	
	
	

}