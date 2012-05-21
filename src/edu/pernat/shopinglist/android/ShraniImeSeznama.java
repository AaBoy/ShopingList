package edu.pernat.shopinglist.android;

import java.util.ArrayList;
import java.util.Calendar;

import edu.pernat.shopinglist.android.razredi.NovSeznamArtiklov;
import edu.pernat.shopinglist.android.razredi.Seznami;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShraniImeSeznama extends Dialog implements OnClickListener {
 
	TextView ime;
	Button potrdi;
	GlobalneVrednosti app;
	Activity aa;
	Context vmesni=null;
	public ShraniImeSeznama(Context context,GlobalneVrednosti temp) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.nastavi_ime_seznama);
		this.setTitle("Nastavite ime seznama.");
		ime=(TextView)findViewById(R.id.imeSeznamaTextView);	
		app=temp;
		vmesni=context;
		if(app.novSeznam.getImeSeznama()==null)
		{  
			
			ime.setText("");
		}
		else
		{
			ime.setText(app.novSeznam.getImeSeznama());
		}
	
		
		potrdi=(Button)findViewById(R.id.potrdniImeSeznama);
		potrdi.setOnClickListener(this);
		
	}
	
	public ShraniImeSeznama(Context context,GlobalneVrednosti temp, Activity aa) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.nastavi_ime_seznama);
		this.setTitle("Nastavite ime seznama.");
		app=temp;
		this.aa=aa;
		ime=(TextView)findViewById(R.id.imeSeznamaTextView);	
		
		if(app.novSeznam.getImeSeznama()==null)
		{  
			
			ime.setText("");
		}
		else
		{
			ime.setText(app.novSeznam.getImeSeznama());
		}
	
		
		potrdi=(Button)findViewById(R.id.potrdniImeSeznama);
		potrdi.setOnClickListener(this);
		
	}
	
	
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.potrdniImeSeznama:

			if(aa!=null)
			{
	  	        NovSeznamArtiklov ns= app.novSeznam;
	  			final Calendar c = Calendar.getInstance();
	  	        app.novSeznam.setDatumNakupa(""+(c.get(Calendar.DATE))+". "+(c.get(Calendar.MONTH)+1)+". "+c.get(Calendar.YEAR));
	  	        ns.setImeSeznama(ime.getText().toString());
	  	        if(app.stSeznama!=-1)
	         	 	{
	  	        		app.vsiSeznami.removNovSeznam(app.stSeznama);   
	  	        		app.vsiSeznami.replaceSeznam(app.stSeznama, ns);
	         	 	}
	  	        else
	  	        {
	         	    app.dodajSeznamNaSeznam(ns);
	  	        }
	         	    
	  	        	app.novSeznam=new NovSeznamArtiklov();
	         	    if(!app.novSeznamList.isEmpty())
	       			app.novSeznamList = new NovSeznamArrayAdapter(aa,R.layout.nov_seznam, app.novSeznam.getNovSeznamArtiklov(),app);
	         	    app.napolniBazoSeznamov();
	         	    aa.finish();
			}
			else
			{
					NovSeznamArtiklov ns= app.novSeznam;
		  	        ns.setImeSeznama(ime.getText().toString());
		  	        if(app.stSeznama!=-1)
		         	 	{
		  	        		app.vsiSeznami.removNovSeznam(app.stSeznama);   
		  	        		app.vsiSeznami.replaceSeznam(app.stSeznama, ns);
		         	 	}
		  	        else
		  	        {
		         	    app.dodajSeznamNaSeznam(ns);
		  	        }
		         	    
		  	        app.novSeznam=new NovSeznamArtiklov();
		         	if(!app.novSeznamList.isEmpty())         		
		       		app.novSeznamList = new NovSeznamArrayAdapter(vmesni,R.layout.nov_seznam, app.novSeznam.getNovSeznamArtiklov(),app);
		         	app.seznamList.notifyDataSetChanged();
		         	this.dismiss();    
		         	    
			 }
			}
			
		
		}
		
		
	}


