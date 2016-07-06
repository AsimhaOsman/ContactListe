package com.example.adaptersample;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CHDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATENBANKNAME = "dbKontaktliste";
	private static final int DATENBANKVERSION = 2; //laufende nummer (version der eigenen db-struktur)

	//Datenbankstruktur definieren
	//"Tabellennamen"
	private static final String TBL_CONTACTS = "tblkontakte"; //Tabellenname
	//"Spaltennamen"
	private static final String KEY_ID = "id"; //primaryKey, autoicrement
	private static final String KEY_VORNAME = "vorname";
	private static final String KEY_NACHNAME = "nachname";
	private static final String KEY_TEL = "telefon";
	private static final String KEY_ICON_RES = "iconid";
	private static final String KEY_ICON_RES2 = "iconid2";
	
	
	public CHDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}	
	public CHDatabaseHelper(Context context) {
		super(context, DATENBANKNAME, null, DATENBANKVERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Anweisungen, die das Anlegen der Tabellen betreffen
		String sCreateKontakte = "CREATE TABLE " + TBL_CONTACTS + " ( "
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ KEY_VORNAME + " TEXT, "
				+ KEY_NACHNAME + " TEXT, "
				+ KEY_TEL + " TEXT, "
				+ KEY_ICON_RES + " INTEGER, "
				+ KEY_ICON_RES2 + " INTEGER "
				+" ) ";
		
		Log.i("SQLc", sCreateKontakte);
		db.execSQL(sCreateKontakte);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Anweisungen, die eventuelle Änderungen in der Struktur betreffen
		if (oldVersion == 1 && newVersion == 2) {
			//scripte zum migrieren auf die neue version
			//z.b. alten bestand retten, tabellen löschen & neu aufbauen, datenbestand zurück schreiben
			//oder alter-table statements ausführen
		}
	}

	
	public long insertKontakt(CDContacts contact) {
		long lRes = -1;
		SQLiteDatabase db = null;
		
		//DB zum schreiben öffnen
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues(); //die benötigte struktur
		//daten hinein legen
		values.put(KEY_VORNAME, contact.getsVorname());
		values.put(KEY_NACHNAME, contact.getsNachname());
		values.put(KEY_TEL, contact.getsTelefon());
		values.put(KEY_ICON_RES, contact.getiImgRes());
		
		//daten in die db-tabelle eintragen
		lRes = db.insert(TBL_CONTACTS, null, values); //im idealfall result = id des datensatzes
		//db connection schliessen
		db.close();
		
		contact.lDBID = lRes; //Dem in die DB geschriebenen Obj. die Eintrags-ID geben
		
		return lRes;
	}
	
	public long updateKontakt(CDContacts contact) {
		long lRes = -1;
		SQLiteDatabase db = null;
		
		//DB zum schreiben öffnen
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues(); //die benötigte struktur
		//daten hinein legen
		values.put(KEY_VORNAME, contact.getsVorname());
		values.put(KEY_NACHNAME, contact.getsNachname());
		values.put(KEY_TEL, contact.getsTelefon());
		values.put(KEY_ICON_RES, contact.getiImgRes());
		
		long lID = contact.lDBID; //vom Obj. die Eintrags-ID erfragen
		
		lRes = db.update(TBL_CONTACTS, //in welcher Tabelle
				values, //folgende wertepaare
				KEY_ID + " = ? ", //where key_id = <platzhalter> 
				new String [] {String.valueOf(lID)}); //<platzhalter> = lID
		
		db.close();
		
		return lRes;
	}
	
	public int bdeleteKontakt(long lID) {
		int bres = -1;
		
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			bres = db.delete(TBL_CONTACTS, KEY_ID + " = ? ", new String[] {String.valueOf(lID)});
			db.close();
		} catch (Exception e) 
		{
			
		}
		
		return bres;
	}
	
	public List<CDContacts> getKontaktliste(List<CDContacts> ctlist) throws Exception { //liste mit allen kontakten erstellen
		if (ctlist == null) {
			ctlist = new ArrayList<CDContacts>(); //neu initialisieren
		} else {
			ctlist.clear(); //bereinigen
		}
		
		String sQuery = "SELECT * FROM " + TBL_CONTACTS;
		SQLiteDatabase db = null;
		
		db = this.getReadableDatabase(); //zum lesen reicht
		Cursor cursor = db.rawQuery(sQuery, null);
		
		if (cursor.moveToFirst()) { //wenn zumindest ein erster Eintrag vorhanden ist
			do {
				CDContacts tcontact = new CDContacts(cursor.getString(1)
						,cursor.getString(2)
						,cursor.getString(3)
						,cursor.getInt(4));
				tcontact.lDBID = cursor.getLong(0); //key bzw. EintragsID 
				
				ctlist.add(tcontact);
			} while (cursor.moveToNext());
		}
		db.close();
		
		return ctlist;
	}
	
}
