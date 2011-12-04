package edu.pernat.shopinglist.android.razredi;

public class Artikli {
	
	long id_trgovina;
	 double cena;
	 String ime,kolicina;
	 String opis;
	
	public Artikli(long idTrgovina, double cena, String ime, String kolicina,
			String opis) {
		super();
		id_trgovina = idTrgovina;
		this.cena = cena;
		this.ime = ime;
		this.kolicina = kolicina;
		this.opis = opis;
	}
	public Artikli(long iD,double c,String i,String k )
	{
		id_trgovina=iD;
		cena=c;
		ime=i;
		kolicina=k;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	public Artikli()
	{}
	
	
	public void setIme(String i)
	{
		ime=i;
	}
	public void setKolicina(String k)
	{kolicina=k;}
	public void setCena(double c)
	{
		cena=c;;
	}
	public void setID(long i)
	{
		id_trgovina=i;
	}
	
	
	public long getId_trgovina()
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
	public String getKolicina()
	{return kolicina;}
	
	public String printOut()
	{
		
		return ""+id_trgovina+";"+ime+";"+cena+";"+kolicina+";"+opis;
	}
	
}
