package com.example.organiserempty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RMeinAdapter extends ArrayAdapter<String> {

    private List<RObjectItem> data;
    private Context context;

    public RMeinAdapter(Context context, List<RObjectItem> data) {
        super(context, R.layout.item);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public String getItem(int position) {

        return data.get(position).getHeader();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.remitem, parent, false);

        TextView header = (TextView)view.findViewById(R.id.Rheader);
        TextView comp = (TextView) view.findViewById(R.id.Rcomp);

        RObjectItem robjectItem = data.get(position);

        header.setText(robjectItem.getHeader());
        comp.setText(robjectItem.getComp());

        return view;
    }
}