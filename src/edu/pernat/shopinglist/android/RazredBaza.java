package edu.pernat.shopinglist.android;




public class RazredBaza {
	
	
		int stSeznama;
		int stArtikla;
		String imeSeznama;
		long id;
		
		public void setID(long l)
		{id=l;}
		
		public void setStSeznma(int i)
		{stSeznama=i;}
		public void setStArtikla(int i)
		{stArtikla=i;}
		public void setImeSeznama(String i)
		{imeSeznama=i;}
		
		public RazredBaza(int i, int p, String s)
		{
			stSeznama=i;
			stArtikla=p;
			imeSeznama=s;
		}
		
		
		
		public RazredBaza() {
			// TODO Auto-generated constructor stub
		}
		public int getStSeznama(){return stSeznama;}
		public int getStArtikla(){return stArtikla;}
		public String getImeSeznama(){return imeSeznama;}


}
