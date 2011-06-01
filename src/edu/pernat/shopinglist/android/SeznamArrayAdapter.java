package edu.pernat.shopinglist.android;

import java.util.ArrayList;
import java.util.List;

import edu.pernat.shopinglist.android.razredi.Artikli;
import edu.pernat.shopinglist.android.razredi.Seznam;
import edu.pernat.shopinglist.android.razredi.Seznami;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;


public class SeznamArrayAdapter extends ArrayAdapter<Seznami>{
	
	LayoutInflater mInflater;
	int i=0;
	
	public SeznamArrayAdapter(Context context, int textViewResourceId, List<Seznami> objects) { //Step 4.8 POPRAVI Stevec ->Rezultati
		super(context, textViewResourceId,objects);
	    mInflater = LayoutInflater.from(context);
	    i=0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Seznami tmp = getItem(position); //Step Step 4.7 pridobi data
		ViewHolder holder;
		// When convertView is not null, we can reuse it directly, there is no need
		// to reinflate it. We only inflate a new View when the convertView supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.seznam_narocil, null); //Step Step 4.7.5 DOLOČI ROW LL
			// Creates a ViewHolder and store references to the skupnaCena children views
			// we want to bind data to.
			holder = new ViewHolder();
			holder.naslovSeznama = (TextView) convertView.findViewById(R.id.imeArtikla); //Step 4.8 POPRAVI
			// holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.skupnaCena = (TextView) convertView.findViewById(R.id.SkupnaCena); //Step 4.8 POPRAVI
			holder.zapovrstniID=(TextView)convertView.findViewById(R.id.stZaporedja);
			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			// and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		}
		// Bind the data efficiently with the holder.
		holder.naslovSeznama.setText(""+tmp.getImeSeznama(0)); //Step 4.8 POPRAVI
		holder.skupnaCena.setText(""+tmp.getSkupnaCena()+"€"); //Step 4.8 POPRAVI
		holder.zapovrstniID.setText(""+(i));
		//holder.icon.setImageBitmap((position & 1) == 1 ? mIcon1 : mIcon2);
		i++;
		return convertView;
	}
	
	
	static class ViewHolder {
		TextView naslovSeznama; //Step 4.8 POPRAVI
		TextView skupnaCena; //Step 4.8 POPRAVI
		TextView zapovrstniID;
	}


}
