package edu.pernat.shopinglist.android.razredi;

import android.widget.RatingBar;

public class Seznam {

		String uporabnik;
		Artikli artikel;
		public String imeSeznama;
		
		public Seznam(String u, Artikli a)
		{
			uporabnik=u;
			artikel=a;
			
		}
		
		public double getArtikelCena()
		{
			return artikel.getCena();
		}
		
		public String getArtikelIme()
		{
			return artikel.getIme();
		}
		
		public void setArtikelIme(String i)
		{
			artikel.ime=i;
		}
		
		public void setArtikelCena(Double c)
		{
			artikel.cena=c;
			
		}
		public String getArtikliKolicina()
		{
			return artikel.getKolicina();
		}
}
