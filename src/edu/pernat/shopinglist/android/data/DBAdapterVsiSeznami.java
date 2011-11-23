package edu.pernat.shopinglist.android.data;

import java.sql.Date;

import edu.pernat.shopinglist.android.RazredBaza;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;



public class DBAdapterVsiSeznami implements BaseColumns {
	public static final  String TAG="ShoppingList";
	
	public static final  int S_ID=0;
	public static final  int SEZNAM_ID=0;
	public static final  int ARTIKEL_ID=1;
	public static final int IME_SEZNAMA_ID=2;

	public static final  String ST_SEZNAMA="stSeznama";
	public static final  String ST_ARTIKLA="stArtikla";
	public static final  String IME_SEZNAMA="imeSeznama";
	


	public static final  String TABELA_SEZNAMI="seznami";
	public static final  String TABLE="stevec";


	private final Context context;

	private DatabaseHelperVsiSeznami DBHelper;
	private SQLiteDatabase db;

	public DBAdapterVsiSeznami(Context ctx) 
	{
		this.context = ctx;
		DBHelper = new DatabaseHelperVsiSeznami(context);
	}


	//---opens the database---
	public DBAdapterVsiSeznami open() throws SQLException 
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}

	//---closes the database---    
	public void close() 
	{
		DBHelper.close();
	}

	//---insert a stevec
	public long insertUporabnik(RazredBaza stevec) 
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(ST_SEZNAMA, stevec.getStSeznama()); 
		initialValues.put(ST_ARTIKLA, stevec.getStArtikla());
		initialValues.put(IME_SEZNAMA, stevec.getImeSeznama());
		return db.insert(TABELA_SEZNAMI, null, initialValues);
		//return 1;
	}

	//---deletes a particular title---
	public boolean deleteStevec(long rowId) 
	{
		return db.delete(TABELA_SEZNAMI, _ID + "=" + rowId, null) > 0;
	}

	//---retrieves all the titles---
	public Cursor getAll() 
	{
		
		return db.query(TABELA_SEZNAMI, new String[] {
				_ID,       //POS__ID=0;
				ST_SEZNAMA,      //POS_NAME=1
				ST_ARTIKLA,
				IME_SEZNAMA},    //POS_VALUE =2
				null, 
				null, 
				null, 
				null, 
				null);
	}

	//---retrieves a particular title---
	public Cursor getStevec(long rowId) throws SQLException 
	{
		Cursor mCursor =
			db.query(true, TABLE, new String[] {
					_ID, 
					ST_SEZNAMA,
					ST_ARTIKLA,
					IME_SEZNAMA}, 
					_ID + "=" + rowId, 
					null,
					null, 
					null, 
					null, 
					null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	//---update---
	public boolean updateStevec() 
	{
		/*ContentValues args = new ContentValues();
		args.put(NAME, tmp.getIme());
		args.put(STANJE, tmp.getTock());
		return db.update(TABLE, args, 
				_ID + "=" + tmp.getId(), null) > 0;*/
		return false;
	}
	public long sizeDB()
	{
		
//		SQLiteStatement s= 
			//return DatabaseUtils.queryNumEntries(db, TABELA_SEZNAMI);
		//Cursor dataCount = db.rawQuery("select count(*) from " +TABELA_SEZNAMI , null);
		
//		return dataCount.getCount();
		return 1;
	}
}
