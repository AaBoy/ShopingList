/*******************************************************************************
 * Copyright (c) 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package edu.pernat.shopinglist.android;




import edu.pernat.shopinglist.android.razredi.EmailNaslovi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dostava extends Dialog implements OnClickListener {
	private AutoCompleteTextView emailTo;
	private EditText emailSubject;
	private EditText emailBody;
	private Button btnSend;
	
	private String teloEmaila;
//    static final String[] COUNTRIES = new String[] {"aaboyxx@gmail.com","matej.crepinsek@gmail.com","dejan.hrncic@uni-mb.si"
//        };
	GlobalneVrednosti app;
	Context javni;
	String[] emaili;
	public Dostava(Context context,GlobalneVrednosti temp) {
		super(context);
	
		setContentView(R.layout.dostava);
		// TODO Auto-generated constructor stub
		// Get handle to the text edit and button widgets
		emailTo = (AutoCompleteTextView) findViewById(R.id.editTxtTo);
		emailSubject = (EditText) findViewById(R.id.editTxtSubject);
		emailBody = (EditText) findViewById(R.id.editTxtBody);
		btnSend = (Button) findViewById(R.id.btnEmailSend);
		btnSend.setOnClickListener(this);
		app=temp;
		javni=context;
		
		if(app.lista.size()>0)
		{
			emaili=new String[app.lista.size()];
			
			for(int i=0;i<app.lista.size();i++)
			{
				emaili[i]=app.lista.get(i).getEmail();
			}
			
		}
		if(emaili==null)
		{
			emaili=new String[1];
			emaili[0]="Naslovi";
		}
		
		ArrayAdapter adapter = new ArrayAdapter(context,android.R.layout.simple_dropdown_item_1line, emaili);
       
        emailTo.setAdapter(adapter);
		
		gnerirajTelo();
		emailBody.setText(teloEmaila);
	}


	
	/**
	 * Method to send email
	 */
	protected void sendEmail() {
		// Setup the recipient in a String array
		String[] mailto = { emailTo.getText().toString() };
		//String[] ccto = { "somecc@somedomain.com" };
		// Create a new Intent to send messages
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		// Add attributes to the intent
		sendIntent.putExtra(Intent.EXTRA_EMAIL, mailto);
		//sendIntent.putExtra(Intent.EXTRA_CC, ccto);
		
		
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject.getText().toString());
		
		sendIntent.putExtra(Intent.EXTRA_TEXT, teloEmaila);
		// sendIntent.setType("message/rfc822");
		sendIntent.setType("text/plain");
		getContext().startActivity(Intent.createChooser(sendIntent, "email"));
	}

	public void gnerirajTelo()
	{
		teloEmaila="";
		for(int i=0;i<app.novSeznam.size();i++)
		{
			teloEmaila+=app.novSeznam.get(i).getArtikelIme()+"   "+app.novSeznam.get(i).getArtikelCena()+"€   \n";
			
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

		if(v.getId()==R.id.btnEmailSend)
		{
			if(app.lista.size()>0)
			{
				
				int i=0;
				for(;i<app.lista.size();i++)
				{
					if(emailTo.getText().toString()==app.lista.get(i).getEmail())
					break;
				}
				
				if(i==app.lista.size())
					app.dodajNaslov(new EmailNaslovi(emailTo.getText().toString()));
			}
			
			
			sendEmail();
		}
	}
	
	
	public void onStop()
	{
		super.onStop();
		
		emailTo.setText("");
		emailSubject.setText("");
		emailBody.setText("");
		Toast.makeText(javni, "Sem pobriso",Toast.LENGTH_SHORT);
		
	}
}


