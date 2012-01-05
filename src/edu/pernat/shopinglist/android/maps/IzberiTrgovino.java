package edu.pernat.shopinglist.android.maps;

import com.google.android.maps.MapActivity;

import edu.pernat.shopinglist.android.GlobalneVrednosti;
import edu.pernat.shopinglist.android.R;
import edu.pernat.shopinglist.android.R.id;
import edu.pernat.shopinglist.android.R.layout;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class IzberiTrgovino extends Dialog implements OnClickListener {

	Spinner imeTrgovin;
	String array_spinner[];
	Button potrdi, zavrni;
	KjeSemActivity glob;
	GlobalneVrednosti app;
	
	public IzberiTrgovino(Context context, KjeSemActivity aa,GlobalneVrednosti app1) {
		super(context);
		setContentView(R.layout.izberi_trgovino);
		this.setTitle("Izberite trgovino!");
		glob=aa;
		app=app1;

		potrdi=(Button)findViewById(R.id.potrdiTrgovino);
		zavrni=(Button)findViewById(R.id.zavrniTrgovino);
		potrdi.setOnClickListener(this);
		zavrni.setOnClickListener(this);
		//*Dodaj spinerje*/
		
		imeTrgovin=(Spinner)findViewById(R.id.spinerNazivTrgovine);
		
		
		array_spinner=new String [app.seznamTrgovin.size()];

		for(int i=0;i<app.seznamTrgovin.size();i++)
		{
			array_spinner[i]=app.seznamTrgovin.get(i).print();
		}
			
		ArrayAdapter pinnerArrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,array_spinner );
		imeTrgovin.setAdapter(pinnerArrayAdapter);
	
	/*Konec dodajanja*/
		// TODO Auto-generated constructor stub
	}

	public void onClick(View v) {
		switch (v.getId())
		
		{
		case R.id.potrdiTrgovino:
		{
			app.stSeznama= imeTrgovin.getSelectedItemPosition();
			glob.my_updateWithNewLocation(null);
			this.dismiss();
			break;
		}
		case R.id.zavrniTrgovino:
		{
			
			glob.finish();
			break;
		}
		
		
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
