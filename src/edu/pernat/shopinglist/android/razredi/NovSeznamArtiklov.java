package edu.pernat.shopinglist.android.razredi;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.R.bool;
import android.widget.RatingBar;

/*
 * @skupnaCena - koliko stane naš seznam
 * 
 * */

public class NovSeznamArtiklov {

	public class Oznaceni
	{
		int indx;
		boolean oznaceno;
		int stKosov;
		
		public int getStKosov() {
			return stKosov;
		}
		public void setStKosov(int stKosov) {
			this.stKosov = stKosov;
		}
		public Oznaceni(int indx,boolean oznaceno)
		{
			this.indx=indx;
			this.oznaceno=oznaceno;
			
		}
		public Oznaceni(int indx,boolean oznaceno,int stKosov)
		{
			this.indx=indx;
			this.oznaceno=oznaceno;
			this.stKosov=stKosov;
		}
		public Oznaceni(int indx)
		{
			this.indx=indx;
			
		}
		public void setOznaci(boolean oz)
		{
			oznaceno=oz;
		}
		public boolean getOznaceno()
		{
			return oznaceno;
		}
		
	}
	
	private ArrayList<Artikli> NovSeznamArtiklov;
	public ArrayList<Oznaceni> oznacee;
	private double skupnaCena;
	private String imeSeznama;
	private int stOznacenih;
	private int idBaze;
	
	public int getStOznacenih() {
		prestejOznacene();
		return stOznacenih;
	}


	public void setStOznacenih(int stOznacenih) {
	
		this.stOznacenih = stOznacenih;
		
	}


	public NovSeznamArtiklov()
	{
		NovSeznamArtiklov=new ArrayList<Artikli>();
		oznacee=new ArrayList<Oznaceni>();
	}

	public ArrayList<Artikli> getNovSeznamArtiklov()
	{
		return NovSeznamArtiklov;
	}
	
	public double getSkupnaCena() {
		return skupnaCena;
	}
	public void setSkupnaCena(double skupnaCena) {
		this.skupnaCena = skupnaCena;
	}
	public String getImeSeznama() {
		return imeSeznama;
	}
	public void setImeSeznama(String imeSeznama) {
		this.imeSeznama = imeSeznama;
	}
	
	public void Oznaci(int index)
	{
		if(oznacee.get(index).oznaceno==true)
			oznacee.get(index).oznaceno=false;
		else
			oznacee.get(index).oznaceno=true;
		
		prestejOznacene();
	}
	public void Oznaci2(int index,boolean vrednost)
	{
		oznacee.get(index).setOznaci(vrednost);
	}
	public boolean jeOznacen(int index)
	{
		return oznacee.get(index).getOznaceno();
	}
	
	public void prestejOznacene()
	{
		
		int vmesni=0;
		for(int i=0;i<oznacee.size();i++)
		{
			if(oznacee.get(i).oznaceno==true)
			{
				vmesni++;
			}
		}
		stOznacenih=vmesni;
	}
	
	public int getIdBaze() {
		return idBaze;
	}


	public void setIdBaze(int idBaze) {
		this.idBaze = idBaze;
	}


	public void sestejCeno()
	{
		double vmesni=0;
		for(int i=0;i<NovSeznamArtiklov.size();i++)
		{
			vmesni+=NovSeznamArtiklov.get(i).getCena();
		}
		DecimalFormat twoDForm= new DecimalFormat("#.##");
		
		this.skupnaCena=vmesni;//Double.valueOf(twoDForm.format(vmesni));
	}
	
	public void addArtikelNaSeznam(Artikli tmp)
	{
		NovSeznamArtiklov.add(tmp);
		oznacee.add(new Oznaceni(NovSeznamArtiklov.size()-1));
		sestejCeno();
	}
	public void addArtikelNaSeznam(Artikli tmp,boolean ozn)
	{
		NovSeznamArtiklov.add(tmp);
		oznacee.add(new Oznaceni(NovSeznamArtiklov.size()-1,ozn));
		sestejCeno();
	}
	
	public int getVelikostSeznamaArtiklov()
	{
		return NovSeznamArtiklov.size();
	}
	
	public int getStKosov(int index)
	{
		return oznacee.get(index).getStKosov();
	}
	public void setStKosov(int index,int stKosov1)
	{
		oznacee.get(index).setStKosov(stKosov1);
	}
	
	
}
