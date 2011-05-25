package edu.pernat.shopinglist.android.razredi;

public class Artikli {
	
	int id_trgovina;
	double cena;
	String ime,kolicina;
	
	public Artikli(int iD,double c,String i,String k )
	{
		id_trgovina=iD;
		cena=c;
		ime=i;
		kolicina=k;
	}
	
	public int getId_trgovina()
	{
		return id_trgovina;
	}
	
	public double getCena()
	{
		return cena;
	}
	public String getIme()
	{
		return ime;
	}
	
}
