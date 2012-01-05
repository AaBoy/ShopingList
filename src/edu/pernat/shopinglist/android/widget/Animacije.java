package edu.pernat.shopinglist.android.widget;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import edu.pernat.shopinglist.android.R;

public class Animacije extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onStart(Intent intent, int startId) {
	
		Log.e("Se, mptro", "widget");
	}

}
