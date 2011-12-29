package edu.pernat.shopinglist.android.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.util.Log;
import edu.pernat.shopinglist.android.razredi.Artikli;



public class DBAdapterIzdelki implements BaseColumns {
	public static final  String TAG="ShoppingListEmail";

	public static final  int AR_ID=0;
	public static final  int AR_I=1;
	public static final  int AR_C=2;
	public static final  int AR_K=3;
	public static final  int AR_O=4;
	
	public static final  String AR_IME="ime";
	public static final  String AR_CENA="s_cena";
	public static final  String AR_KOLI="kolicina";
	public static final  String AR_OPIS="opis";
	


	public static final  String TABLE="izdelki";
	public static final  String TABELA_IZDELKI="izdelek";


	private final Context context;
	
	private DatabaseHelperIzdelki DBHelper;
	private SQLiteDatabase db;

	public DBAdapterIzdelki(Context ctx) 
	{
		this.context = ctx;
		DBHelper = new DatabaseHelperIzdelki(context);
	}


	//---opens the database---
	public DBAdapterIzdelki open() throws SQLException 
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
	public long insertArtikel(Artikli tmp) 
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(AR_IME, tmp.getIme()); 
		initialValues.put(AR_CENA, tmp.getCena()); 
		initialValues.put(AR_KOLI, tmp.getKolicina());
		initialValues.put(AR_OPIS, tmp.getOpis());
		return db.insert(TABELA_IZDELKI, null, initialValues);
		//return 1;
	}

	//---deletes a particular title---
	public boolean deleteIzdelek(long rowId) 
	{
		return db.delete(TABELA_IZDELKI, _ID + "=" + rowId, null) > 0;
	}

	//---retrieves all the titles---
	public Cursor getAll() 
	{
		return db.query(TABELA_IZDELKI, new String[] {
				_ID,       //POS__ID=0;
				    //POS_NAME=1
				AR_IME,
				AR_CENA,
				AR_KOLI,
				AR_OPIS},    //POS_VALUE =2
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
					AR_IME,
					AR_CENA,
					AR_KOLI,
					AR_OPIS}, 
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
	
	public void deleteAll()
	{
	    SQLiteDatabase db= this.db;
	    db.delete(TABELA_IZDELKI, null, null);

	}
	public boolean obstajaTabela()
	{
		try {
			
			db = DBHelper.getWritableDatabase();
			Cursor cur;
			cur=db.rawQuery("select * from "+TABELA_IZDELKI +";", null);
			cur.getCount();
			if(cur.getCount()>0)return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return false;
	}
	
	public String vrniPozicije(String ime)
	{
		Cursor cursor = db.query(TABELA_IZDELKI, new String[] { "_ID",AR_IME}, 
                AR_IME + " = " + "'"+ime+ "'", null, null, null, null);
		
		return cursor.getString(0);
	}
	
	public long stVrstic()
	{
		String sql = "SELECT COUNT(*) FROM " + TABELA_IZDELKI;
	    SQLiteStatement statement = db.compileStatement(sql);
	    long count = statement.simpleQueryForLong();
	    return count;
	}
	
	
	public int Update(Artikli tmp)
	{
		db = DBHelper.getWritableDatabase();
		ContentValues cv =  new ContentValues();
		cv.put(AR_IME, tmp.getIme());
		cv.put(AR_CENA, tmp.getCena());
		cv.put(AR_KOLI, tmp.getKolicina());
		cv.put(AR_OPIS, tmp.getOpis());
		return db.update(TABELA_IZDELKI, cv, AR_IME+"='"+tmp.getIme()+"'  AND "+AR_OPIS+"='"+tmp.getOpis()+"'",null);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
