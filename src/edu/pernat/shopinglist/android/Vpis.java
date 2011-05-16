package edu.pernat.shopinglist.android;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;


public class Vpis  extends Activity implements OnClickListener {
   
	

	/** Called when the activity is first created. */
    
	GlobalneVrednosti app;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vpis);
        app=(GlobalneVrednosti) getApplication();
        
    }

	
	public void odlocitevKlik(View v)
	{
		switch (v.getId()){
		
		case R.id.potrdiPrijavo:
			
			TextView en;
			en=(TextView)findViewById(R.id.textView1);
			
			app.uporabnisko=(String) en.getText();
			en=(TextView)findViewById(R.id.textView2);
			app.geslo=(String) en.getText();
			
			
			break;
		
		case R.id.zavrniPrijavo:
			
			break;
		}
		
		
	}


	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
}
