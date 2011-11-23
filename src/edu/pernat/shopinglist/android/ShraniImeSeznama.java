package edu.pernat.shopinglist.android;

import java.util.ArrayList;

import edu.pernat.shopinglist.android.razredi.Seznam;
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
	public ShraniImeSeznama(Context context,GlobalneVrednosti temp, Activity aa) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.nastavi_ime_seznama);
		this.setTitle("Nastavite ime seznama.");
		app=temp;
		this.aa=aa;
		ime=(TextView)findViewById(R.id.imeSeznamaTextView);	
		
		if(app.novSeznam.get(0).imeSeznama==null)
		{  
			app.uporabnisko="";
			ime.setText(app.uporabnisko);
		}
		else
		{
			ime.setText(app.novSeznam.get(0).imeSeznama);
		}
	
		
		potrdi=(Button)findViewById(R.id.potrdniImeSeznama);
		potrdi.setOnClickListener(this);
		
	}
	
	
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.potrdniImeSeznama:
			app.uporabnisko=ime.getText().toString();

  	        ArrayList<Seznam>ns= app.novSeznam;
  	        
  	        if(app.stSeznama!=-1)
         	 	app.vsiSeznami.remove(app.stSeznama);   
  	        
         	    ns.get(0).imeSeznama=app.uporabnisko;
         	    app.novSeznam=new ArrayList<Seznam>();
         	    app.vsiSeznami.add(new Seznami(ns));

         	    if(!app.novSeznamList.isEmpty())
       			app.novSeznamList = new NovSeznamArrayAdapter(aa,R.layout.nov_seznam, app.novSeznam);
			
         	    aa.finish();

			break;
		
		}
		
		
	}

}
