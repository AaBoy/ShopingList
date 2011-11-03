package edu.pernat.shopinglist.android.razredi;

import java.util.ArrayList;

public class Seznami {

	ArrayList<	ArrayList<Seznam>> posamezniSeznam=new ArrayList<ArrayList<Seznam>>();
	public int steviloIzbranih;
	public Seznami()
	{
		steviloIzbranih=0;
		
	}
	public Seznami(ArrayList<Seznam> temp)
	{
		
		posamezniSeznam.add(temp);
	}
	
	public void dodaj(ArrayList<Seznam> temp)
	{
		posamezniSeznam.add(temp);
	}
	
	public String getImeSeznama(int i)
	{
		return posamezniSeznam.get(i).get(i).imeSeznama;
		
	}
	
	public double getSkupnaCena()
	{
		return 15.3;
	}
	
	public int getSize(int i)
	{
		return posamezniSeznam.get(i).size();
	}
	
	public Seznam vrsniSeznam(int i)
	{
		return new Seznam( posamezniSeznam.get(0).get(i).uporabnik, posamezniSeznam.get(0).get(i).artikel);
	}
	
	public int oznacen(int i)
	{
		int tmp=0;
		for(int j=0;j<getSize(i)-1;j++)
		{
			 if(posamezniSeznam.get(i).get(j).oznacen)
				 tmp++;
		}
		return tmp;
	}
	
}
