package edu.pernat.shopinglist.android.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


public class DatabaseHelperSeznami extends SQLiteOpenHelper 
{	

	public static final  String TAG="DatabaseHelper";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Poiskusna5Seznami";
	private static final String DATABASE_CREATE =

				"create table " +DBAdapterSeznami.TABELA_SEZNAMI +" ("
				+ DBAdapterSeznami.ST_SEZNAMA+" integer primary key not null, "+ DBAdapterSeznami.IME_SEZNAMA+ " TEXT not null, "+
				DBAdapterSeznami.SKUPNA_CENA+ " real not null, "+ DBAdapterSeznami.KOLI_NAKUOPV+" integer not null, "
				+DBAdapterSeznami.DATUM_NAKUPA_S+" TEXT not null); ";
				
				
	
				
;

	DatabaseHelperSeznami(Context context) 
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
		db.execSQL("DROP TABLE IF EXISTS "+DBAdapterSeznami.TABLE);
		onCreate(db);
	}

}
