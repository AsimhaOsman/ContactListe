package com.example.adaptersample;
//alt + shift + s bringt das SourceMenü
public class CDContacts {
	private int iImgRes = R.drawable.ic_launcher; //Das Bild/Symbol für den Datensatz
	private String sVorname;
	private String sNachname;
	private String sTelefon;
	
	protected long lDBID = -1;
	/**
	 * @param sVorname
	 * @param sNachname
	 * @param sTelefon
	 * @param iImgRes
	 */
	public CDContacts(String sVorname, String sNachname, String sTelefon,
			int iImgRes) {
		super();
		this.sVorname = sVorname;
		this.sNachname = sNachname;
		this.sTelefon = sTelefon;
		if (iImgRes > 0) this.iImgRes = iImgRes;
	}

	public int getiImgRes() {
		return iImgRes;
	}
	
	public String getsGesamtname() {
		return this.getsVorname() + " " + this.getsNachname();
	}

	public String getsVorname() {
		return sVorname;
	}

	public String getsNachname() {
		return sNachname;
	}

	public String getsTelefon() {
		return sTelefon;
	}

	public void setiImgRes(int iImgRes) {
		this.iImgRes = iImgRes;
	}

	public void setsVorname(String sVorname) {
		this.sVorname = sVorname;
	}

	public void setsNachname(String sNachname) {
		this.sNachname = sNachname;
	}

	public void setsTelefon(String sTelefon) {
		this.sTelefon = sTelefon;
	}

	@Override
	public String toString() {
		return "CDContacts [sVorname=" + sVorname + ", sNachname=" + sNachname
				+ ", sTelefon=" + sTelefon + "]";
	}
	
	
}
