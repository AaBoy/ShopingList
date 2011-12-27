package edu.pernat.shopinglist.android.razredi;

public class Artikli {
	
	
	 private double cena;
	 private String ime,kolicina;
	 private String opis;
	// private boolean oznacen;
	 
	 private int idBaze;
	
	public Artikli(double cena, String ime, String kolicina, String opis, int id) {
		super();
		this.cena = cena;
		this.ime = ime;
		this.kolicina = kolicina;
		this.opis=opis;
		this.idBaze=id;
		//this.oznacen = oznacen;
	}
	public int getIdBaze() {
		return idBaze;
	}
	public void setIdBaze(int idBaze) {
		this.idBaze = idBaze;
	}
	public Artikli(int id,double cena, String ime, String kolicina) {
		super();
		this.cena = cena;
		this.ime = ime;
		this.kolicina = kolicina;
		
	}

	public Artikli() {
		
	}

	public Artikli(double cena, String ime, String kolicina, String opis) {
		super();
		this.cena = cena;
		this.ime = ime;
		this.kolicina = kolicina;
		this.opis = opis;
		
		
	}


	public double getCena() {
		return cena;
	}


	public void setCena(double cena) {
		this.cena = cena;
	}


	public String getIme() {
		return ime;
	}


	public void setIme(String ime) {
		this.ime = ime;
	}


	public String getKolicina() {
		return kolicina;
	}


	public void setKolicina(String kolicina) {
		this.kolicina = kolicina;
	}


	public String getOpis() {
		return opis;
	}


	public void setOpis(String opis) {
		this.opis = opis;
	}


//	public boolean getOznacen() {
//		return oznacen;
//	}
//
//
//	public void setOznacen(boolean oznacen) {
//		this.oznacen = oznacen;
//	}



	public String printOut()
	{
		
		return ime+";"+cena+";"+kolicina+";"+opis;
	}
	
}
