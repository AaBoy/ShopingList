package edu.pernat.shopinglist.android.razredi;




public class RazredBaza {
	
	
		
		int stSeznama;
		int stArtikla;
		String imeSeznama;
		long id;
		int oznacen;
		int kolNakupov;
		
		public void setID(long l)
		{id=l;}
		public void setKolicina(int i)
		{kolNakupov=i;}
		public void setStSeznma(int i)
		{stSeznama=i;}
		public void setStArtikla(int i)
		{stArtikla=i;}
		public void setImeSeznama(String i)
		{imeSeznama=i;}
		public void setoznacen(int o)
		{oznacen=o;}
		
		public RazredBaza(int i, int p, String s, int o, int k)
		{
			stSeznama=i;
			stArtikla=p;
			imeSeznama=s;
			oznacen=o;
			kolNakupov=k;
		}
		
		
		
		public RazredBaza() {
			// TODO Auto-generated constructor stub
		}
		public int getStSeznama(){return stSeznama;}
		public int getStArtikla(){return stArtikla;}
		public String getImeSeznama(){return imeSeznama;}
		public int getOznacen(){return oznacen;}
		public int getKolicina(){return kolNakupov;}


}
