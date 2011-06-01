package edu.pernat.shopinglist.android.razredi;

public class EmailNaslovi {

	String emailNaslovi;
	
	public EmailNaslovi()
	{
		
	}
	long id;
	
	public void setID(long l)
	{id=l;}
	public EmailNaslovi(String e)
	{
		emailNaslovi=e;
	}
	
	public void setEmail(String e)
	{
		emailNaslovi=e;
	}
	
	public String getEmail()
	{
		return emailNaslovi;
	}
}
