package edu.pernat.shopinglist.android.razredi;

public class PrijavniPodatki {
	String uporabnisko, geslo;

	public String getUporabnisko() {
		return uporabnisko;
	}

	public void setUporabnisko(String uporabnisko) {
		this.uporabnisko = uporabnisko;
	}

	public String getGeslo() {
		return geslo;
	}

	public void setGeslo(String geslo) {
		this.geslo = geslo;
	}

	public PrijavniPodatki(String uporabnisko, String geslo) {
		super();
		this.uporabnisko = uporabnisko;
		this.geslo = geslo;
	}

	public PrijavniPodatki() {
		super();
	}
	
}
