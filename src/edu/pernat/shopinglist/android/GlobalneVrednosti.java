package edu.pernat.shopinglist.android;

import java.util.ArrayList;

import edu.pernat.shopinglist.android.data.DBAdapterEmail;
import edu.pernat.shopinglist.android.data.DBAdapterIzdelki;
import edu.pernat.shopinglist.android.data.DBAdapterVsiSeznami;
import edu.pernat.shopinglist.android.razredi.Artikli;
import edu.pernat.shopinglist.android.razredi.EmailNaslovi;
import edu.pernat.shopinglist.android.razredi.NovSeznamArtiklov;
import edu.pernat.shopinglist.android.razredi.Seznami;
import edu.pernat.shopinglist.android.razredi.Trgovina;

import android.app.Application;
import android.database.Cursor;
import android.opengl.Visibility;
import android.util.Log;

public class GlobalneVrednosti extends Application {
	public ArrayList<EmailNaslovi> lista;
	public ArrayList<Artikli> seznamArtiklov;
	public ArrayList<Trgovina> seznamTrgovin;
	public Seznami vsiSeznami;
	public NovSeznamArtiklov novSeznam;
	public int stSeznama;
	
	SeznamArrayAdapter seznamList;
	public NovSeznamArrayAdapter novSeznamList;
	DBAdapterEmail db;
	DBAdapterIzdelki dbIzdelki;
	DBAdapterVsiSeznami dbSeznami;
	String uporabnisko,geslo;
	String bazaPolna;

	public void onCreate() {
        super.onCreate(); //ne pozabi
        db=new DBAdapterEmail(this);
        dbIzdelki=new DBAdapterIzdelki(this);
        dbSeznami=new DBAdapterVsiSeznami(this);
        /*lista = new ArrayList<Rezultat>(); //inicializirat
         fillFromDB();*/
        lista=new ArrayList<EmailNaslovi>();
        seznamArtiklov=new ArrayList<Artikli>();
        stSeznama=-1;
        vsiSeznami=new Seznami();
        novSeznam=new NovSeznamArtiklov();
       
        init();
        fillFromDB();
        //napolniNaslov();
        seznamList = new SeznamArrayAdapter(this, R.layout.seznam_narocil,vsiSeznami.getUstvarjeniSezname()); //Step 4.10 Globalna lista
        novSeznamList=new NovSeznamArrayAdapter(this,R.layout.nov_seznam, novSeznam.getNovSeznamArtiklov(), this);
        
        
	}
	
	public void dodajArtikelNaSeznam(Artikli tmp)
	{
		
		novSeznam.addArtikelNaSeznam(tmp);
	}
	
	public void dodajSeznamNaSeznam(NovSeznamArtiklov tmp)
	{
		vsiSeznami.addNovSeznam(tmp);
	}
	
	public void artikelNaSeznam(Artikli tmp)
	{
		seznamArtiklov.add(tmp);
		addDBArtikel(tmp);
		
	}
	
	public void init()
	{
		/*
		 * public Artikli(double cena, String ime, String kolicina, String opis,
			boolean oznacen, int stIzbranegaArtikla)
		 * */
		//lista.add(new RazredBaza("uporabniško", "Geslo"));
		artikelNaSeznam(new Artikli(2, "Mleko", "1l","Polmastno",false,1));
		artikelNaSeznam(new Artikli(2, "Sok", "1l","Polmastno",false,1));
		artikelNaSeznam(new Artikli(2, "Vino", "1l","Polmastno",false,1));
		artikelNaSeznam(new Artikli(2, "Moka", "1l","Polmastno",false,1));
		artikelNaSeznam(new Artikli(2, "Margerina", "1l","Polmastno",false,1));
		artikelNaSeznam(new Artikli(2, "Marmelada", "1l","Polmastno",false,1));
		artikelNaSeznam(new Artikli(2, "Čips", "1l","Polmastno",false,1));
		artikelNaSeznam(new Artikli(2, "Tuna", "1l","Polmastno",false,1));
		artikelNaSeznam(new Artikli(2, "Žemlja", "1l","Polmastno",false,1));
		artikelNaSeznam(new Artikli(2, "Posebna", "1l","Polmastno",false,1));
		
		
		dodajArtikelNaSeznam (seznamArtiklov.get(2));
		dodajArtikelNaSeznam (seznamArtiklov.get(1));
		
		
		novSeznam.setImeSeznama("Mihaaa");
		
		
		dodajSeznamNaSeznam(novSeznam);
		novSeznam=new NovSeznamArtiklov();
		
		dodajArtikelNaSeznam (seznamArtiklov.get(2));
		dodajArtikelNaSeznam (seznamArtiklov.get(2));
		dodajArtikelNaSeznam (seznamArtiklov.get(0));
		dodajArtikelNaSeznam (seznamArtiklov.get(1));
		dodajArtikelNaSeznam (seznamArtiklov.get(2));
		novSeznam.setImeSeznama("Jajo jao");
		dodajSeznamNaSeznam(novSeznam);
		novSeznam=new NovSeznamArtiklov();
		
		
		//novSeznam.add(new Seznam("Nekdo", seznamArtiklov.get(2)));
		//novSeznam.add(new Seznam("Nekdo", seznamArtiklov.get(1)));
		
		stVnosov(new RazredBaza(1, 1, "Joza",1,5));
		
	}

	public void newNovSeznam()
	{
		novSeznam=new NovSeznamArtiklov();
		
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
		novSeznam.addArtikelNaSeznam(temp);
		
	}
	
	
	//DB izdelki
	public void fillFromDBIzdelki() {
		dbIzdelki.open();
		Cursor c = dbIzdelki.getAll();
		Artikli tmp;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			tmp = new Artikli();
			
			tmp.setIme(c.getString(DBAdapterIzdelki.AR_I));
			tmp.setCena(c.getDouble(DBAdapterIzdelki.AR_C));
			tmp.setKolicina(c.getString(DBAdapterIzdelki.AR_K));
			
			seznamArtiklov.add(tmp); 
		}
		c.close();
		dbIzdelki.close();
	}
	public void addDBArtikel(Artikli s) {
		dbIzdelki.open();
		//s.setID(dbIzdelki.insertArtikel(s));
		dbIzdelki.close();	
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
	public void stVnosov(RazredBaza s)
	{
		dbSeznami.open();
		s.setID(dbSeznami.insertUporabnik(s));
		Log.e("velikost baze",dbSeznami.sizeDB()+"" ); 
		dbSeznami.close();
		
//		dbIzdelki.open();
//		Log.e("Vrjen izpis", dbIzdelki.vrniPozicije("Pivo")+"" ); ;
//		dbIzdelki.close();
		
		
	}
	
	
	
	
	
	
}
