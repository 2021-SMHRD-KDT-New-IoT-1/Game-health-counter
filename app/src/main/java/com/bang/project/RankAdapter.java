package com.bang.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

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

//        ImageView r_logo = convertView.findViewById(R.id.r_logo);
        TextView r_lv = convertView.findViewById(R.id.co);
        TextView r_nick = convertView.findViewById(R.id.pull_cnt);
        TextView r_exp = convertView.findViewById(R.id.r_exp);
        TextView r_num = convertView.findViewById(R.id.r_num);

//        r_logo.setImageResource(r_data.get(position).getR_logo());

        int rownum = r_data.get(position).getRowNum();
        r_num.setText(rownum+"");

        r_num.setBackground(ContextCompat.getDrawable(context, R.drawable.rank_radius));

        if(rownum == 1) {
            r_num.setBackground(ContextCompat.getDrawable(context, R.drawable.gold));
//            r_num.setText("");
        }
        if(rownum == 2) {
            r_num.setBackground(ContextCompat.getDrawable(context, R.drawable.silver));
//            r_num.setText("");
        }
        if(rownum == 3) {
            r_num.setBackground(ContextCompat.getDrawable(context, R.drawable.bronze));
//            r_num.setText("");
        }

        r_lv.setText("Lv "+r_data.get(position).getC_level()+"");
        r_nick.setText(r_data.get(position).getM_nickname());
        r_exp.setText(r_data.get(position).getTotal_exp()+"");

        return convertView;
    }
}
