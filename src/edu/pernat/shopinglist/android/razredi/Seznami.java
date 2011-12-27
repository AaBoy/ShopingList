package edu.pernat.shopinglist.android.razredi;

import java.util.ArrayList;

import edu.pernat.shopinglist.android.UstvariNovIzdelek;

public class Seznami {

	public ArrayList<NovSeznamArtiklov> ustvarjeniSeznami;
	
	public Seznami()
	{
		ustvarjeniSeznami=new ArrayList<NovSeznamArtiklov>();
	}
	
	public ArrayList<NovSeznamArtiklov> getUstvarjeniSezname()
	{
		return ustvarjeniSeznami;
	}
	public void odstraniNovSeznamArtiklov(int index)
	{
		ustvarjeniSeznami.remove(index);
	}
	
	public void addNovSeznam(NovSeznamArtiklov tmp)
	{
		ustvarjeniSeznami.add(tmp);
	}
	public void removNovSeznam(int index)
	{
		ustvarjeniSeznami.remove(index);
	}
	
	public double getSkupanCena(int index)
	{
		return ustvarjeniSeznami.get(index).getSkupnaCena();
	}
	public String getImeNovegaSeznama(int index)
	{
		return ustvarjeniSeznami.get(index).getImeSeznama();
	}
	public int getSteviloOznacenihArtiklov(int index)
	{
		ustvarjeniSeznami.get(index).prestejOznacene();
		return ustvarjeniSeznami.get(index).getStOznacenih();
	}
	public int size()
	{
		return ustvarjeniSeznami.size();
	}

	
}
