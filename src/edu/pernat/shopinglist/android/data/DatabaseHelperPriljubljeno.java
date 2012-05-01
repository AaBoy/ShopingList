package edu.pernat.shopinglist.android.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


public class DatabaseHelperPriljubljeno extends SQLiteOpenHelper 
{	

	
	public static final  String TAG="DatabaseHelper";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "TabelaPriljubljeni";
	private static final String DATABASE_CREATE =

				"create table " +DBAdapterPriljubljeno.TABELA_PRILJUBLJENI +" ("+DBAdapterPriljubljeno._ID+ " integer primary key autoincrement, "
				+ DBAdapterPriljubljeno.ST_ARTIKLA+ " integer not null, "
				+ DBAdapterPriljubljeno.KOLIKO_KRAT_IZBRAN_STRING+" integer not null); ";


	DatabaseHelperPriljubljeno(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, 
			int newVersion) 
	{
		Log.w(TAG, "Upgrading database from version " + oldVersion 
				+ " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS "+DBAdapterPriljubljeno.TABLE);
		onCreate(db);
	}

}
