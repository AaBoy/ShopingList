package edu.pernat.shopinglist.android;

import android.R.bool;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SpremeniIzdelek extends Activity {
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spremeni_izdelek);
    	
    }

   
	public boolean odlocitvKlik (View v)
	{
		
		Toast.makeText(this,"Potrdi spremembo" , Toast.LENGTH_SHORT)
        .show();
		
		return false;
		/*switch (v.getId())
		{
		case R.id.potrdiSpremembo:
		{
			Toast.makeText(this,"Potrdi spremembo" , Toast.LENGTH_SHORT)
	          .show();
			return true;
		}
		case R.id.zavrniSpremembo:
		{
			Toast.makeText(this,"Shranjeno", Toast.LENGTH_SHORT).show();
			return true;
		}
		default:
		{
			return false;
			
		}
		
		}*/
		
	}
	
}
