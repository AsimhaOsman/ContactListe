package com.example.adaptersample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityInputContact extends ActionBarActivity implements OnClickListener {

	EditText etVN, etNN, etTN;
	Button btSave, btBack;
	private AppGlobal myApp;
	CHDatabaseHelper chdb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_input_contact);
		myApp = (AppGlobal) this.getApplication();

		chdb = new CHDatabaseHelper(this); 
		
		etVN = (EditText) findViewById(R.id.etVN);
		etNN = (EditText) findViewById(R.id.etNN);
		etTN = (EditText) findViewById(R.id.etTN);

		btSave = (Button) findViewById(R.id.btSave);
		btBack = (Button) findViewById(R.id.btBack);

		btSave.setOnClickListener(this);
		btBack.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_input_contact, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btSave:
			if (myApp.iSelectedPosition == -1) {
				//neu anlegen
				CDContacts contact = new CDContacts(etVN.getText().toString(),
						etNN.getText().toString(),
						etTN.getText().toString(),		
						R.drawable.rot);
				myApp.lsContactList.add(contact);
				chdb.insertKontakt(contact);
			} else {
				//oder editieren
				CDContacts cdc = myApp.lsContactList.get(myApp.iSelectedPosition);
				cdc.setsVorname(etVN.getText().toString());
				cdc.setsNachname(etNN.getText().toString());
				cdc.setsTelefon(etTN.getText().toString());
				chdb.updateKontakt(cdc);
			}
			myApp.bNotifyIt = true;
		case R.id.btBack:
			this.finish();
			break;
		default:
			Toast.makeText(this, "Was soll ich tun?", Toast.LENGTH_SHORT).show();
				
		}
		
	}
}
