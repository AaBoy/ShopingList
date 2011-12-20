package edu.pernat.shopinglist.android.razredi;

public class Artikli {
	
	
	 private double cena;
	 private String ime,kolicina;
	 private String opis;
	 private boolean oznacen;
	 private int steviloIzbranihAriklov;
	 private int idBaze;
	
	public Artikli(double cena, String ime, String kolicina, boolean oznacen) {
		super();
		this.cena = cena;
		this.ime = ime;
		this.kolicina = kolicina;
		this.oznacen = oznacen;
	}
	public Artikli(int id,double cena, String ime, String kolicina) {
		super();
		this.cena = cena;
		this.ime = ime;
		this.kolicina = kolicina;
		
	}

	public Artikli() {
		
	}

	public Artikli(double cena, String ime, String kolicina, String opis,
			boolean oznacen, int stIzbranegaArtikla) {
		super();
		this.cena = cena;
		this.ime = ime;
		this.kolicina = kolicina;
		this.opis = opis;
		this.oznacen = oznacen;
		this.steviloIzbranihAriklov = stIzbranegaArtikla;
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


	public boolean getOznacen() {
		return oznacen;
	}


	public void setOznacen(boolean oznacen) {
		this.oznacen = oznacen;
	}


	public int getStIzbranegaArtikla() {
		return steviloIzbranihAriklov;
	}


	public void setStIzbranegaArtikla(int stIzbranegaArtikla) {
		this.steviloIzbranihAriklov = stIzbranegaArtikla;
	}


	public String printOut()
	{
		
		return ime+";"+cena+";"+kolicina+";"+opis;
	}
	
}
