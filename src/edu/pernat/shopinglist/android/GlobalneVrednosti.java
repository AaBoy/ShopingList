package edu.pernat.shopinglist.android;

import java.util.ArrayList;
import edu.pernat.shopinglist.android.data.DBAdapterTrgovine;
import edu.pernat.shopinglist.android.razredi.Artikli;
import edu.pernat.shopinglist.android.razredi.Seznam;
import edu.pernat.shopinglist.android.razredi.Seznami;
import edu.pernat.shopinglist.android.razredi.Trgovina;

import android.app.Application;
import android.database.Cursor;
import android.opengl.Visibility;

public class GlobalneVrednosti extends Application {
	public ArrayList<RazredBaza> lista;
	public ArrayList<Artikli> seznamArtiklov;
	public ArrayList<Trgovina> seznamTrgovin;
	public ArrayList<Seznami>  vsiSeznami;
	public ArrayList<Seznam> novSeznam;
	public int stSeznama;
	
	SeznamArrayAdapter seznamList;
	public NovSeznamArrayAdapter novSeznamList;
	DBAdapterTrgovine db;
	String uporabnisko,geslo;
	
	public void onCreate() {
        super.onCreate(); //ne pozabi
        db=new DBAdapterTrgovine(this);
        /*lista = new ArrayList<Rezultat>(); //inicializirat
         fillFromDB();*/
        lista=new ArrayList<RazredBaza>();
        seznamArtiklov=new ArrayList<Artikli>();
        stSeznama=-1;
        vsiSeznami=new ArrayList<Seznami>();
        novSeznam=new ArrayList<Seznam>();
        init();
        seznamList = new SeznamArrayAdapter(this, R.layout.seznam_narocil,vsiSeznami); //Step 4.10 Globalna lista
        novSeznamList=new NovSeznamArrayAdapter(this,R.layout.nov_seznam, novSeznam);
	}
	
	public void init()
	{
		lista.add(new RazredBaza("uporabniško", "Geslo"));
		seznamArtiklov.add(new Artikli(1, 2, "Mleko", "1 liter"));
		seznamArtiklov.add(new Artikli(1, 2.4, "Pivo", "0,5 liter"));
		seznamArtiklov.add(new Artikli(1, 0.25, "Lizika", "16g"));
		
		
		novSeznam.add(new Seznam("Nekdo", seznamArtiklov.get(2)));
		novSeznam.add(new Seznam("Nekdo", seznamArtiklov.get(1)));
		novSeznam.get(0).imeSeznama="Miha kovačev";
		novSeznam.get(1).imeSeznama="Miha kovačev";
		
		
		vsiSeznami.add(new Seznami(novSeznam));
		novSeznam=new ArrayList<Seznam>();
		
		novSeznam.add(new Seznam("Nekdo", seznamArtiklov.get(2)));
		novSeznam.add(new Seznam("Nekdo", seznamArtiklov.get(2)));
		novSeznam.add(new Seznam("Nekdo", seznamArtiklov.get(0)));
		novSeznam.add(new Seznam("Nekdo", seznamArtiklov.get(1)));
		novSeznam.add(new Seznam("Nekdo", seznamArtiklov.get(2)));
		novSeznam.get(0).imeSeznama="Janez kaj";
		vsiSeznami.add(new Seznami(novSeznam));
		novSeznam=new ArrayList<Seznam>();
		
		
		novSeznam.add(new Seznam("Nekdo", seznamArtiklov.get(2)));
		novSeznam.add(new Seznam("Nekdo", seznamArtiklov.get(1)));
		
		
		
	}
	
	public void pobrisiNovSeznam()
	{
		novSeznam.clear();
	}
	
	public String getUser()
	{
		return uporabnisko;
		
	}
	
	//dodajNoviElement
	public void dodaj(RazredBaza temp)
	{
		lista.add(temp);
		novSeznam.add(new Seznam(uporabnisko, seznamArtiklov.get(1)));
	}
	
	
	public void dodajIzdelek(Artikli temp)
	{
		novSeznam.add(new Seznam(uporabnisko, temp));
		
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
