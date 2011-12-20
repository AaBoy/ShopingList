package edu.pernat.shopinglist.android.razredi;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.widget.RatingBar;

/*
 * @skupnaCena - koliko stane naš seznam
 * 
 * */

public class NovSeznamArtiklov {

	private ArrayList<Artikli> NovSeznamArtiklov;
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
		if(NovSeznamArtiklov.get(index).getOznacen()==true)
			NovSeznamArtiklov.get(index).setOznacen(false);
		else
			NovSeznamArtiklov.get(index).setOznacen(true);
		
		prestejOznacene();
	}
	
	public boolean jeOznacen(int index)
	{
		return NovSeznamArtiklov.get(index).getOznacen();
	}
	
	public void prestejOznacene()
	{
		int vmesni=0;
		for(int i=0;i<NovSeznamArtiklov.size();i++)
		{
			if(NovSeznamArtiklov.get(i).getOznacen()==true)
				vmesni++;
		}
		stOznacenih=vmesni;
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
		sestejCeno();
	}
	
	public int getVelikostSeznamaArtiklov()
	{
		return NovSeznamArtiklov.size();
	}
	
	
}
