package edu.pernat.shopinglist.android.tab;

import edu.pernat.shopinglist.android.GlobalneVrednosti;
import edu.pernat.shopinglist.android.Iskanje;
import edu.pernat.shopinglist.android.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class IskanjeTab extends TabActivity{
	TabHost tabHost;
	

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources(); // Resource object to get Drawables
        tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, Iskanje.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("Iskanje").setIndicator("Vsi izdelki",
                          res.getDrawable(R.drawable.save))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, PriljubljeniActivity.class);
        GlobalneVrednosti application=(GlobalneVrednosti)getApplication();
        
       
        spec = tabHost.newTabSpec("Priljubljeni").setIndicator("Priljubljeni Izdelki",
                          res.getDrawable(R.drawable.save))
                      .setContent(intent);
        this.setRequestedOrientation(1);
        tabHost.addTab(spec);

    }
	
	protected void onRestart() {
		super.onRestart();
		tabHost.setCurrentTab(0);
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		tabHost.setCurrentTab(0);
	}
	
}
