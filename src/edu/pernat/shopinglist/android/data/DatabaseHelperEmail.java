package edu.pernat.shopinglist.android.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


public class DatabaseHelperEmail extends SQLiteOpenHelper 
{	

	public static final  String TAG="DatabaseHelper";
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "Email naslovi";
	private static final String DATABASE_CREATE =

				"create table " +DBAdapterEmail.TABELA_EMAIL +" ("+DBAdapterTrgovine._ID+ " integer primary key autoincrement, "
				+ DBAdapterEmail.EMAIL_NASLOVI+" TEXT not null);"
		
;

	DatabaseHelperEmail(Context context) 
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
		db.execSQL("DROP TABLE IF EXISTS "+DBAdapterEmail.TABLE);
		onCreate(db);
	}

}
