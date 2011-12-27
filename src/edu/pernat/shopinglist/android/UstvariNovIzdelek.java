package edu.pernat.shopinglist.android;

import edu.pernat.shopinglist.android.razredi.Artikli;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UstvariNovIzdelek extends Dialog implements OnClickListener  {
	//GlobalneVrednosti app;
	EditText cena,ime,kolicina;
	Button potPri,zavPri;
	GlobalneVrednosti app;
	public UstvariNovIzdelek(Context context,GlobalneVrednosti tmp) {
		super(context);
		setContentView(R.layout.ustvari_nov_izdelek);
		
		zavPri=(Button)findViewById(R.id.zavrniNovIzdelek);
		potPri=(Button)findViewById(R.id.potrdiNovIzdelek);
		zavPri.setOnClickListener(this);
		potPri.setOnClickListener(this);
		this.setTitle("Vpišite ime, cena in kolicino izdelka!");
		app=tmp;
		cena=(EditText)findViewById(R.id.cenaNovegaIzdelkaEditText);
		ime=(EditText)findViewById(R.id.imeNovegaIzdelkaEditText);
		kolicina=(EditText)findViewById(R.id.kolicinaTextEdit);
        //app=(GlobalneVrednosti) context.getApplicationContext();///// getApplication();
	}



	/** Called when the activity is first created. */



	public void onClick(View v){
		switch (v.getId()){
		
		case R.id.potrdiNovIzdelek:
			
			app.artikelNaSeznamInBazo(new Artikli(app.seznamArtiklov.size(), Double.parseDouble(cena.getText().toString()), ime.getText().toString(), kolicina.getText().toString()));			
			Toast.makeText(getContext(), "Potrdi", Toast.LENGTH_SHORT).show();
			this.cancel();
			
			break;
		
		case R.id.zavrniNovIzdelek:
			
			Toast.makeText(getContext(), "Zavrni", Toast.LENGTH_SHORT).show();
			this.dismiss();
			break;
		}
		
		
	}

}