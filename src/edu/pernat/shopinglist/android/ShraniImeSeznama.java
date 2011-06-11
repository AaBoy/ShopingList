package edu.pernat.shopinglist.android;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ShraniImeSeznama extends Dialog implements OnClickListener {
 
	TextView ime;
	Button potrdi;
	GlobalneVrednosti app;
	public ShraniImeSeznama(Context context,GlobalneVrednosti temp) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.nastavi_ime_seznama);
		this.setTitle("Nastavite ime seznama.");
		app=temp;
		
		ime=(TextView)findViewById(R.id.imeSeznamaTextView);	
		ime.setText("");
		potrdi=(Button)findViewById(R.id.potrdniImeSeznama);
		potrdi.setOnClickListener(this);
	}
	
	
	public void onClick(View v) {
		if(v.getId()==R.id.potrdniImeSeznama)
		{
			
			app.novSeznam.get(0).imeSeznama=ime.getText().toString();
			this.dismiss();
		}
	}

}
