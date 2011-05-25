package edu.pernat.shopinglist.android.razredi;

import java.util.ArrayList;

public class Seznami {

	ArrayList<	ArrayList<Seznam>> posamezniSeznam;
	
	public Seznami()
	{
		posamezniSeznam=new ArrayList<ArrayList<Seznam>>();
		
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
	
}
