package edu.pernat.shopinglist.android;

import java.util.ArrayList;
import edu.pernat.shopinglist.android.data.DBAdapterTrgovine;

import android.app.Application;
import android.database.Cursor;

public class GlobalneVrednosti extends Application {
	public ArrayList<RazredBaza> lista;
	SeznamArrayAdapter seznamList;
	DBAdapterTrgovine db;
	String uporabnisko,geslo;
	
	public void onCreate() {
        super.onCreate(); //ne pozabi
        db=new DBAdapterTrgovine(this);
        /*lista = new ArrayList<Rezultat>(); //inicializirat
         fillFromDB();*/
        lista=new ArrayList<RazredBaza>();
        init();
        seznamList = new SeznamArrayAdapter(this, R.layout.seznam_narocil,lista); //Step 4.10 Globalna lista
        
	}
	
	public void init()
	{
		lista.add(new RazredBaza("uporabniško", "Geslo"));
		
		
	}
	
	//dodajNoviElement
	public void dodaj(RazredBaza temp)
	{
		lista.add(temp);
		
	}
	
	//DB dodano
	public void fillFromDB() {
		db.open();
		Cursor c = db.getAll();
		RazredBaza tmp;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			tmp = new RazredBaza();
			tmp.setID(c.getLong(DBAdapterTrgovine.UPO_ID));
			tmp.setUpo(c.getString(DBAdapterTrgovine.UPO_UPO));
			tmp.setGeslo(c.getString(DBAdapterTrgovine.UPO_GES));
			
			
			lista.add(tmp); 
		}
		c.close();
		db.close();
	}
	public void addDB(RazredBaza s) {
		db.open();
		s.setID(db.insertUporabnik(s));
		db.close();	
	}
	//DB konec
}
