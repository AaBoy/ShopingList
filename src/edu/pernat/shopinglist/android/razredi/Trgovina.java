package edu.pernat.shopinglist.android.razredi;

public class Trgovina {
	
	String naslov, kraj;
	int id;
	
	public Trgovina(String n, String k)
	{
		naslov=n;
		kraj=k;

	}
	public Trgovina()
	{}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getKraj() {
		return kraj;
	}

	public void setKraj(String kraj) {
		this.kraj = kraj;
	}
	
	public String print()
	{
		return naslov+", "+kraj;
	}
}
