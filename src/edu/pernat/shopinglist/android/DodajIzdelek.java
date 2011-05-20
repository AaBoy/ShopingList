package edu.pernat.shopinglist.android;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class DodajIzdelek extends Dialog implements OnClickListener {

	Button potdi,zavrni;
	Spinner prvi;
	private ArrayAdapter<CharSequence> m_adapterForSpinner;
	public DodajIzdelek(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dodaj_izdelek);
		
		prvi=(Spinner)findViewById(R.id.spinner1);

		potdi=(Button)findViewById(R.id.dodajPotrdi);
		zavrni=(Button)findViewById(R.id.dodajZavrni);
		potdi.setOnClickListener(this);
		zavrni.setOnClickListener(this);
	}

	public void onClick(View v) {
		
		switch (v.getId())
		{
		case R.id.dodajPotrdi:
			//dodaj na seznam
			Toast.makeText(getContext(), "Potrdi", Toast.LENGTH_SHORT).show();

			break;
			
		case R.id.dodajZavrni:
			
			Toast.makeText(getContext(), "Si zaprl", Toast.LENGTH_SHORT).show();
			this.dismiss();
			break;
		
		
		}
		
		// TODO Auto-generated method stub
		
	}
	
}
