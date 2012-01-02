package edu.pernat.shopinglist.android.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import edu.pernat.shopinglist.android.razredi.NovSeznamArtiklov;



public class DBAdapterSeznami implements BaseColumns {
	public static final  String TAG="ShoppingList";
	
	public static final  int SEZNAM_ID=0;
	public static final int IME_SEZNAMA_ID=1;
	public static final int SKUPNA_CENA_ID=2;
	public static final int KOL_NAKUPOV=3;


	public static final  String ST_SEZNAMA="stSeznama";
	public static final  String IME_SEZNAMA="imeSeznama";
	public static final String SKUPNA_CENA="cena";
	public static final String KOLI_NAKUOPV="koliNakupov";


	public static final  String TABELA_SEZNAMI="seznami";
	public static final  String TABLE="stevec";


	private final Context context;

	private DatabaseHelperSeznami DBHelper;
	private SQLiteDatabase db;

	public DBAdapterSeznami(Context ctx) 
	{
		this.context = ctx;
		DBHelper = new DatabaseHelperSeznami(context);
	}


	//---opens the database---
	public DBAdapterSeznami open() throws SQLException 
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
	public long insertSezname(NovSeznamArtiklov stevec,int index) 
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(ST_SEZNAMA, index); 
		initialValues.put(IME_SEZNAMA, stevec.getImeSeznama());
		initialValues.put(SKUPNA_CENA, stevec.getSkupnaCena());
		initialValues.put(KOLI_NAKUOPV, stevec.getStOznacenih());
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
				ST_SEZNAMA,      //POS_NAME=1
				IME_SEZNAMA,
				SKUPNA_CENA,
				KOLI_NAKUOPV},    //POS_VALUE =2
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
					IME_SEZNAMA,
					SKUPNA_CENA,
					KOLI_NAKUOPV}, 
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
	    db.delete(TABELA_SEZNAMI, null, null);
	   }
	}

	public boolean obstajaTabela()
	{
		try {
			db = DBHelper.getWritableDatabase();
			Cursor cur;
			cur=db.rawQuery("select * from "+TABELA_SEZNAMI +";", null);
			if(cur!=null)return true;
			
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
		Cursor dataCount = db.rawQuery("select count(*) from " +TABELA_SEZNAMI , null);
		
		return dataCount.getCount();
//		return 1;
	}
}
