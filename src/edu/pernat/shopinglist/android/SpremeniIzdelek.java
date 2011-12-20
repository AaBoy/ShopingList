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
	
	GlobalneVrednosti app;
	int globalnoIzbran;
	Button potSpre,zavSpre;
	EditText spreIme,spreCena;
	public SpremeniIzdelek(Context context,GlobalneVrednosti temp,int izbrani) {
		super(context);
	    setContentView(R.layout.spremeni_izdelek);
	    potSpre=(Button)findViewById(R.id.potrdiSpremembo);
	    zavSpre=(Button)findViewById(R.id.zavrniSpremembo);
	    spreIme=(EditText)findViewById(R.id.spremeniIme);
	    spreCena=(EditText)findViewById(R.id.spremeniCena);
	    app=temp;
	    setTitle("Spremeni informacije o izdelku");
	    potSpre.setOnClickListener(this);
	    zavSpre.setOnClickListener(this);
	    globalnoIzbran=izbrani;
	    spreIme.setText(app.novSeznam.getNovSeznamArtiklov().get(izbrani).getIme());
	    spreCena.setText(""+app.novSeznam.getNovSeznamArtiklov().get(izbrani).getCena());
	    
	}
	
	public void onClick(View v) {
		 switch  (v.getId())
		{	
			case R.id.potrdiSpremembo: 
			{
				
				
				app.novSeznam.getNovSeznamArtiklov().get(globalnoIzbran).setIme(spreIme.getText().toString());
				app.novSeznam.getNovSeznamArtiklov().get(globalnoIzbran).setCena(Double.parseDouble( spreCena.getText().toString()));
				 
				app.novSeznamList.setNotifyOnChange(true);
				this.dismiss();
				break;
			}
			
			case R.id.zavrniSpremembo:
			{
				
				this.cancel();
				break;
			}
		}	
		
	}

	@Override
    public void dismiss() {
		super.dismiss();
       
    }
	
	@Override
	public void onStop()
	{
		
	    spreIme.setText("");
	    spreCena.setText("");
	}
	
	@Override
	public void  onBackPressed()
	{
		//super.onBackPressed();
		Toast.makeText(getContext(), "Potrdite ali zavrnite spremembo na izdelku.", Toast.LENGTH_SHORT).show();
		
	}
}
