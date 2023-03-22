package com.example.econtract;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class custom_communication extends BaseAdapter {
    private Context context;

    ArrayList<String> a;




    public custom_communication(Context applicationContext, ArrayList<String> a) {
        // TODO Auto-generated constructor stub
        this.context=applicationContext;
        this.a=a;
//        this.b=b;
//            this.c=c;
//            this.d=d;
//            this.e=e;


    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return a.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getItemViewType(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(context);
            gridView=inflator.inflate(R.layout.activity_custom_communication, null);

        }
        else
        {
            gridView=(View)convertview;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView74);
//        TextView tv2=(TextView)gridView.findViewById(R.id.textView54);
//            TextView tv3 = (TextView)gridView.findViewById(R.id.textView55);
//            TextView tv4 = (TextView)gridView.findViewById(R.id.textView59);
//            TextView tv5 = (TextView)gridView.findViewById(R.id.textView60);




        tv1.setText(a.get(position));
//        tv2.setText(b.get(position));



        tv1.setTextColor(Color.BLACK);
//        tv2.setTextColor(Color.BLACK);










        return gridView;

    }

}
