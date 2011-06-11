package edu.pernat.shopinglist.android;

import java.util.ArrayList;

import edu.pernat.shopinglist.android.data.DBAdapterEmail;
import edu.pernat.shopinglist.android.data.DBAdapterIzdelki;
import edu.pernat.shopinglist.android.data.DBAdapterTrgovine;
import edu.pernat.shopinglist.android.razredi.Artikli;
import edu.pernat.shopinglist.android.razredi.EmailNaslovi;
import edu.pernat.shopinglist.android.razredi.Seznam;
import edu.pernat.shopinglist.android.razredi.Seznami;
import edu.pernat.shopinglist.android.razredi.Trgovina;

import android.app.Application;
import android.database.Cursor;
import android.opengl.Visibility;

public class GlobalneVrednosti extends Application {
	public ArrayList<EmailNaslovi> lista;
	public ArrayList<Artikli> seznamArtiklov;
	public ArrayList<Trgovina> seznamTrgovin;
	public ArrayList<Seznami>  vsiSeznami;
	public ArrayList<Seznam> novSeznam;
	public int stSeznama;
	
	SeznamArrayAdapter seznamList;
	NovSeznamArrayAdapter novSeznamList;
	DBAdapterEmail db;
	DBAdapterIzdelki db1;
	String uporabnisko,geslo;
	
	public void onCreate() {
        super.onCreate(); //ne pozabi
        db=new DBAdapterEmail(this);
        db1=new DBAdapterIzdelki(this);
        /*lista = new ArrayList<Rezultat>(); //inicializirat
         fillFromDB();*/
        lista=new ArrayList<EmailNaslovi>();
        seznamArtiklov=new ArrayList<Artikli>();
        stSeznama=-1;
        vsiSeznami=new ArrayList<Seznami>();
        novSeznam=new ArrayList<Seznam>();
        init();
        fillFromDB();
        //napolniNaslov();
        seznamList = new SeznamArrayAdapter(this, R.layout.seznam_narocil,vsiSeznami); //Step 4.10 Globalna lista
        novSeznamList=new NovSeznamArrayAdapter(this,R.layout.nov_seznam, novSeznam);
        
        
	}
	
	public void dodajArtikelNaSeznam(Artikli tmp)
	{
		novSeznam.add(new Seznam("Nekdo", tmp));
		
	}
	
	public void artikelNaSeznam(Artikli tmp)
	{
		seznamArtiklov.add(tmp);
		addDBArtikel(tmp);
		
	}
	
	public void init()
	{
		//lista.add(new RazredBaza("uporabniško", "Geslo"));
		artikelNaSeznam(new Artikli(1, 2, "Mleko", "1l"));
		artikelNaSeznam(new Artikli(1, 2.4, "Pivo", "0,5l"));
		artikelNaSeznam(new Artikli(1, 0.25, "Lizika", "16g"));
		
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
		
		
		//novSeznam.add(new Seznam("Nekdo", seznamArtiklov.get(2)));
		//novSeznam.add(new Seznam("Nekdo", seznamArtiklov.get(1)));
		
		
		
	}

	public void newNovSeznam()
	{
		novSeznam=new ArrayList<Seznam>();
		
	}
	
	public void newVsiArtikli()
	{
		seznamArtiklov=new ArrayList<Artikli>();
	}
	public String getUser()
	{
		return uporabnisko;
		
	}
	
	public void napolniNaslov()
	{
		if(lista.size()==0)
		{
			lista.add(new EmailNaslovi("aaboyxx@gmail.com"));
			lista.add(new EmailNaslovi("matej.crepinsek@gmail.com"));
			lista.add(new EmailNaslovi("dejan.hrncic@uni-mb.si"));
			addDB(new EmailNaslovi("aaboyxx@gmail.com"));
			addDB(new EmailNaslovi("matej.crepinsek@gmail.com"));
			addDB(new EmailNaslovi("dejan.hrncic@uni-mb.si"));
		}
		
	}
	
	//dodajNoviElement
	public void dodajNaslov(EmailNaslovi temp)
	{
		
		lista.add(temp);
		addDB(temp);
		//novSeznam.add(new Seznam(uporabnisko, seznamArtiklov.get(1)));
	}
	
	
	public void dodajIzdelek(Artikli temp)
	{
		novSeznam.add(new Seznam(uporabnisko, temp));
		
	}
	
	
	//DB izdelki
	public void fillFromDBIzdelki() {
		db1.open();
		Cursor c = db1.getAll();
		Artikli tmp;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			tmp = new Artikli();
			tmp.setID(c.getLong( DBAdapterIzdelki.AR_ID));
			tmp.setIme(c.getString(DBAdapterIzdelki.AR_I));
			tmp.setCena(c.getDouble(DBAdapterIzdelki.AR_C));
			tmp.setKolicina(c.getString(DBAdapterIzdelki.AR_K));
			
			seznamArtiklov.add(tmp); 
		}
		c.close();
		db1.close();
	}
	public void addDBArtikel(Artikli s) {
		db1.open();
		s.setID(db1.insertArtikel(s));
		db1.close();	
	}
	public void remove(Artikli a) {
		if (a!=null)
		seznamArtiklov.remove(a);  //Step 4.10 Globalna lista
	}
	
	
	
	// konec db izdelki
	//DB dodano
	public void fillFromDB() {
		db.open();
		Cursor c = db.getAll();
		EmailNaslovi tmp;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			tmp = new EmailNaslovi();
			tmp.setID(c.getLong( DBAdapterEmail.POS__ID));
			tmp.setEmail(c.getString(DBAdapterEmail.POS_EAMIL));
			lista.add(tmp); 
		}
		c.close();
		db.close();
	}
	public void addDB(EmailNaslovi s) {
		db.open();
		s.setID(db.insertEmail(s));
		db.close();	
	}
	public void remove(EmailNaslovi a) {
		if (a!=null)
		lista.remove(a);  //Step 4.10 Globalna lista
	}
	//DB konec
}
