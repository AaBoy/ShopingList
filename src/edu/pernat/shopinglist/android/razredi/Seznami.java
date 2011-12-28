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
	public void replaceSeznam(int index, NovSeznamArtiklov tmp)
	{
		ustvarjeniSeznami.add(index, tmp);
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
	
	public void vstaviSeznamNaArtikel(int indexSeznam,int indexArtikle, Artikli tmp, boolean ozn)
	{
	
		ustvarjeniSeznami.get(indexSeznam).addArtikelNaSeznam(tmp, ozn);
		//ustvarjeniSeznami.get(indexSeznam).oznacee.add(new Oznaceni(indexArtikle, ozn));
		
	 
	}
	public int size()
	{
		return ustvarjeniSeznami.size();
	}

	
}
