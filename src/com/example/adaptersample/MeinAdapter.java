package com.example.adaptersample;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MeinAdapter extends ArrayAdapter {
	Activity ctx;
	int iLayoutId;
	List<CDContacts> lsData;

	public MeinAdapter(Activity context, int resource, List<CDContacts> objects) {
		super(context, resource, objects);
		this.ctx = context;
		this.iLayoutId = resource;
		this.lsData = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView; //ein eventuell bestehendes View zum recycling übernehmen
		
		if (rowView == null) { //wenn noch keines existiert
			LayoutInflater inflater = ctx.getLayoutInflater(); //Inflater anfordern
			rowView = inflater.inflate(iLayoutId, null);	   //Layout durch Inflater an
			//rowView = inflater.inflate(R.layout.datenlayout, null);	   //Layout durch Inflater an
			
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.ivImage  = (ImageView) rowView.findViewById(R.id.ivDatenBild);
			viewHolder.tvDaten1 = (TextView) rowView.findViewById(R.id.tvD1);
			viewHolder.tvDaten2 = (TextView) rowView.findViewById(R.id.tvD2);
			
			//den Viewholder mit der View assoziieren
			rowView.setTag(viewHolder);
		}
		//nun existiert eine gültige view samt assoziiertem viewHolder 
		
		//den Viewholder zu der View erfragen
		ViewHolder vholder = (ViewHolder) rowView.getTag();
		
		//Daten zum gewünschten Datensatz aus der Liste lesen
		CDContacts cdc = lsData.get(position); //Datensatz mit ner nummer "position" aus der Liste lesen 
		
		//Inhalte in die Felder schreiben
		vholder.tvDaten1.setText(cdc.getsGesamtname());
		vholder.tvDaten2.setText(cdc.getsTelefon());
		if(cdc.getiImgRes() > 0) { //da wir mit -1 initialisieren hier nur, wenn ein Img vergeben wurde
			vholder.ivImage.setImageResource(cdc.getiImgRes());
			//vholder.ivImage.setI
		}
		
		return rowView;
	}
	
	//Der ViewHolder
	static class ViewHolder {
		public ImageView ivImage;
		public TextView tvDaten1;
		public TextView tvDaten2;
	}
	
	
	/*//ohne view holder
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView; //ein eventuell bestehendes View zum recycling übernehmen
		
		if (rowView == null) { //wenn noch keines existiert
			LayoutInflater inflater = ctx.getLayoutInflater(); //Inflater anfordern
			rowView = inflater.inflate(iLayoutId, null);	   //Layout durch Inflater an
		}
		//hier existiert nun definitiv ein gültiges Layout ((für jeden durchlauf))
		TextView tvD1 = (TextView) rowView.findViewById(R.id.tvD1); //Das zu behandelnde Datenelement
		TextView tvD2 = (TextView) rowView.findViewById(R.id.tvD2); //im aktuellen rowView finden
		
		String sDatensatz = lsData.get(position).toString(); //Den Datensatz aus der Liste lesen
		
		//Inhalte in die Felder schreiben
		tvD1.setText(position + ")");
		tvD2.setText(sDatensatz);
		
		return rowView;
	}
	*/
	

}
