package edu.pernat.shopinglist.android;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;


public class Vpis  extends Dialog implements OnClickListener {
	//GlobalneVrednosti app;
	TextView en;

	public Vpis(Context context) {
		super(context);
		setContentView(R.layout.vpis);

		this.setTitle("Vpisite uporabniško in geslo!");
		
		en=(TextView)findViewById(R.id.textView1);
        //app=(GlobalneVrednosti) context.getApplicationContext();///// getApplication();
	}



	/** Called when the activity is first created. */



	public void onClick(View v){
		switch (v.getId()){
		
		case R.id.potrdiPrijavo:
			
			
			
			en.setText("Daj hlače dol");
			/*app.uporabnisko=(String) en.getText();
			en=(TextView)findViewById(R.id.textView2);
			app.geslo=(String) en.getText();*/
			
			
			break;
		
		case R.id.zavrniPrijavo:
			
		
			
			break;
		}
		
		
	}



	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}

}
