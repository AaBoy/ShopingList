package edu.pernat.shopinglist.android;

import java.util.ArrayList;

import edu.pernat.shopinglist.android.data.DBAdapterEmail;
import edu.pernat.shopinglist.android.data.DBAdapterIzdelki;
import edu.pernat.shopinglist.android.data.DBAdapterTrgovina;
import edu.pernat.shopinglist.android.data.DBAdapterSeznami;
import edu.pernat.shopinglist.android.data.DBAdapterVmesnaTabela;
import edu.pernat.shopinglist.android.razredi.Artikli;
import edu.pernat.shopinglist.android.razredi.EmailNaslovi;
import edu.pernat.shopinglist.android.razredi.NovSeznamArtiklov;
import edu.pernat.shopinglist.android.razredi.RazredBaza;
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
	DBAdapterSeznami dbSeznami;
	DBAdapterTrgovina dbTrgovina;
	DBAdapterVmesnaTabela dbVmesnaTabela;
	String uporabnisko,geslo;
	String bazaPolna;

	public void onCreate() {
        super.onCreate(); //ne pozabi
        db=new DBAdapterEmail(this);
        dbIzdelki=new DBAdapterIzdelki(this);
        dbSeznami=new DBAdapterSeznami(this);
        dbTrgovina=new DBAdapterTrgovina(this);
        dbVmesnaTabela=new DBAdapterVmesnaTabela(this);
        /*lista = new ArrayList<Rezultat>(); //inicializirat
         fillFromDB();*/
        lista=new ArrayList<EmailNaslovi>();
        seznamArtiklov=new ArrayList<Artikli>();
        stSeznama=-1;
        vsiSeznami=new Seznami();
        novSeznam=new NovSeznamArtiklov();
        seznamTrgovin=new ArrayList<Trgovina>();
//        fillFromDB();
        //napolniNaslov();
        seznamList = new SeznamArrayAdapter(this, R.layout.seznam_narocil,vsiSeznami.getUstvarjeniSezname()); //Step 4.10 Globalna lista
        novSeznamList=new NovSeznamArrayAdapter(this,R.layout.nov_seznam, novSeznam.getNovSeznamArtiklov(), this);
        
        
	}
	
	public void dodajArtikelNaSeznam(Artikli tmp)
	{
		
		novSeznam.addArtikelNaSeznam(tmp);
		
	}
	public void dodajArtikelNaSeznam(Artikli tmp,boolean oz)
	{
		
		novSeznam.addArtikelNaSeznam(tmp,oz);
		
	}
	
	public void dodajSeznamNaSeznam(NovSeznamArtiklov tmp)
	{
		vsiSeznami.addNovSeznam(tmp);
	}
	public void dodajTrgovinoNaSeznam(Trgovina tmp)
	{
		seznamTrgovin.add(tmp);
		addDBTrgovina(tmp);
	}
	
	public void artikelNaSeznamInBazo(Artikli tmp)
	{
		seznamArtiklov.add(tmp);
		addDBArtikel(tmp);
		
	}
	
	public void poskusni()
	{
		RazredBaza tmp=new RazredBaza(1, 1, "Moja", 0, 1);
		dodajArtikelNaSeznam(seznamArtiklov.get(tmp.getStArtikla()));
		novSeznam.setStKosov(novSeznam.getVelikostSeznamaArtiklov()-1, tmp.getKolicina());
		novSeznam.setImeSeznama(tmp.getImeSeznama());
		//novSeznam.Oznaci(novSeznam.getVelikostSeznamaArtiklov()-1);
		dodajSeznamNaSeznam(novSeznam);
		novSeznam=new NovSeznamArtiklov();
		
		tmp=new RazredBaza(2, 3, "Moja", 0, 1);
		dodajArtikelNaSeznam(seznamArtiklov.get(tmp.getStArtikla()));
		novSeznam.setStKosov(novSeznam.getVelikostSeznamaArtiklov()-1, tmp.getKolicina());
		novSeznam.setImeSeznama(tmp.getImeSeznama());
		//novSeznam.Oznaci(novSeznam.getVelikostSeznamaArtiklov()-1);
		dodajSeznamNaSeznam(novSeznam);
		novSeznam=new NovSeznamArtiklov();
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
			tmp.setOpis(c.getString(DBAdapterIzdelki.AR_O));
			tmp.setIdBaze(c.getInt(DBAdapterIzdelki.AR_ID));
			
			seznamArtiklov.add(tmp); 
		}
		c.close();
		dbIzdelki.close();
	}
	public void addDBArtikel(Artikli s) {
		dbIzdelki.open();
		dbIzdelki.insertArtikel(s);
		dbIzdelki.close();	
	}
	
	public long stVrsticVDBArtikli()
	{
		return dbIzdelki.stVrstic();
	}
	public void sprazniBazoArtikli()
	{
		dbIzdelki.open();
		dbIzdelki.deleteAll();
		dbIzdelki.close();
	}
	
	public void remove(Artikli a) {
		if (a!=null)
		seznamArtiklov.remove(a);  //Step 4.10 Globalna lista
	}
	
	//*vSI PODATKI*/
	public void addDBSeznami(NovSeznamArtiklov en, int index)
	{
		dbSeznami.open();
		dbSeznami.insertSezname(en,index);
		dbSeznami.close();
		
	}
	public boolean obstajaTabelaSeznami()
	{
		return dbSeznami.obstajaTabela();
	}
	
	public void napolniBazoSeznamov()
	{
		dbSeznami.deleteAll();
		if(vsiSeznami.getUstvarjeniSezname().size()>0)
		{		
			//if(dbSeznami.sizeDB()>0) dbSeznami.deleteAll();
			for(int i=0;i<vsiSeznami.getUstvarjeniSezname().size();i++)
			{
				NovSeznamArtiklov tmp=vsiSeznami.getUstvarjeniSezname().get(i);
				addDBSeznami(tmp,i);
			}
		}
	}
	public void fillFromDBSeznami() {
		dbSeznami.open();
		Cursor c = dbSeznami.getAll();
		NovSeznamArtiklov tmp;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			tmp = new NovSeznamArtiklov();
			tmp.setImeSeznama(c.getString(DBAdapterSeznami.IME_SEZNAMA_ID));
			tmp.setStOznacenih(c.getInt(DBAdapterSeznami.KOL_NAKUPOV));
			tmp.setIdBaze(c.getInt(DBAdapterSeznami.SEZNAM_ID));
			tmp.setSkupnaCena(c.getDouble(DBAdapterSeznami.SKUPNA_CENA_ID));
			dodajSeznamNaSeznam(tmp);
			
		}
		Log.e("idSeznam", ""+vsiSeznami.size());
		
		c.close();
		dbSeznami.close();
	}
	
	int id;


