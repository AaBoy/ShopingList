package edu.pernat.shopinglist.android.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper 
{	

	public static final  String TAG="DatabaseHelper";
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "Poiskusna";
	private static final String DATABASE_CREATE =

				"create table " +DBAdapterTrgovine.TABELA_UPORABMIK +" ("+DBAdapterTrgovine._ID+ " integer primary key autoincrement, "
				+ DBAdapterTrgovine.UPORABNISKO+" TEXT not null, "+DBAdapterTrgovine.GESLO +"TEXT not null);"+
				"create table "  +DBAdapterTrgovine.TABELA_TRGOVINE +" ("+DBAdapterTrgovine._ID+ " integer primary key autoincrement, "
				+ DBAdapterTrgovine.NAME_TRGO + " TEXT not null);"+
				"create table "  +DBAdapterTrgovine.TABELA_SEZNAM +" ("+DBAdapterTrgovine._ID+ " integer primary key autoincrement, "
				+DBAdapterTrgovine.SEZNAM_DATUM + " DATE not null, " +DBAdapterTrgovine.SEZNAM_SKU_CENA+ " DECIMAL not null);"+
				"create table "  +DBAdapterTrgovine.TABELA_ARTI +" ("+DBAdapterTrgovine._ID+ " integer primary key autoincrement, "
				+DBAdapterTrgovine.ARTI_KOLI_ID + " INTEGER not null ,"+ DBAdapterTrgovine.ARTI_NAME_S + " TEXT not null);"+
				"create table "  +DBAdapterTrgovine.TABELA_KOL_ART +" ("+DBAdapterTrgovine._ID+ " integer primary key autoincrement, "
				+DBAdapterTrgovine.KOLI_AR_VRSTA_KOLI+ " TEXT not null ,"+ DBAdapterTrgovine.KOLI_AR_KOLI+ "DECIMAL not null);"+
				"create table "  +DBAdapterTrgovine.TABELA_UPOR_HAS_SEZ +" ("+DBAdapterTrgovine.V_US_UPO_S+ " integer not null, "
				+DBAdapterTrgovine.V_US_SENZ_S+ " integer not null); "+
				"create table "  +DBAdapterTrgovine.TABELA_SEZ_HAS_ARTI +" ("+DBAdapterTrgovine.V_SA_SEZN_S+ " integer not null, "
				+DBAdapterTrgovine.V_SA_ARTI_S+ " integer not null, "+ DBAdapterTrgovine.V_SA_SKUP_S+ " DECIMAL not null);"+
				"create table "  +DBAdapterTrgovine.TABELA_TGRO_HAS_ARTI +" ("+DBAdapterTrgovine.V_TA_TRG_S+ " integer not null, "
				+DBAdapterTrgovine.V_TA_ARTI_S+ " integer not null); "
				
				
;

	DatabaseHelper(Context context) 
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
		db.execSQL("DROP TABLE IF EXISTS "+DBAdapterTrgovine.TABLE);
		onCreate(db);
	}

}
