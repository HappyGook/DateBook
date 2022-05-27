package com.example.organiserempty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MeinAdapter extends ArrayAdapter<String> {

    private List<ObjectItem> data;
    private Context context;

    public MeinAdapter(Context context, List<ObjectItem> data) {
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
        // задаем вид элемента списка, который мы создали высше
        View view = inflater.inflate(R.layout.item, parent, false);


        TextView header = (TextView)view.findViewById(R.id.Iheader);
        TextView time = (TextView)view.findViewById(R.id.Itime);
        TextView desc = (TextView)view.findViewById(R.id.Idesc);
        TextView date = (TextView)view.findViewById(R.id.Idate);


        ObjectItem objectItem = data.get(position);

        header.setText(objectItem.getHeader().toString());
        time.setText(objectItem.getTime().toString());
        desc.setText(objectItem.getDesc().toString());
        date.setText(objectItem.getDate().toString());

        return view;
    }
}