package com.example.adaptersample;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements
		OnItemClickListener, OnClickListener {
	ListView lvSample;
	MeinAdapter adapter;
	Button btNeuAnlegen;
	
	AppGlobal myApp;

	CHDatabaseHelper chdb = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myApp = (AppGlobal) this.getApplication();
		
		chdb = new CHDatabaseHelper(this);
		
		fillList();

		lvSample = (ListView) findViewById(R.id.lvBeispiel);

		adapter = new MeinAdapter(this // der context
				, R.layout.datenlayout // layout für die stringelemente
				, myApp.lsContactList); // die Datenliste

		lvSample.setAdapter(adapter);

		lvSample.setOnItemClickListener(this); // Behandeln, wenn auf einen
												// Listeneintrag geklickt

		btNeuAnlegen = (Button) findViewById(R.id.btNeuEintrag);
		btNeuAnlegen.setOnClickListener(this);
		myApp.bNotifyIt = false;
	}
	
	@Override 
	public void onResume() {
		super.onResume();
		if (myApp.bNotifyIt) { //wenn änderung passiert ist
			adapter.notifyDataSetChanged();
			myApp.bNotifyIt = false;
		}
	}

	private void fillList() {
		try {
			myApp.lsContactList = chdb.getKontaktliste(myApp.lsContactList);
		} catch (Exception e) {
			//hier behandeln, dass irgendwas nicht ging
			//..z.b. beispieldaten
/*
			// Vorname, Nachname, Telefonnummer, ImageRessource
			myApp.lsContactList.add(new CDContacts("Nina", "Mueller", "++49175938567",
					R.drawable.rot));
			myApp.lsContactList.add(new CDContacts("Martin", "Steege", "05751/3875272",
					R.drawable.blau));
			myApp.lsContactList.add(new CDContacts("Lars", "Marx", "74867",
					R.drawable.blau));
			myApp.lsContactList
					.add(new CDContacts("Franz", "Staiger", "++4385893626", -1));
			myApp.lsContactList.add(new CDContacts("Sabine", "Baum", "0421 8467820", -1));
			myApp.lsContactList.add(new CDContacts("Tanja", "Klein", "978943",
					R.drawable.rot));
			myApp.lsContactList.add(new CDContacts("Anke", "Gross", "++49937548274", -1));
			
			for (CDContacts cdc : myApp.lsContactList) {
				chdb.insertKontakt(cdc); //dummydaten in die db schreiben
			} //*/
		}
		/*
		*/
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// wird aufgerufen, wenn ein listeneintrag angeklickt wird
		if (parent == lvSample) { // wenn sich das item in der listview lvSample
									// befindet
			myApp.iSelectedPosition = position; // den Wert der position in das Feld
											// myApp.iSelectedPosition schreiben
			showContactAlertDialog();
		}
	}
	
	//erstellt einen Dialog mit durch Entwickler konfigurierbaren Attributen	
	protected void buildCustomDialog() { 
		Display display = getWindowManager().getDefaultDisplay(); //den "screen" holen (das, worauf die app dargestellt wird) 
		int wdt = (int)(display.getWidth() * 0.8);
		int heg = (int)(display.getHeight() * 0.9);
		
		final Dialog dialog = new Dialog(this);
		
		dialog.setContentView(R.layout.lomydialog); //das layout für den Dialog festlegen
		dialog.setTitle("Erster eigener Dialog");
		
		dialog.getWindow().setLayout(wdt, heg); //Grösse einstellen (via Window-Komponente)
		
		final EditText etKommentar = (EditText) dialog.findViewById(R.id.etComment);
		
		CheckBox cbA = (CheckBox) dialog.findViewById(R.id.cbAlpha);
		CheckBox cbB = (CheckBox) dialog.findViewById(R.id.cbBeta);
		CheckBox cbC = (CheckBox) dialog.findViewById(R.id.cbGamma);
		//cbA.isChecked()
		
		Button btOkdlg  = (Button) dialog.findViewById(R.id.btOKdlg);
		Button btBackdlg= (Button) dialog.findViewById(R.id.btBackdlg);
		
		btBackdlg.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss(); //schliesst den dialog wieder
			}
		});
		btOkdlg.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				String sComment = etKommentar.getText().toString();
				if (sComment.length() > 3) {
					Toast.makeText(MainActivity.this
							, "OK geklickt, kommentar ist " + sComment
							, Toast.LENGTH_LONG).show();					
				} else {
					Toast.makeText(MainActivity.this, "OK geklickt", Toast.LENGTH_LONG).show();
				}
				dialog.dismiss(); //schliesst den dialog wieder
			}
		});
		
		
		dialog.show(); //den dialog darstellen
	}

	protected void showContactAlertDialog() { // erstellt einen Dialog
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setTitle("Person ausgewählt");
		alertDialogBuilder
				.setMessage("Was soll mit diesem Datensatz geschehen?");
		alertDialogBuilder.setPositiveButton("Bearbeiten",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						doBearbeite();
					}
				});
		alertDialogBuilder.setNeutralButton("Löschen",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						doDelete();
					}
				});
		alertDialogBuilder.setNegativeButton("Abbrechen",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// doNothing ;)
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	protected void doBearbeite() {

	}

	protected void doDelete() {
		if (myApp.iSelectedPosition > 0) {
			chdb.bdeleteKontakt(
					myApp.lsContactList.get(myApp.iSelectedPosition).lDBID
					);
			myApp.lsContactList.remove(myApp.iSelectedPosition); // Datensatz entfernen
			adapter.notifyDataSetChanged(); // Adapter informieren -> Dieser
											// gibt Info an LV weiter
			myApp.iSelectedPosition = -1;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_beenden) {
			Toast.makeText(this, "Beenden geklickt", Toast.LENGTH_SHORT).show();
			myApp.finishAll();
		}
		if (id == R.id.action_funktion1) {
			Toast.makeText(this, "Funktion 1 gewählt", Toast.LENGTH_SHORT).show();
			buildCustomDialog();
		}
		if (id == R.id.action_funktion2) {
			Toast.makeText(this, "Funktion 2 gewählt", Toast.LENGTH_SHORT).show();
			smstest();
		}
		if (id == R.id.action_funktion3) {
			Toast.makeText(this, "Funktion 3 gewählt", Toast.LENGTH_LONG).show();
		}
		if (id == R.id.action_settings) {
			Toast.makeText(this, "Settings gewählt", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		myApp.iSelectedPosition = -1;
		Intent intent = new Intent(MainActivity.this, ActivityInputContact.class);
		startActivity(intent);
	}
	
	protected void smstest() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("sms:"+"456789"));
		intent.putExtra("sms_body"
				, "Liebe Person XYZ, ich mag Dich informieren, dass Du eine SMS erhalten hast.");
		
		startActivity(intent);		
	}
	
	protected void doSMSbyUser(String sNumber) { //ruft die SMS-App des Gerätes auf
		//benötigt keine Berechtigungen in der Manifest
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+sNumber));
		intent.putExtra("sms_body"
				, "Liebe Person XYZ, ich mag Dich informieren, dass Du eine SMS erhalten hast.");
		
		startActivity(intent);
	}
	
	protected void doSMSbySystem(String sNummer) {//versendet eine SMS ohne weiteres zutun des users
		//benötigt in der Manifest permission.SEND_SMS
		SmsManager smsmanager = SmsManager.getDefault(); //der SMS-Manger des Telefons
		smsmanager.sendTextMessage(sNummer,null			 //sendet die SMS dann
				, "Hallo, ich bin eine tolle Nachricht, bitte schickt mehr Waschmschinen", 
				null, null);		
	}

	protected void doCall(String sNummer) { //initiiert einen Anruf
		//braucht in der Manifest die permission CALL_PHONE
		Intent intent = new Intent(Intent.ACTION_CALL); //intent mit dem Ziel TelefonieActivity erzeugen
		intent.setData(Uri.parse("tel:"+sNummer));		//telefonnummer dem intent beifügen
		startActivity(intent); 							//ruft die Telefonie-Activity auf
	}
}
