package edu.pernat.shopinglist.android.data;

import java.sql.Date;

import edu.pernat.shopinglist.android.razredi.EmailNaslovi;
import edu.pernat.shopinglist.android.razredi.RazredBaza;
import edu.pernat.shopinglist.android.razredi.Trgovina;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;



public class DBAdapterTrgovina implements BaseColumns {
	public static final  String TAG="ShoppingListTrgovine";

	public static final  String KRAJ_TRGOVINE = "kraj";
	public static final  String NASLOV_TRGOVINE = "naslov";
	public static final  int TRGOVINA__ID=0;
	public static final int TRGOVINA_NASLOV=1;
	public static final int TRGOVINA_KRAJ=2;
	
	
	public static final  String TABLE="trgovine";
	public static final  String TABELA_TRGOVINE="trgovina";


	private final Context context;
	
	private DatabaseHelperTrgovina DBHelper;
	private SQLiteDatabase db;

	public DBAdapterTrgovina(Context ctx) 
	{
		this.context = ctx;
		DBHelper = new DatabaseHelperTrgovina(context);
	}


	//---opens the database---
	public DBAdapterTrgovina open() throws SQLException 
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
	public long insertTrgovina(Trgovina tmp) 
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KRAJ_TRGOVINE, tmp.getKraj()); 
		initialValues.put(NASLOV_TRGOVINE, tmp.getNaslov()); 
		return db.insert(TABELA_TRGOVINE, null, initialValues);
	}

	//---deletes a particular title---
	public boolean deleteTrgovino(long rowId) 
	{
		return db.delete(TABELA_TRGOVINE, _ID + "=" + rowId, null) > 0;
	}

	//---retrieves all the titles---
	public Cursor getAll() 
	{
		return db.query(TABELA_TRGOVINE, new String[] {
				_ID,       //POS__ID=0;
				    //POS_NAME=1
				KRAJ_TRGOVINE,
				NASLOV_TRGOVINE
				},    //POS_VALUE =2
				null, 
				null, 
				null, 
				null, 
				null);
	}

	public void deleteAll()
	{
	    SQLiteDatabase db= this.db;
	    db.delete(TABELA_TRGOVINE, null, null);

	}

	
	//---retrieves a particular title---
	public Cursor getTrgvoino(long rowId) throws SQLException 
	{
		Cursor mCursor =
			db.query(true, TABELA_TRGOVINE, new String[] {
					_ID, 
					KRAJ_TRGOVINE,
					NASLOV_TRGOVINE}, 
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
			cur=db.rawQuery("select * from "+TABELA_TRGOVINE +";", null);
			cur.getCount();
			
			if(cur.getCount()>0)return true;
			
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
