package com.bang.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RankAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<RankVO> r_data;

    public RankAdapter(Context context, int layout, ArrayList<RankVO> r_data) {
        this.context = context;
        this.layout = layout;
        this.r_data = r_data;

        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return r_data.size();
    }

    @Override
    public Object getItem(int position) {
        return r_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(layout,parent, false);
        }

        ImageView r_logo = convertView.findViewById(R.id.r_logo);
        TextView r_lv = convertView.findViewById(R.id.co);
        TextView r_nick = convertView.findViewById(R.id.r_nick);
        TextView r_exp = convertView.findViewById(R.id.r_exp);

        r_logo.setImageResource(r_data.get(position).getR_logo());
        r_lv.setText(r_data.get(position).getR_lv());
        r_nick.setText(r_data.get(position).getR_nick());
        r_exp.setText(r_data.get(position).getR_exp());

        return convertView;
    }
}
