package edu.pernat.shopinglist.android.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import edu.pernat.shopinglist.android.R;

public class MojWidget extends AppWidgetProvider {
	public String ACTION_WIDGET_CONFIGURE="ConfigureWidget";
	public String ACTION_WIDGET_RECEIVER ="ActionReceverWidget";
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// Build the intent to call the service
				RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
				Intent intent = new Intent(context.getApplicationContext(),UpdateWidgetService.class);
				Intent animacije= new Intent(context.getApplicationContext(),Animacije.class);
				PendingIntent pendingIntent = PendingIntent.getService(context.getApplicationContext(), 0, animacije,PendingIntent.FLAG_UPDATE_CURRENT);
				intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
				remoteViews.setOnClickPendingIntent(R.id.TextViewOpis1, pendingIntent);

				
				appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
				
				context.startService(intent);
				
	}
}
