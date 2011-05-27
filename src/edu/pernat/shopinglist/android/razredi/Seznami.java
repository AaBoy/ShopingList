package edu.pernat.shopinglist.android.razredi;

import java.util.ArrayList;

public class Seznami {

	ArrayList<	ArrayList<Seznam>> posamezniSeznam=new ArrayList<ArrayList<Seznam>>();
	
	public Seznami()
	{
		
		
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
	
	public int getSkupnaCena()
	{
		return 190;
	}
	
	public int getSize(int i)
	{
		return posamezniSeznam.get(i).size();
	}
	
	public Seznam vrsniSeznam(int i)
	{
		return new Seznam( posamezniSeznam.get(0).get(i).uporabnik, posamezniSeznam.get(0).get(i).artikel);
	}
	
}
