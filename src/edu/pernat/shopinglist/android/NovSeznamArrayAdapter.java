package edu.pernat.shopinglist.android;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.pernat.shopinglist.android.SeznamArrayAdapter.ViewHolder;
import edu.pernat.shopinglist.android.razredi.Seznam;

public class NovSeznamArrayAdapter extends ArrayAdapter<Seznam>{
	
	LayoutInflater mInflater;
	
	public NovSeznamArrayAdapter(Context context, int textViewResourceId, List<Seznam> objects) { //Step 4.8 POPRAVI Stevec ->Rezultati
		super(context, textViewResourceId,objects);
	    mInflater = LayoutInflater.from(context);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Seznam tmp = getItem(position); //Step Step 4.7 pridobi data
		ViewHolder holder;
		// When convertView is not null, we can reuse it directly, there is no need
		// to reinflate it. We only inflate a new View when the convertView supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.nov_seznam, null); //Step Step 4.7.5 DOLOČI ROW LL
			// Creates a ViewHolder and store references to the naziv children views
			// we want to bind data to.
			holder = new ViewHolder();
			holder.cena = (TextView) convertView.findViewById(R.id.novaCena); //Step 4.8 POPRAVI
			// holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.naziv = (TextView) convertView.findViewById(R.id.novaIme); //Step 4.8 POPRAVI
			holder.kolicina=(TextView)convertView.findViewById(R.id.novaKolicina);
			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			// and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		}
		// Bind the data efficiently with the holder.
		holder.cena.setText(""+tmp.getArtikelCena()+" €"); //Step 4.8 POPRAVI
		holder.naziv.setText(tmp.getArtikelIme()); //Step 4.8 POPRAVI
		holder.kolicina.setText(tmp.getArtikliKolicina());
		//holder.icon.setImageBitmap((position & 1) == 1 ? mIcon1 : mIcon2);
		return convertView;
	}
	static class ViewHolder {
		TextView cena; //Step 4.8 POPRAVI
		TextView naziv; //Step 4.8 POPRAVI
		TextView kolicina;
	}

}