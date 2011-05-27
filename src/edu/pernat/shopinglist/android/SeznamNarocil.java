package edu.pernat.shopinglist.android;

import android.R.bool;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SeznamNarocil extends ListActivity implements OnItemClickListener  {
	
	GlobalneVrednosti app;
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stevec_list_activity);
       
        app=(GlobalneVrednosti) getApplication();
    	//app.dodaj(new RazredBaza("Kuku","lele"));
        setListAdapter(app.seznamList);
		this.getListView().setOnItemClickListener(this);
		//Toast.makeText(this, "Pritisnili ste:", Toast.LENGTH_LONG).show();

	}
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		//Toast.makeText(this, "Pritisnili ste: "+, Toast.LENGTH_LONG).show();
		app.stSeznama=position;
		Intent moj=new Intent(this, NovSeznam.class);
		this.startActivity(moj);
	}

	/*@Override
	public void onBackPressed() {
		Toast.makeText(this, "Stisno si back", Toast.LENGTH_LONG).show();
    	
	   
	}*/

	@Override
    public void onResume() {
		super.onResume();
        app.seznamList.notifyDataSetChanged();
    }
	

}

    

