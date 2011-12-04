package edu.pernat.shopinglist.android.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


public class DatabaseHelperVsiSeznami extends SQLiteOpenHelper 
{	

	public static final  String TAG="DatabaseHelper";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Poiskusna4";
	private static final String DATABASE_CREATE =

				"create table " +DBAdapterVsiSeznami.TABELA_SEZNAMI +" ("+DBAdapterVsiSeznami._ID+ " integer primary key autoincrement, "
				+ DBAdapterVsiSeznami.ST_SEZNAMA+" integer not null, "+DBAdapterVsiSeznami.ST_ARTIKLA +" integer not null, "+ DBAdapterVsiSeznami.IME_SEZNAMA+ " TEXT not null, "+
				DBAdapterVsiSeznami.JE_OZNACEN+ " integer not null, "+ DBAdapterVsiSeznami.KOLI_NAKUOPV+" integer not null); ";
				
				
	
				
;

	DatabaseHelperVsiSeznami(Context context) 
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
		db.execSQL("DROP TABLE IF EXISTS "+DBAdapterVsiSeznami.TABLE);
		onCreate(db);
	}

}
