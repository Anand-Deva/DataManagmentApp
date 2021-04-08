package com.example.datamanagmentapp;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;
        import java.util.ArrayList;
public class CustomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CustomBodyMeasurementModel> arrayList;
    private TextView timestamp, Gewicht_Kg, Größe_cm;
    public CustomAdapter(Context context, ArrayList<CustomBodyMeasurementModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        timestamp = convertView.findViewById(R.id.timestamp);
        Gewicht_Kg = convertView.findViewById(R.id.Gewicht_Kg);
        Größe_cm = convertView.findViewById(R.id.Größe_cm);
        timestamp.setText("TimeStamp:  " + arrayList.get(position).getTimestamp());
        Gewicht_Kg.setText("Gewicht in Kg:  "+ arrayList.get(position).getGewicht_Kg());
        Größe_cm.setText("Größe in cm:  " + arrayList.get(position).getGröße_cm());
        return convertView;
    }
}