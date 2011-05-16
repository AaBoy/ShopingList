package edu.pernat.shopinglist.android.data;

import java.sql.Date;

import edu.pernat.shopinglist.android.RazredBaza;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;



public class DBAdapterTrgovine implements BaseColumns {
	public static final  String TAG="ShoppingList";

	public static final  String VALUE = "i_value";
	public static final  String NAME = "s_name";
	public static final  int POS__ID=0;
	public static final  int POS_NAME=1;
	public static final  int POS_VALUE=2;
	
	public static final  int UPO_ID=0;
	public static final  int UPO_UPO=1;
	public static final  int UPO_GES=2;
	public static final  String UPORABNISKO="Uporabnisko";
	public static final  String GESLO="Geslo";
	
	public static final  int TRGO_ID=0;
	public static final  int TRGO_IME=1;
	public static final  String NAME_TRGO="ImeTrgovine";
	
	public static final  int KOLI_AR_ID=0;
	public static final  String KOLI_AR_VRSTA_KOLI="VrstaKolicine";
	public static final  String KOLI_AR_KOLI="Kolicina";
	public static final  int KOLI_AR_VK=1;
	public static final  int KOLI_AR_KO=2;
	
	public static final  int SEZNAM_ID=0;
	public static final  String SEZNAM_DATUM="Datum";
	public static final  String SEZNAM_SKU_CENA="SkupnaCena";
	public static final  int SEZNA_DATE=1;
	public static final  int SEZNAM_SK_CE=2;
	
	public static final  int ARTI_ID=0;
	public static final  String ARTI_NAME_S="Ime";
	public static final  String ARTI_KOLI_ID="KolicinaArtikla";
	public static final  int ARTI_KOL_ID=1;
	public static final  int ARTI_IME_I=2;
	
	public static final  int V_SA_SENAM_ID=0;
	public static final  int V_SA_ARTI_ID=1;
	public static final  String V_SA_SEZN_S="SeznamID";
	public static final  String V_SA_ARTI_S="ArtikliID";
	public static final  int V_SA_SKUP_CENA=2;
	public static final  String V_SA_SKUP_S="SkupnaCena";
	
	
	public static final  int V_US_UPO_ID=0;
	public static final  int V_US_SENZ_ID=1;
	public static final  String V_US_UPO_S="Uporabnik_ID";
	public static final  String V_US_SENZ_S="Seznam_ID";
	
	public static final  int V_TA_TRG_ID=0;
	public static final  int V_TA_ARTI_ID=1;
	public static final  String V_TA_TRG_S="Trgovine_ID";
	public static final  String V_TA_ARTI_S="Artikli_ID";

	public static final  String TABELA_UPORABMIK="uporabnik";
	public static final  String TABELA_TRGOVINE="Trgovine";
	public static final  String TABELA_TGRO_HAS_ARTI="Trgovine_has_Artikli";
	public static final  String TABELA_UPOR_HAS_SEZ="Uporabnik_has_Seznam";
	public static final  String TABELA_ARTI="Artikli";
	public static final  String TABELA_KOL_ART="KolicinaArtikla";
	public static final  String TABELA_SEZNAM="Seznam";
	public static final  String TABELA_SEZ_HAS_ARTI="Seznam_has_Aartikli";
	public static final  String TABLE="stevec";


	private final Context context;

	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapterTrgovine(Context ctx) 
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}


	//---opens the database---
	public DBAdapterTrgovine open() throws SQLException 
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
		initialValues.put(UPORABNISKO, stevec.getUpo()); 
		initialValues.put(GESLO, stevec.getGeslo()); 
		return db.insert(TABELA_UPORABMIK, null, initialValues);
		//return 1;
	}

	//---deletes a particular title---
	public boolean deleteStevec(long rowId) 
	{
		return db.delete(TABELA_UPORABMIK, _ID + "=" + rowId, null) > 0;
	}

	//---retrieves all the titles---
	public Cursor getAll() 
	{
		return db.query(TABELA_UPORABMIK, new String[] {
				_ID,       //POS__ID=0;
				UPORABNISKO,      //POS_NAME=1
				GESLO},    //POS_VALUE =2
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
					UPORABNISKO,
					GESLO}, 
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
}
