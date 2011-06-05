package edu.pernat.shopinglist.android;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import edu.pernat.shopinglist.android.razredi.*;

public class MyXMLHandler extends DefaultHandler {

	Boolean currentElement = false;
	String currentValue = null;
	GlobalneVrednosti app;
	int ID=1;
	double cena=0;
	String ime="";
	String kolicina="";
	int id=0;
	
	//public static SitesList sitesList = null;

	/*public static SitesList getSitesList() {
		return sitesList;
	}
*/
	public MyXMLHandler(GlobalneVrednosti tmp)
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
		if (localName.equalsIgnoreCase("ime"))
			ime=currentValue;
		else if (localName.equalsIgnoreCase("cena"))
			cena=Double.parseDouble(currentValue);
		else if(localName.equalsIgnoreCase("kolicina"))
			kolicina=currentValue;

		if(cena!=0 && ime!="" && kolicina!="")
		{
			app.seznamArtiklov.add(new Artikli(ID, cena, ime, kolicina));
			
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
