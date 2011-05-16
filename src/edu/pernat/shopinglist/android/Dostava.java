package edu.pernat.shopinglist.android;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Dostava extends Activity {
	
	
	private EditText emailTo;
	private EditText emailSubject;
	private EditText emailBody;
	private Button btnSend;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dostava);

		// Get handle to the text edit and button widgets
		emailTo = (EditText) findViewById(R.id.editTxtTo);
		emailSubject = (EditText) findViewById(R.id.editTxtSubject);
		emailBody = (EditText) findViewById(R.id.editTxtBody);
		btnSend = (Button) findViewById(R.id.btnEmailSend);


	}

	public void sedEmail(View v) {
		if (v.getId() == R.id.btnEmailSend) {
			sendEmail();
		}
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
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject.getText()
				.toString());
		sendIntent.putExtra(Intent.EXTRA_TEXT, emailBody.getText().toString());
		// sendIntent.setType("message/rfc822");
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, "email"));
	}
	
}