/*
 * Vmesna tabela polni in pridobi
 * */

	public void fillFromDBVmesni() {
		dbVmesnaTabela.open();
		Cursor c = dbVmesnaTabela.getAll();
		long idSeznama=0, idArtikla;
		long vmesni=0;
		int oznacen;
		
//		if(!c.isAfterLast())
//		{
//			vmesni=c.getLong(DBAdapterVmesnaTabela.SEZNAM_ID);
//			newNovSeznam();
//			Log.e("Prvi id seznama", vmesni+"");
//		}
		c = dbVmesnaTabela.getAll();
		int stevec=0;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			
			idSeznama=c.getLong(DBAdapterVmesnaTabela.SEZNAM_ID);
			idArtikla=c.getLong(DBAdapterVmesnaTabela.ARTIKLE_ID);

			oznacen=c.getInt(DBAdapterVmesnaTabela.OZNACEN);
			if(vmesni!=idSeznama)
			{
				if(oznacen!=1)
					vsiSeznami.vstaviSeznamNaArtikel((int)idSeznama, stevec, seznamArtiklov.get((int) idArtikla-1),false);
				else 
					vsiSeznami.vstaviSeznamNaArtikel((int)idSeznama, stevec, seznamArtiklov.get((int) idArtikla-1), true);
				
				
				stevec++;
				vmesni=idSeznama;
			}else
			{
				if(oznacen!=1)
					vsiSeznami.vstaviSeznamNaArtikel((int)idSeznama, stevec, seznamArtiklov.get((int) idArtikla-1),false);
				else 
					vsiSeznami.vstaviSeznamNaArtikel((int)idSeznama, stevec, seznamArtiklov.get((int) idArtikla-1), true);
				
				
				stevec++;
				vmesni=idSeznama;
			}	
		}
		c.close();
		dbVmesnaTabela.close();
	}
	
	public boolean obstajaVmensaTabela()
	{
		return dbVmesnaTabela.obstajaTabela();
	}
	
	public void napolniVmesno()
	{
		dbVmesnaTabela.open();
		dbVmesnaTabela.deleteAll();
		for(int i=0;i<vsiSeznami.size();i++)
		{
			NovSeznamArtiklov tmp= vsiSeznami.getUstvarjeniSezname().get(i);
			
			for(int j=0;j<tmp.getVelikostSeznamaArtiklov();j++)
			{
				dbVmesnaTabela.insertSezname(i, tmp.getNovSeznamArtiklov().get(j).getIdBaze(), tmp.jeOznacen(j)? 1 : 0);
			}
			
		}

		dbVmesnaTabela.close();
	}
	
	/*
	 *Konec vmesne baze
	 * 
	 * */
	
	//EMAILI
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
	///Trgovina
	public void addDBTrgovina(Trgovina en)
	{
		dbTrgovina.open();
		dbTrgovina.insertTrgovina(en);
		dbTrgovina.close();
		
	}
	
	public void fillFromDBTrgovina() {
		dbTrgovina.open();
		Cursor c = dbTrgovina.getAll();
		Trgovina tmp;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			tmp = new Trgovina();
			
			tmp.setKraj(c.getString(DBAdapterTrgovina.TRGOVINA_KRAJ));
			tmp.setNaslov(c.getString(DBAdapterTrgovina.TRGOVINA_NASLOV));
			
			
			seznamTrgovin.add(tmp); 
		}
		c.close();
		dbTrgovina.close();
	}
	
	public void sprazniBazoTrgovina()
	{
		dbTrgovina.open();
		dbTrgovina.deleteAll();
		dbTrgovina.close();
	}
	
	//konec trgovine
	
	public void remove(EmailNaslovi a) {
		if (a!=null)
		lista.remove(a);  //Step 4.10 Globalna lista
	}
	//DB konec
	
	
	//DB obstaja tabela
	
	
	public boolean obstajaEmailTabela()
	{
		return db.obstajaTabela();
	}
	
	public boolean obstajaTrgovinaTabela()
	{
		return dbTrgovina.obstajaTabela();
	}
	
	
	
	public boolean obstajaIzdelkiTabela()
	{
		return dbIzdelki.obstajaTabela();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
