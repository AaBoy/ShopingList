package edu.pernat.shopinglist.android;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class PridobiSezname extends  AsyncTask<Integer, Void, Long>
{
	ProgressDialog dialogWait;
	@Override
	protected void onPreExecute()
	{
		 //dialogWait= ProgressDialog.show(PridobiSezname.this, "", "Delam! Počakajte prosim...", true);
		
	}

	@Override
	protected Long doInBackground(Integer... params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}


