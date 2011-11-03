package edu.pernat.shopinglist.android;

import edu.pernat.shopinglist.android.razredi.Seznam;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class DodajIzdelek extends Dialog implements OnClickListener {
	GlobalneVrednosti app;
	Button potdi,zavrni;
	Spinner prvi;
	private String array_spinner[];
	String en;
	private ArrayAdapter<CharSequence> m_adapterForSpinner;
	public DodajIzdelek(Context context,GlobalneVrednosti temp) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dodaj_izdelek);
		this.setTitle("Dodajte nov izdelek na seznam!");
		prvi=(Spinner)findViewById(R.id.spinner1);
		app=temp;
		potdi=(Button)findViewById(R.id.dodajPotrdi);
		zavrni=(Button)findViewById(R.id.dodajZavrni);
		potdi.setOnClickListener(this);
		zavrni.setOnClickListener(this);
		en="Nkeaj";
		

		napolniSeznamArtiklov();
		
		
		ArrayAdapter pinnerArrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, array_spinner);
		prvi.setAdapter(pinnerArrayAdapter);

	}
	
	
	public DodajIzdelek(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dodaj_izdelek);
		this.setTitle("Dodajte nov izdelek na seznam!");
		prvi=(Spinner)findViewById(R.id.spinner1);
		
		potdi=(Button)findViewById(R.id.dodajPotrdi);
		zavrni=(Button)findViewById(R.id.dodajZavrni);
		potdi.setOnClickListener(this);
		zavrni.setOnClickListener(this);
		en="Nkeaj";
//		
//
//		napolniSeznamArtiklov();
//		
//		
//		ArrayAdapter pinnerArrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, array_spinner);
//		prvi.setAdapter(pinnerArrayAdapter);
		
		
		Toast.makeText(getContext(), "Jej notri", Toast.LENGTH_SHORT).show();
	}

	public void napolniSeznamArtiklov()
	{

		
		array_spinner=new String[app.seznamArtiklov.size()];
		for(int i=0;i<app.seznamArtiklov.size();i++)
		{
			array_spinner[i]=app.seznamArtiklov.get(i).getIme();
		}
	}
	
	public void onClick(View v) {
		
		switch (v.getId())
		{
		case R.id.dodajPotrdi:
			//dodaj na seznam
			
			Toast.makeText(getContext(), "Izdelek dodan", Toast.LENGTH_SHORT).show();
			//app.novSeznam.add(new Seznam(app.getUser(), app.seznamArtiklov.get(prvi.getSelectedItemPosition())));
			app.novSeznamList.add(new Seznam("", app.seznamArtiklov.get(prvi.getSelectedItemPosition())));
			this.dismiss();
			break;
			
		case R.id.dodajZavrni:
			
			this.cancel();
			break;
		
		
		}
		
		// TODO Auto-generated method stub
		
	}
	@Override
    public void dismiss() {
		super.dismiss();
        app.novSeznamList.notifyDataSetChanged();
    }
	
	@Override
	public void onBackPressed()
	{
		
		Toast.makeText(getContext(), "Potrdite ali zavrnite dodan izdelek.", Toast.LENGTH_SHORT).show();
		
	}
	
	
 
	
}
