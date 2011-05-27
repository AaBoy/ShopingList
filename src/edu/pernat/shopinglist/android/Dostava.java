package edu.pernat.shopinglist.android;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Dostava extends Dialog implements OnClickListener {
	private EditText emailTo;
	private EditText emailSubject;
	private EditText emailBody;
	private Button btnSend;
	
	private String teloEmaila;
	
	GlobalneVrednosti app;
	public Dostava(Context context,GlobalneVrednosti temp) {
		super(context);
	
		setContentView(R.layout.dostava);
		// TODO Auto-generated constructor stub
		// Get handle to the text edit and button widgets
		emailTo = (EditText) findViewById(R.id.editTxtTo);
		emailSubject = (EditText) findViewById(R.id.editTxtSubject);
		emailBody = (EditText) findViewById(R.id.editTxtBody);
		btnSend = (Button) findViewById(R.id.btnEmailSend);
		btnSend.setOnClickListener(this);
		app=temp;
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
			sendEmail();
		}
	}
	
}


