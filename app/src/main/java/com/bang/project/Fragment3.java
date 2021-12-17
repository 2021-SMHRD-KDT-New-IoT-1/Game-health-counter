package com.bang.project;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Fragment3 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_3, container, false);

        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.popup, null);
        ConstraintLayout layout2 = (ConstraintLayout) inflater.inflate(R.layout.popup2, null);
        ConstraintLayout layout3 = (ConstraintLayout) inflater.inflate(R.layout.popup3, null);

//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setView(layout);
//        builder.create().show();

        v.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(layout);
                builder.create().show();

            }
        });

        v.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(layout2);
                builder.create().show();
            }
        });

        v.findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(layout3);
                builder.create().show();
            }
        });


        return v;
    }

}


