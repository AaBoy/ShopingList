package edu.pernat.shopinglist.android;



import java.io.FileNotFoundException;
import java.io.PrintWriter;

import edu.pernat.shopinglist.android.maps.KjeSemActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.AlteredCharSequence;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Main extends Activity {
	

	GlobalneVrednosti global=new GlobalneVrednosti();
	/** Called when the activity is first created. */
	public static final String FILE_NAME_USER="uporabnisko";
	public static final String FILE_NAME_PASS="geslo";
	public static final String PIME="NAME";
	public static final String PPASS="PASSWORD";
	public static final int DIALOG_VPIS=0;
	private Menu mMenu; 
	private  PrintWriter  myUsername,myPass;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	global=(GlobalneVrednosti) getApplication();

    	
    }
    
    
    
    public void odpriVpis(View v)
    {
    	if(v.getId()==R.id.button1)
    	{	
    		Intent moj=new Intent(this, NovSeznam.class);
			this.startActivity(moj);
    		//this.startActivityForResult(moj, 0);
    	}
    }
    
    public void odpriOkno(View v)
    {
    	switch(v.getId())
    	{
    		case R.id.googleMaps:
    		{
    			Intent moj=new Intent(this, KjeSemActivity.class);
    			this.startActivity(moj);
    			break;
    		}
    	
    		case R.id.zgodovinaSeznamov:
    		{
    			Intent moj=new Intent(this, SeznamNarocil.class);
    			this.startActivity(moj);
    			
    			break;
    		}
    		
    		case R.id.novSeznam:
    		{
    			Intent moj=new Intent(this, NovSeznam.class);
    			this.startActivity(moj);
    			break;
    		}
    		
    		case R.id.gumbKamera:
    		
    		{
    			Intent moj=new Intent(this, Kamera.class);
    			this.startActivity(moj);
    			break;
    			
    		}
    		
    	}
    	
    	
    }
    
    
    @Override
	public void onResume() { //pref predno user vidi nastavim prave vrednosti
		super.onResume();
		SharedPreferences settings = getSharedPreferences(FILE_NAME_USER,0); //pref odprem preferences
		String tmp = settings.getString(PIME, ""); //pref preberem staro vrednost

		settings = getSharedPreferences(FILE_NAME_PASS,0); //pref odprem preferences
		String tmp1 = settings.getString(PPASS, ""); //pref preberem staro vrednost
		
		//app.stInc.setStanje(tmp); //pref nastavim staro vrednost (od tod naprej šteje)
		

	}
	@Override
	public void onPause() { //pref uporabnik ali OS zapusti pogled, potrebno shranit
		super.onPause();
    	SharedPreferences settings =getSharedPreferences(FILE_NAME_USER, 0);
    	SharedPreferences.Editor editor = settings.edit();
		editor.putString(PIME, "Janez");
		editor.commit();
		
		settings =getSharedPreferences(FILE_NAME_PASS, 0);
    	editor = settings.edit();
		editor.putString(PPASS, "Miha");
		editor.commit();
	}
    
    @Override
    public void onStop()
    {
    	super.onStop();

    	SharedPreferences settings =getSharedPreferences(FILE_NAME_USER, 0);
    	SharedPreferences.Editor editor = settings.edit();
		editor.putString(PIME, "Janez");
		editor.commit();
		
		settings =getSharedPreferences(FILE_NAME_PASS, 0);
    	editor = settings.edit();
		editor.putString(PPASS, "Miha");
		editor.commit();

    	//myName.println(app.ime);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        // See which child activity is calling us back.

        switch (resultCode) {

            case 0:
        }
        
    }
    /*Menu in dialog*/
    protected Dialog onCreateDialog(int id) {
    	
        switch(id) {
        case DIALOG_VPIS:
        	
        	//setContentView(R.layout.vpis);
        	Context mContext = this;
        	Vpis dialog = new Vpis(mContext);


        	/*TextView text = (TextView) dialog.findViewById(R.id.text);
        	text.setText("Hello, this is a custom dialog!");
        	ImageView image = (ImageView) dialog.findViewById(R.id.image);
        	image.setImageResource(R.drawable.android);*/
        	
        	
			return dialog;
			
			
        default:
            break;
        }
        
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

      mMenu = menu; //ni nujno
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.main_menu, mMenu);
      return true;

    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.Vpis:
    	  
    	  showDialog(DIALOG_VPIS);
    	  return true;


      default:// Generic catch all for all the other menu resources
        if (!item.hasSubMenu()) {
          Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT)
          .show();
          return true;
        }
        break;
      }
      return false;
    }
    /*Konec menija*/
}