package edu.pernat.shopinglist.android;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import edu.pernat.shopinglist.android.razredi.NovSeznamArtiklov;


public class SeznamArrayAdapter extends ArrayAdapter<NovSeznamArtiklov>{
	
	LayoutInflater mInflater;
	int i=0;
	
	public SeznamArrayAdapter(Context context, int textViewResourceId, List<NovSeznamArtiklov> objects) { //Step 4.8 POPRAVI Stevec ->Rezultati
		super(context, textViewResourceId,objects);
	    mInflater = LayoutInflater.from(context);
	    
	    i=0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NovSeznamArtiklov tmp = getItem(position); //Step Step 4.7 pridobi data
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
			holder.stNakupov=(ProgressBar)convertView.findViewById(R.id.progressBar1);
			holder.veilkostPrgres=(TextView)convertView.findViewById(R.id.textViewVelikostProgressbara);
			holder.datum=(TextView)convertView.findViewById(R.id.textViewDatumUstvarjanjeSeznama);
			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			// and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		}
		// Bind the data efficiently with the holder.
		holder.naslovSeznama.setText(""+tmp.getImeSeznama()); //Step 4.8 POPRAVI
		holder.skupnaCena.setText(""+tmp.getSkupnaCena()+"€"); //Step 4.8 POPRAVI
		holder.zapovrstniID.setText(""+(position+1));
		holder.stNakupov.setMax(tmp.getVelikostSeznamaArtiklov());
		holder.stNakupov.setProgress(tmp.getStOznacenih());
		holder.veilkostPrgres.setText(tmp.getStOznacenih()+"/"+tmp.getVelikostSeznamaArtiklov()+". izdelkov.");
		final Calendar c = Calendar.getInstance();
//	    mHour = c.get(Calendar.DATE);
//	    mMinute = c.get(Calendar.);

		holder.datum.setText("Ustvarjen seznam: "+c.get(Calendar.DATE)+". "+c.get(Calendar.MONTH)+". "+c.get(Calendar.YEAR));
		//holder.icon.setImageBitmap((position & 1) == 1 ? mIcon1 : mIcon2);
		return convertView;
	}
	
	
	static class ViewHolder {
		TextView naslovSeznama; //Step 4.8 POPRAVI
		TextView skupnaCena; //Step 4.8 POPRAVI
		TextView zapovrstniID;
		TextView veilkostPrgres;
		TextView datum;
		ProgressBar stNakupov;
	}


}
