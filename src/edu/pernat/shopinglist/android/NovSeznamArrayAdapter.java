package edu.pernat.shopinglist.android;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import edu.pernat.shopinglist.android.R;
import edu.pernat.shopinglist.android.R.id;
import edu.pernat.shopinglist.android.R.layout;
import edu.pernat.shopinglist.android.razredi.Seznam;


public class NovSeznamArrayAdapter extends ArrayAdapter<Seznam>{
	public static final int DIALOG_DODAJ_IZDELEK =1;
	LayoutInflater mInflater;
	
	Context vmensa=null;
	
	public NovSeznamArrayAdapter(Context context, int textViewResourceId, List<Seznam> objects) { //Step 4.8 POPRAVI Stevec ->Rezultati
		super(context, textViewResourceId,objects);
	    mInflater = LayoutInflater.from(context);
	   }
	
	  protected Dialog onCreateDialog(int id) {
	    	
	        switch(id) {
				
	        case DIALOG_DODAJ_IZDELEK:
	        	Context mContext2=vmensa;
	        	Dostava dialog2=new Dostava(mContext2);
	        	
	        	return dialog2;
	        
	        default:
	            break;
	        }
	        
	        return null;
	    }
	
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		final Seznam tmp = getItem(position); //Step Step 4.7 pridobi data
		final ViewHolder holder;
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
			holder.kupljeno=(CheckBox)convertView.findViewById(R.id.CheckBox1);

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
		
		holder.kupljeno.setChecked(tmp.oznacen);
		
		holder.kupljeno.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	getItem(position).setSelected(holder.kupljeno.isChecked());
            }
        });
		
//		holder.naziv.setOnLongClickListener((OnLongClickListener) new OnLongClickListener() {
//			
//			public boolean onLongClick(View v) {
//
//				parent.getContext();
//                
//				
//				return false;
//			}
//		});
//		
//		 holder.cena.setOnLongClickListener(new OnLongClickListener() {
//			
//			public boolean onLongClick(View v) {
//				vmensa= parent.getContext();
//                onCreateDialog(DIALOG_DODAJ_IZDELEK);
//				
//				return false;
//			}
//		});

		
		//holder.kupljeno.setClickable(true);
		
		//holder.icon.setImageBitmap((position & 1) == 1 ? mIcon1 : mIcon2);
		return convertView;
	}
	static class ViewHolder {
		TextView cena; //Step 4.8 POPRAVI
		TextView naziv; //Step 4.8 POPRAVI
		TextView kolicina;
		CheckBox kupljeno;
	}
	


}