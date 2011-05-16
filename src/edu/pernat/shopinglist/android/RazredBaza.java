package edu.pernat.shopinglist.android;

import android.R.string;



public class RazredBaza {
	
	
		String uporabnisko;
		String geslo;
		long id;
		
		public void setID(long l)
		{id=l;}
		
		public void setUpo(String i)
		{uporabnisko=i;}
		public void setGeslo(String i)
		{geslo=i;}
		
		public RazredBaza(String i, String p)
		{
			uporabnisko=i;
			geslo=p;
		}
		
		
		
		public RazredBaza() {
			// TODO Auto-generated constructor stub
		}
		public String getUpo(){return uporabnisko;}
		public String getGeslo(){return geslo;}


}
