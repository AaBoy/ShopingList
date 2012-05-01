package edu.pernat.shopinglist.android.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.BaseColumns;
import android.util.Log;
import edu.pernat.shopinglist.android.razredi.Artikli;
import edu.pernat.shopinglist.android.razredi.NovSeznamArtiklov;



public class DBAdapterPriljubljeno implements BaseColumns {
	public static final  String TAG="ShoppingList";
	

	public static final int ARTIKLE_ID=1;
	public static final int KOLIKO_KRAT_IZBRAN=2;
	



	public static final  String ST_ARTIKLA="stArtikla";
	public static final String KOLIKO_KRAT_IZBRAN_STRING="izbran";
	

	public static final  String TABELA_PRILJUBLJENI="priljubljen";
	public static final  String TABLE="priljubljeni";


	private final Context context;

	private DatabaseHelperPriljubljeno DBHelper;
	private SQLiteDatabase db;

	public DBAdapterPriljubljeno(Context ctx) 
	{
		this.context = ctx;
		DBHelper = new DatabaseHelperPriljubljeno(context);
	}


	//---opens the database---
	public DBAdapterPriljubljeno open() throws SQLException 
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
	public long insertSezname(Artikli tmp) 
	{
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(ST_ARTIKLA, tmp.getIdBaze());
		initialValues.put(KOLIKO_KRAT_IZBRAN_STRING, 1);
		return db.insert(TABELA_PRILJUBLJENI, null, initialValues);
		//return 1;
	}

	//---deletes a particular title---
	public boolean deleteStevec(long rowId) 
	{
		return db.delete(TABELA_PRILJUBLJENI, _ID + "=" + rowId, null) > 0;
	}

	//---retrieves all the titles---
	public Cursor getAll() 
	{
		
		return db.query(TABELA_PRILJUBLJENI, new String[] {
				ST_ARTIKLA,
				KOLIKO_KRAT_IZBRAN_STRING
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
					ST_ARTIKLA,
					KOLIKO_KRAT_IZBRAN_STRING
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
	    db.delete(TABELA_PRILJUBLJENI, null, null);
	   }
	}

	public boolean obstajaTabela()
	{
		try {
			db = DBHelper.getWritableDatabase();
			Cursor cur;
		
			cur=db.rawQuery("select * from "+TABELA_PRILJUBLJENI +";", null);
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
		Cursor dataCount = db.rawQuery("select count(*) from " +TABELA_PRILJUBLJENI , null);
		
		return dataCount.getCount();
//		return 1;
	}
	public int idDBArtikelIzPriljubljeni(int i)
	{
		db = DBHelper.getWritableDatabase();
		Cursor dataCount=db.query(TABELA_PRILJUBLJENI, new String[]{ST_ARTIKLA,KOLIKO_KRAT_IZBRAN_STRING,_ID}, ST_ARTIKLA
				+ "="+i+"", null, null, null, null);
		if(dataCount.getCount()>0)
		{
			dataCount.moveToFirst();
			
			Log.e("ID izdelka", dataCount.getInt(0)+" ID_Izdelka "+i);
			return (dataCount.getInt(0)-1);
		}
		 return 1;
	}
	
	public int vrniIndeks(int stArtikla)
	{
		db = DBHelper.getWritableDatabase();
		Cursor dataCount=db.query(TABELA_PRILJUBLJENI, new String[]{ST_ARTIKLA,KOLIKO_KRAT_IZBRAN_STRING,_ID}, ST_ARTIKLA
				+ "="+stArtikla+"", null, null, null, null);
		if(dataCount.getCount()>0)
		{
			dataCount.moveToFirst();
			
			Log.e("VrniIndeks", stArtikla+"   ;  "+dataCount.getInt(2));
			return dataCount.getInt(2);
		}
		 return 1;
	}
	
	public int update(NovSeznamArtiklov tmp)
	{
		db = DBHelper.getWritableDatabase();
		
		int velikost=0;
		
		for(int i=0;i<tmp.getNovSeznamArtiklov().size();i++)
		{
			ContentValues cv1;
			int stUpdatov=0;
			try {
				
				Cursor dataCount=db.query(TABELA_PRILJUBLJENI, new String[]{ST_ARTIKLA,KOLIKO_KRAT_IZBRAN_STRING,_ID}, ST_ARTIKLA
						+ "="+tmp.getNovSeznamArtiklov().get(i).getIdBaze()+"", null, null, null, null);
				if(dataCount.getCount()>0)
				{
					dataCount.moveToFirst();
					

					velikost=dataCount.getInt(1);
//					Log.e("Ime artikla", dataCount.getString(0));
//					Log.e("Kolicina artikla", dataCount.getString(1));
//					Log.e("ID artikla", dataCount.getString(2));
					
					cv1 =  new ContentValues();

					cv1.put(KOLIKO_KRAT_IZBRAN_STRING, velikost+1);
					Log.e("Velikost",""+ cv1.getAsInteger(KOLIKO_KRAT_IZBRAN_STRING));
					stUpdatov= db.update(TABELA_PRILJUBLJENI, cv1, ST_ARTIKLA+"='"+tmp.getNovSeznamArtiklov().get(i).getIdBaze()+"'",null);
					velikost=0;
				}
				
				if(stUpdatov==0)
					insertSezname(tmp.getNovSeznamArtiklov().get(i));
//				
				dataCount.close();
				
			} catch (SQLiteException e) {
				// TODO: handle exception
				Log.e("DBAdapterPriljubljeni + Update", e.toString());
			}
			
			
		}
		
		return 2;
	}
	
}
