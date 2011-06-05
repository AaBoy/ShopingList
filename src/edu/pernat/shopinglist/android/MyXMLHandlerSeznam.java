package edu.pernat.shopinglist.android;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.pernat.shopinglist.android.razredi.Artikli;

public class MyXMLHandlerSeznam extends DefaultHandler {

	Boolean currentElement = false;
	String currentValue = null;
	GlobalneVrednosti app;
	int ID=1;
	double cena=0;
	String ime="";
	String kolicina="";
	String ime_seznama="";
	
	
	//public static SitesList sitesList = null;

	/*public static SitesList getSitesList() {
		return sitesList;
	}
*/
	public MyXMLHandlerSeznam(GlobalneVrednosti tmp)
	{
		
		app=tmp;
	}
	
	/*public static void setSitesList(SitesList sitesList, GlobalneVrednosti tmp) {
		MyXMLHandler.sitesList = sitesList;
	}*/

	/** Called when tag starts ( ex:- <name>AndroidPeople</name> 
	 * -- <name> )*/
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		/*if (localName.equals("inventory"))
		{
			
			
		} else if (localName.equals("book")) {
			
			String attr = attributes.getValue("year");
			app.seznamArtiklov.add(new ) .setCategory(attr);
		}*/

	}

	/** Called when tag closing ( ex:- <name>AndroidPeople</name> 
	 * -- </name> )*/
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;

		
		
		
		/** set value */ 
		if (localName.equalsIgnoreCase("ime_artikla"))
			ime=currentValue;
		else if (localName.equalsIgnoreCase("cena_artikla"))
			cena=Double.parseDouble(currentValue);
		else if(localName.equalsIgnoreCase("kolicina_artikla"))
			kolicina=currentValue;
		else if(localName.equalsIgnoreCase("Ime_seznama"))
			ime_seznama=currentValue;

		if(cena!=0 && ime!="" && kolicina!="" && ime_seznama!="")
		{
			app.seznamArtiklov.add(new Artikli(ID, cena, ime, kolicina));
			
			ime_seznama="";
			cena=0;
			ime="";
			kolicina="";
		}
	}

	/** Called to get tag characters ( ex:- <name>AndroidPeople</name> 
	 * -- to get AndroidPeople Character ) */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = new String(ch, start, length);
			currentElement = false;
		}

	}

}
