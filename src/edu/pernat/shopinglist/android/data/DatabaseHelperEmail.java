package edu.pernat.shopinglist.android.data;

import android.content.Context;
import android.database.SQLException;
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

				"create table " +DBAdapterEmail.TABELA_EMAIL +" ("+DBAdapterSeznami._ID+ " integer primary key autoincrement, "
				+ DBAdapterEmail.EMAIL_NASLOVI+" TEXT not null);"
		
;

	DatabaseHelperEmail(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		context.deleteDatabase(DATABASE_NAME);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		try
		{
			try {
				db.delete(DATABASE_NAME, null, null);
				Log.e("brisem","jej");
			} catch (Exception e) {
				// TODO: handle exception
			}
			
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
		db.execSQL("DROP TABLE IF EXISTS "+DBAdapterEmail.TABLE);
		onCreate(db);
	}
	
	public void izbrisiTabelo()
	{
		
	}

}
