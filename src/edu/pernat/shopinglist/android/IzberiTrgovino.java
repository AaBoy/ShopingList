package edu.pernat.shopinglist.android;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class IzberiTrgovino extends Dialog implements OnClickListener {

	Spinner imeTrgovin, naslovTrgovin;
	String array_spinner[];
	Button potrdi, zavrni;
	
	
	public IzberiTrgovino(Context context) {
		super(context);
		setContentView(R.layout.izberi_trgovino);
		this.setTitle("Izberite trgovino!");
		
		
		
		
		potrdi=(Button)findViewById(R.id.potrdiTrgovino);
		zavrni=(Button)findViewById(R.id.zavrniTrgovino);
		potrdi.setOnClickListener(this);
		zavrni.setOnClickListener(this);
		//*Dodaj spinerje*/
		
		imeTrgovin=(Spinner)findViewById(R.id.spinerNazivTrgovine);
		naslovTrgovin=(Spinner)findViewById(R.id.spinerNasloTrgovine);
		
		array_spinner=new String [1];
		array_spinner[0]="Mercator";
		/*array_spinner[1]="Spar";
		array_spinner[2]="Tuš";
		array_spinner[3]="Žerak";
		array_spinner[4]="Eurospin";*/
		
		
		
		ArrayAdapter pinnerArrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, array_spinner);
		imeTrgovin.setAdapter(pinnerArrayAdapter);
		
		array_spinner=new String [1];
		array_spinner[0]="TRŽAŠKA 65";
		/*array_spinner[1]="Miheličeva 55";
		array_spinner[2]="Nova Gorica 5b";
		array_spinner[3]="Tezno 12";
		array_spinner[4]="Žirovnik ul. 5";*/
		
		ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, array_spinner);
		naslovTrgovin.setAdapter(spinnerArrayAdapter);

		
	/*Konec dodajanja*/
		// TODO Auto-generated constructor stub
	}

	public void onClick(View v) {
		switch (v.getId())
		
		{
		case R.id.potrdiTrgovino:
		{
			this.dismiss();
			break;
		}
		case R.id.zavrniTrgovino:
		{
			
			this.dismiss();
			break;
		}
		
		
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
