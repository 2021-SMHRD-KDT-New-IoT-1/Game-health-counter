package com.bang.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatAdapter extends BaseAdapter {

    private LayoutInflater inflater;  // 추출한 inflate를 저장할 변수
    private Context context; // inflater를 추출하기 위한 화면 정보
    private int layout; // 템플릿의 id
    private ArrayList<ChatVO> data; // 꾸밀 데이터

    public ChatAdapter(Context context, int layout, ArrayList<ChatVO> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;

        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {return data.size();}

    @Override
    public Object getItem(int position) {return data.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView =inflater.inflate(layout,parent,false);
        }

        ImageView q_check1 = convertView.findViewById(R.id.q_check1);
        ImageView q_check2 = convertView.findViewById(R.id.q_check2);
        ImageView q_check3 = convertView.findViewById(R.id.q_check3);
        TextView q_text1 = convertView.findViewById(R.id.q_text1);
        TextView q_text2 = convertView.findViewById(R.id.q_text2);
        TextView q_text3 = convertView.findViewById(R.id.q_text3);
        TextView q_date = convertView.findViewById(R.id.q_date);
        TextView q_cal1 = convertView.findViewById(R.id.q_cal1);
        TextView q_cal2 = convertView.findViewById(R.id.q_cal2);
        TextView q_cal3 = convertView.findViewById(R.id.q_cal3);

        q_date.setText(data.get(position).getQ_date());
        q_text1.setText(data.get(position).getQ_text1());
        q_check1.setImageResource(data.get(position).getQ_check1());
        q_cal1.setText(data.get(position).getQ_cal1());
        q_text2.setText(data.get(position).getQ_text2());
        q_check2.setImageResource(data.get(position).getQ_check2());
        q_cal2.setText(data.get(position).getQ_cal2());
        q_text3.setText(data.get(position).getQ_text3());
        q_check3.setImageResource(data.get(position).getQ_check3());
        q_cal3.setText(data.get(position).getQ_cal3());


        return convertView;
    }
}
