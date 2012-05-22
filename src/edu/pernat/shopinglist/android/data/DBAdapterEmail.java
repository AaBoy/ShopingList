package edu.pernat.shopinglist.android.data;

import java.sql.Date;

import edu.pernat.shopinglist.android.razredi.EmailNaslovi;
import edu.pernat.shopinglist.android.razredi.RazredBaza;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;



public class DBAdapterEmail implements BaseColumns {
	public static final  String TAG="ShoppingListEmail";

	public static final  String EMAIL_NASLOVI = "s_email_name";
	public static final  int POS__ID=0;
	public static final int POS_EAMIL=1;

	public static final  String TABLE="emaili";
	public static final  String TABELA_EMAIL="email";


	private final Context context;
	
	private DatabaseHelperEmail DBHelper;
	private SQLiteDatabase db;

	public DBAdapterEmail(Context ctx) 
	{
		this.context = ctx;
		DBHelper = new DatabaseHelperEmail(context);
	}


	//---opens the database---
	public DBAdapterEmail open() throws SQLException 
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
	public long insertEmail(EmailNaslovi tmp) 
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(EMAIL_NASLOVI, tmp.getEmail()); 
		return db.insert(TABELA_EMAIL, null, initialValues);
		//return 1;
	}

	//---deletes a particular title---
	public boolean deleteEmail(long rowId) 
	{
		return db.delete(TABELA_EMAIL, _ID + "=" + rowId, null) > 0;
	}
	
	public void deleteVseEmaile()
	{
		db.delete(TABELA_EMAIL, null, null);
	}
	//---retrieves all the titles---
	public Cursor getAll() 
	{
		return db.query(TABELA_EMAIL, new String[] {
				_ID,       //POS__ID=0;
				    //POS_NAME=1
				EMAIL_NASLOVI},    //POS_VALUE =2
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
					EMAIL_NASLOVI}, 
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
	public boolean obstajaTabela()
	{
		try {
			db = DBHelper.getWritableDatabase();
			Cursor cur;
			cur=db.rawQuery("select * from "+TABELA_EMAIL +";", null);
			if(cur!=null)return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		 
		    return false;
		    
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
}
