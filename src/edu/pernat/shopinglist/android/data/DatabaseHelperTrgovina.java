package edu.pernat.shopinglist.android.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


public class DatabaseHelperTrgovina extends SQLiteOpenHelper 
{	

	public static final  String TAG="DatabaseHelper";
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "NasloviTrgovin";
	private static final String DATABASE_CREATE =
		
				"create table " +DBAdapterTrgovina.TABELA_TRGOVINE +" ("+DBAdapterTrgovina._ID+ " integer primary key autoincrement, "
				+ DBAdapterTrgovina.KRAJ_TRGOVINE+" TEXT not null, "+ DBAdapterTrgovina.NASLOV_TRGOVINE+" TEXT not null);"
		
;

	DatabaseHelperTrgovina(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		try
		{
		db.execSQL(DATABASE_CREATE);
	
		}
		catch(SQLException bb)
		{
			Log.e("nekaj",  bb.getMessage().toString());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, 
			int newVersion) 
	{
		Log.w(TAG, "Upgrading database from version " + oldVersion 
				+ " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS "+DBAdapterTrgovina.TABLE);
		onCreate(db);
	}

}
