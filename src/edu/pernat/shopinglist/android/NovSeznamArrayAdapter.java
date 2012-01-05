package edu.pernat.shopinglist.android;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.pernat.shopinglist.android.razredi.Artikli;


public class NovSeznamArrayAdapter extends ArrayAdapter<Artikli>{
	public static final int DIALOG_DODAJ_IZDELEK =1;
	LayoutInflater mInflater;
	GlobalneVrednosti app;
	Context vmensa=null;

	public NovSeznamArrayAdapter(Context context, int textViewResourceId, List<Artikli> objects, GlobalneVrednosti app) { //Step 4.8 POPRAVI Stevec ->Rezultati
		super(context, textViewResourceId,objects);
	    mInflater = LayoutInflater.from(context);
	    this.app=app;
	    
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
		final Artikli tmp = getItem(position); //Step Step 4.7 pridobi data
		
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
			holder.opis=(TextView)convertView.findViewById(R.id.textViewOpisIzdelka);
			holder.layout=(LinearLayout)convertView.findViewById(R.id.linearLayoutArrayAdapeter);
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
		
		holder.opis.setOnLongClickListener(new OnLongClickListener() {    
         public boolean onLongClick(View v) {
             
             return false;
	         }
	     });
	    holder.cena.setOnLongClickListener(new OnLongClickListener() {
	           
	         public boolean onLongClick(View v) {   
	             return false;
	         }
	     });
	    holder.naziv.setOnLongClickListener(new OnLongClickListener() {
	           
	         public boolean onLongClick(View v) {   
	             return false;
	         }
	     });
	    holder.kolicina.setOnLongClickListener(new OnLongClickListener() {
	           
	         public boolean onLongClick(View v) {   
	             return false;
	         }
	     });
	    holder.layout.setOnLongClickListener(new OnLongClickListener() {
	           
	         public boolean onLongClick(View v) {   
	             return false;
	         }
	     });
		
		
		if(app.novSeznam!=null && app.novSeznam.getVelikostSeznamaArtiklov()>position)
		{
			holder.kupljeno.setChecked(app.novSeznam.jeOznacen(position));
			holder.kupljeno.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	            	app.novSeznam.Oznaci(position);
	            	holder.kupljeno.setChecked(app.novSeznam.jeOznacen(position));
	            }
	        });
			
			// Bind the data efficiently with the holder.
			holder.cena.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					app.novSeznam.Oznaci(position);
					holder.kupljeno.setChecked(app.novSeznam.jeOznacen(position));
				}
			}); 
			holder.opis.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					app.novSeznam.Oznaci(position);
					holder.kupljeno.setChecked(app.novSeznam.jeOznacen(position));
				}
			});
			holder.layout.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					app.novSeznam.Oznaci(position);
					holder.kupljeno.setChecked(app.novSeznam.jeOznacen(position));
				}
			});
			holder.naziv.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					app.novSeznam.Oznaci(position);
					holder.kupljeno.setChecked(app.novSeznam.jeOznacen(position));
				}
			});
			holder.kolicina.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					app.novSeznam.Oznaci(position);
					holder.kupljeno.setChecked(app.novSeznam.jeOznacen(position));
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