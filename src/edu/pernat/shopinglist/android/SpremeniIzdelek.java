package edu.pernat.shopinglist.android;

import android.R.bool;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SpremeniIzdelek extends Dialog implements android.view.View.OnClickListener {
	
	
	public SpremeniIzdelek(Context context) {
		super(context);
	    setContentView(R.layout.spremeni_izdelek);
	}

	public void onClick(View v) {
		switch  (v.getId())
		{	
			case R.id.potrdiSpremembo: 
			{
		
				break;
			}
			
			case R.id.zavrniSpremembo:
			{
				
				break;
			}
		}	
		
	}

	
}
