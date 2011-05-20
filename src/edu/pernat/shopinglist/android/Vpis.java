package edu.pernat.shopinglist.android;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;


public class Vpis  extends Dialog implements OnClickListener, android.view.View.OnClickListener {
	//GlobalneVrednosti app;
	TextView en;
	Button potPri,zavPri;
	public Vpis(Context context) {
		super(context);
		setContentView(R.layout.vpis);
		
		zavPri=(Button)findViewById(R.id.zavrniPrijavo);
		potPri=(Button)findViewById(R.id.potrdiPrijavo);
		zavPri.setOnClickListener(this);
		potPri.setOnClickListener(this);
		this.setTitle("Vpisite uporabniško in geslo!");
		
		en=(TextView)findViewById(R.id.textView1);
        //app=(GlobalneVrednosti) context.getApplicationContext();///// getApplication();
	}



	/** Called when the activity is first created. */



	public void onClick(View v){
		switch (v.getId()){
		
		case R.id.potrdiPrijavo:
			
			
			this.cancel();
			en.setText("Daj hlače dol");
			/*app.uporabnisko=(String) en.getText();
			en=(TextView)findViewById(R.id.textView2);
			app.geslo=(String) en.getText();*/
			Toast.makeText(getContext(), "Potrdi", Toast.LENGTH_SHORT).show();
			
			break;
		
		case R.id.zavrniPrijavo:
			
			Toast.makeText(getContext(), "Zavrni", Toast.LENGTH_SHORT).show();
			dismiss();
			break;
		}
		
		
	}



	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}

}
