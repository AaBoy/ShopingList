package edu.pernat.shopinglist.android;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.pernat.shopinglist.android.razredi.Artikli;

public class IskanjePriljubljeneArrajAdapter  extends ArrayAdapter<Artikli>{
	public static final int DIALOG_DODAJ_IZDELEK =1;
	LayoutInflater mInflater;
	GlobalneVrednosti app;
	Context vmensa=null;

	public IskanjePriljubljeneArrajAdapter(Context context, int textViewResourceId, List<Artikli> objects, GlobalneVrednosti app) { //Step 4.8 POPRAVI Stevec ->Rezultati
		super(context, textViewResourceId,objects);
	    mInflater = LayoutInflater.from(context);
	    this.app=app;
	    
	   }

	
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		final Artikli tmp = getItem(position); //Step Step 4.7 pridobi data
		
		final ViewHolder holder;
		// When convertView is not null, we can reuse it directly, there is no need
		// to reinflate it. We only inflate a new View when the convertView supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.iskanje_seznam, null); //Step Step 4.7.5 DOLOČI ROW LL
			// Creates a ViewHolder and store references to the naziv children views
			// we want to bind data to.
			holder = new ViewHolder();
			
			holder.cena = (TextView) convertView.findViewById(R.id.novaCenaIskanje); //Step 4.8 POPRAVI
			// holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.naziv = (TextView) convertView.findViewById(R.id.novaImeIskanje); //Step 4.8 POPRAVI
			holder.kolicina=(TextView)convertView.findViewById(R.id.novaKolicinaIskanje);
			holder.kupljeno=(CheckBox)convertView.findViewById(R.id.CheckBox1Iskanje);
			holder.opis=(TextView)convertView.findViewById(R.id.textViewOpisIzdelkaIskanje);
			holder.layout=(LinearLayout)convertView.findViewById(R.id.linearLayoutArrayAdapeterIskanje);
			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			// and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		}

		
	
		String v=""+tmp.getCena();
		holder.cena.setText(""+v.replace(".", ",")+"€");
		holder.naziv.setText(tmp.getIme()); //Step 4.8 POPRAVI
		holder.kolicina.setText(tmp.getKolicina());
		holder.opis.setText(tmp.getOpis());
		
		if(app.izbraniPriljubljen.length>position)
		{
			
			Log.e("Velikost izbraniPriljubljenh", app.izbraniPriljubljen.length +"  ;  "+position);
			holder.kupljeno.setChecked(app.izbraniPriljubljen[app.indeksOfArtikelVTabeliPriljubljeni((int)tmp.getIdBaze())]);
			holder.kupljeno.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	            	app.izbraniPriljubljen[position]=!app.izbraniPriljubljen[position];
	            	holder.kupljeno.setChecked(app.izbraniPriljubljen[position]);
	            }
	        });
			
			// Bind the data efficiently with the holder.
			holder.cena.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					app.izbraniPriljubljen[position]=!app.izbraniPriljubljen[position];
	            	holder.kupljeno.setChecked(app.izbraniPriljubljen[position]);
	            	
	            	Log.e("index cena na seznamu: ", position+"");
	            	Log.e("keri element je oznaco: ",tmp.getIdBaze()+" " + tmp.getCena());
				}
			}); 
			holder.opis.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					app.izbraniPriljubljen[position]=!app.izbraniPriljubljen[position];
	            	holder.kupljeno.setChecked(app.izbraniPriljubljen[position]);
				}
			});
			

			holder.layout.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					app.izbraniPriljubljen[position]=!app.izbraniPriljubljen[position];
	            	holder.kupljeno.setChecked(app.izbraniPriljubljen[position]);
				}
			});
			
			
			holder.naziv.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					app.izbraniPriljubljen[position]=!app.izbraniPriljubljen[position];
	            	holder.kupljeno.setChecked(app.izbraniPriljubljen[position]);
				}
			});
			holder.kolicina.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					app.izbraniPriljubljen[position]=!app.izbraniPriljubljen[position];
	            	holder.kupljeno.setChecked(app.izbraniPriljubljen[position]);
				}
			});
			
		}
		return convertView;
	}
	
	static class ViewHolder {
		TextView cena; //Step 4.8 POPRAVI
		TextView naziv; //Step 4.8 POPRAVI
		TextView kolicina;
		TextView opis;
		LinearLayout layout;
		
		CheckBox kupljeno;
	}
	


}