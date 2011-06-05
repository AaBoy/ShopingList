package edu.pernat.shopinglist.android;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
 
import org.apache.commons.net.ftp.FTPClient;
import org.xml.sax.Parser;
import org.xmlpull.v1.XmlSerializer;

import edu.pernat.shopinglist.android.razredi.Seznam;
import edu.pernat.shopinglist.android.razredi.Seznami;

import android.R.xml;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;

public class MakingXMLFIle extends Activity {
	
	GlobalneVrednosti app;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makin_xml);
	
        
          
                    app=(GlobalneVrednosti)getApplication();
                    
                    writeXml();
	}

	private String writeXml(){
	    XmlSerializer serializer = Xml.newSerializer();
	    StringWriter writer = new StringWriter();
	    try {
	    	serializer.setOutput(writer);
	        serializer.startDocument("UTF-8", true);
	        serializer.startTag("", "zbirka_artiklov");
	        serializer.startTag("", "dogotki");
	        int i=0;
	    	for(;i<app.seznamArtiklov.size();i++)
	    	{
	    		serializer.startTag("", "izdelek");
	    		serializer.attribute("", "id", ""+i);
	    		
	    		serializer.startTag("", "ime");
	    		serializer.text(app.seznamArtiklov.get(i).getIme());
	    		serializer.endTag("", "ime");
	    		
	    		serializer.startTag("", "cena");
	    		serializer.text(""+app.seznamArtiklov.get(i).getCena());
	    		serializer.endTag("", "cena");
	    		
	    		serializer.startTag("", "kolicina");
	    		serializer.text(app.seznamArtiklov.get(i).getKolicina());
	    		serializer.endTag("", "kolicina");
	    		
	    		serializer.endTag("", "izdelek");
	    		
	    	}
	    	serializer.endTag("", "dogotki");
	    	serializer.endTag("", "zbirka_artiklov");
	        
	    	
	    	 serializer.endDocument();
	    	
	       /* serializer.setOutput(writer);
	        serializer.startDocument("UTF-8", true);
	        serializer.startTag("", "seznami");
	        serializer.attribute("", "number", String.valueOf(app.vsiSeznami.size()));
	        for (int i=0;i <app.vsiSeznami.size();i++){
	        	serializer.startTag("", "vsiSeznami");
	        	for(int j=0;j<app.vsiSeznami.get(i).getSize(0);j++)
	        	{
	        		serializer.startTag("", "izdelek");
	        		Seznam xmlNovSeznam=new Seznam(app.uporabnisko, app.vsiSeznami.get(i).vrsniSeznam(j).getArtikel());
		        	xmlNovSeznam.imeSeznama="Nekaj";
		        	
		            
		            serializer.startTag("", "Ime_seznama");
		            serializer.text(xmlNovSeznam.imeSeznama.toString());
		            serializer.endTag("", "Ime_seznama");
		            serializer.startTag("", "ime_artikla");
		            serializer.text(xmlNovSeznam.getArtikelIme().toString());
		            serializer.endTag("", "ime_artikla");
		            serializer.startTag("", "cena_artikla");
		            serializer.text(""+xmlNovSeznam.getArtikelCena());
		            serializer.endTag("", "cena_artikla");
		            serializer.startTag("", "kolicina_artikla");
		            serializer.text(""+xmlNovSeznam.getArtikliKolicina().toString());
		            serializer.endTag("", "kolicina_artikla");
		            serializer.endTag("", "izdelek");
	        	}
	        	serializer.endTag("", "vsiSeznami");
	        }
	        serializer.endTag("", "seznami");
	        serializer.endDocument();
	        
	        
	        
	        
	       
            
            
           /* URL url = new URL("ftp://user01:pass1234@ftp.foo.com/README.txt;type=i");
            URLConnection urlc = url.openConnection();
            InputStream is = urlc.getInputStream(); // To download
            OutputStream os = urlc.getOutputStream(); // To upload*/

	    	
	    	 TextView tv = (TextView)findViewById(R.id.result);
	            tv.setText(writer.toString());
	            
            FTPClient con = new FTPClient();
            try
            {
                con.connect("ftp.shoppinglistandroid.netii.net");
                if (con.login("a6432387", "federer90"))
                {
                    con.enterLocalPassiveMode(); // important!
                    String data = "test data";
                    ByteArrayInputStream in = new ByteArrayInputStream(writer.toString().getBytes());
                    boolean result = con.storeFile("public_html/izdelki.xml", in);
                    in.close();
                    if (result) Log.v("upload result", "succeeded");
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


            try
            {
                con.logout();
                con.disconnect();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            
            
            
	        //.e("XML\n",writer.toString());
	        return writer.toString();
	        
	        
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    } 
	}

}
