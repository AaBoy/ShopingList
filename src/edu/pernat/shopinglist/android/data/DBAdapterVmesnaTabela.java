package edu.pernat.shopinglist.android.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import edu.pernat.shopinglist.android.razredi.NovSeznamArtiklov;



public class DBAdapterVmesnaTabela implements BaseColumns {
	public static final  String TAG="ShoppingList";
	
	public static final  int SEZNAM_ID=0;
	public static final int ARTIKLE_ID=1;
	public static final int OZNACEN=2;
	


	public static final  String ST_SEZNAMA="stSeznama";
	public static final  String ST_ARTIKLA="stArtikla";
	public static final String OZNACEN_STRING="oznacen";
	

	public static final  String TABELA_VMESNA="vmesna";
	public static final  String TABLE="vmesni";


	private final Context context;

	private DatabaseHelperVmesnaTabela DBHelper;
	private SQLiteDatabase db;

	public DBAdapterVmesnaTabela(Context ctx) 
	{
		this.context = ctx;
		DBHelper = new DatabaseHelperVmesnaTabela(context);
	}


	//---opens the database---
	public DBAdapterVmesnaTabela open() throws SQLException 
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
	public long insertSezname(int idSeznam,int idArtikel, int stOznacenih) 
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(ST_SEZNAMA, idSeznam); 
		initialValues.put(ST_ARTIKLA, idArtikel);
		initialValues.put(OZNACEN_STRING, stOznacenih);
		return db.insert(TABELA_VMESNA, null, initialValues);
		//return 1;
	}

	//---deletes a particular title---
	public boolean deleteStevec(long rowId) 
	{
		return db.delete(TABELA_VMESNA, _ID + "=" + rowId, null) > 0;
	}

	//---retrieves all the titles---
	public Cursor getAll() 
	{
		
		return db.query(TABELA_VMESNA, new String[] {
				ST_SEZNAMA,      //POS_NAME=1
				ST_ARTIKLA,
				OZNACEN_STRING
				},    //POS_VALUE =2
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
					ST_SEZNAMA,
					ST_ARTIKLA,
					OZNACEN_STRING
					}, 
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
	   if(obstajaTabela())
	   {
		SQLiteDatabase db= this.db;
	    db.delete(TABELA_VMESNA, null, null);
	   }
	}

	public boolean obstajaTabela()
	{
		try {
			db = DBHelper.getWritableDatabase();
			Cursor cur;
		
			cur=db.rawQuery("select * from "+TABELA_VMESNA +";", null);
			if(cur.getCount()>0){cur.close();return true;}
			cur.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		 
		    return false;
		    
	}
	public long sizeDB()
	{
//		SQLiteStatement s= 
			//return DatabaseUtils.queryNumEntries(db, TABELA_SEZNAMI);
		Cursor dataCount = db.rawQuery("select count(*) from " +TABELA_VMESNA , null);
		
		return dataCount.getCount();
//		return 1;
	}
}
