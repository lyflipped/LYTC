package com.example.liyang.mynewfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.avos.avoscloud.AVFile;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Map;

import entity.Evaluation;

/**
 * Created by liyang on 2017/5/2.
 */

public class Eva_Adapter extends BaseAdapter{
    private Context context;
    private List<Evaluation> evaluations;
    public Eva_Adapter(Context context,List<Evaluation> evaluations)
    {
        this.evaluations=evaluations;
        this.context=context;
    }

    @Override
    public int getCount() {
       return evaluations.size();
    }

    @Override
    public Object getItem(int position){
        return evaluations.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //获得一个布局填充器，将xml布局文件转换为View对象
        View view= LayoutInflater.from(context).inflate(R.layout.evaluate_item,null);
        TextView eva_userid=(TextView) view.findViewById(R.id.eva_user_id);
        TextView eva_time=(TextView) view.findViewById(R.id.eva_time);
        TextView eva_content=(TextView) view.findViewById(R.id.eva_content);
        RatingBar ratingBar=(RatingBar) view.findViewById(R.id.ratingBar);
        eva_userid.setText("  "+evaluations.get(position).getUserid());
        eva_time.setText(evaluations.get(position).getTime()+"  ");
        eva_content.setText(evaluations.get(position).getEva_content());
        ratingBar.setRating(Float.valueOf(evaluations.get(position).getStars()));

        return view;
    }
}
