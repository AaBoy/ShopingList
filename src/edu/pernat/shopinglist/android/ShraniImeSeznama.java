package edu.pernat.shopinglist.android;

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
		ime.setText(app.uporabnisko);
		potrdi=(Button)findViewById(R.id.potrdniImeSeznama);
		potrdi.setOnClickListener(this);
	}
	
	
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.potrdniImeSeznama:
			app.uporabnisko=ime.getText().toString();
			aa.finish();
			
			break;
		
		}
		
		
	}

}
