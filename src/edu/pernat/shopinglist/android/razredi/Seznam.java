package edu.pernat.shopinglist.android.razredi;

import android.widget.RatingBar;

public class Seznam {

		String uporabnik;
		Artikli artikel;
		public String imeSeznama;
		public boolean oznacen;
		
		public Seznam(String u, Artikli a)
		{
			uporabnik=u;
			artikel=a;
			oznacen=false;
		}
		
		public void setUporabnik(String s)
		{
			uporabnik=s;
			
			
		}
		public String getUporabnik()
		{
			return uporabnik;
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
		public Artikli getArtikel()
		{
			return new Artikli(artikel.id_trgovina, artikel.cena, artikel.ime, artikel.kolicina);
		}
		public void setSelected(Boolean selected) {
			
			if(selected=true)
				
			
			this.oznacen = selected;
		}
}
