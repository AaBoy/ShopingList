package edu.pernat.shopinglist.android;


import android.R.bool;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SpremeniIzdelek extends Dialog implements OnClickListener {
	
	
	Button potSpre,zavSpre;
	EditText spreIme,spreCena;
	public SpremeniIzdelek(Context context,RazredBaza temp) {
		super(context);
	    setContentView(R.layout.spremeni_izdelek);
	    potSpre=(Button)findViewById(R.id.potrdiSpremembo);
	    zavSpre=(Button)findViewById(R.id.zavrniSpremembo);
	    spreIme=(EditText)findViewById(R.id.spremeniIme);
	    spreCena=(EditText)findViewById(R.id.spremeniCena);
	    
	    setTitle("Spremeni informacije o izdelku");
	    potSpre.setOnClickListener(this);
	    zavSpre.setOnClickListener(this);
	    
	    spreIme.setText(temp.geslo);
	    spreCena.setText(temp.getUpo());
	    
	}
	
	public void onClick(View v) {
		 switch  (v.getId())
		{	
			case R.id.potrdiSpremembo: 
			{
				
				// this.cancel();
				break;
			}
			
			case R.id.zavrniSpremembo:
			{
				SpremeniIzdelek.this.dismiss();
				break;
			}
		}	
		
	}


	
}
