package com.example.liyang.mynewfragment;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datashare.DataShare;
import entity.Goods;

/**
 * Created by liyang on 2017/4/5.
 */

public class ShoppingFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView Goodslist;
    private SimpleAdapter GoodsDataAdapter;
    private View Shoppingview;
    private static  int deletId;
    public String[] datas ={"删除","收藏","+1","-1"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


            Shoppingview = inflater.inflate(R.layout.shoppingfragment, container, false);
        Goodslist=(ListView) Shoppingview.findViewById(R.id.shoppingcartlist);
        Goodslist.setOnItemClickListener(this);
            displayGoods(Shoppingview);
            return Shoppingview;

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //Toast.makeText(getContext(),"点击的第"+position+"行",Toast.LENGTH_SHORT).show();
        System.out.println("第"+position+"行");
        deletId=position;
        View popupView=this.getLayoutInflater(getArguments()).inflate(R.layout.popupwindow,null);
        // TODO: 2016/5/17 为了演示效果，简单的设置了一些数据，实际中大家自己设置数据即可，相信大家都会。
        ListView lsvMore = (ListView) popupView.findViewById(R.id.popwindow);
        lsvMore.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datas));

        // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
        final PopupWindow window = new PopupWindow(popupView, 400, 600);
        // TODO: 2016/5/17 设置动画
        window.setAnimationStyle(R.style.popup_window_anim);
        // TODO: 2016/5/17 设置背景颜色
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        // TODO: 2016/5/17 设置可以获取焦点
        window.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        window.update();
        // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
       // window.showAsDropDown(lsvMore,0 , 20);
        window.showAtLocation(view, Gravity.CENTER,0, 0);
        lsvMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position)
                {
                    case 0:
                        DataShare.shoplist.remove(deletId);
                        displayGoods(Shoppingview);
                        window.dismiss();
                        break;
                    case 1:
                        DataShare.wantlist.add(DataShare.shoplist.get(deletId));
                        break;
                    case 2:
                        DataShare.shoplist.get(deletId).setBuycount(DataShare.shoplist.get(deletId).getBuycount()+1);
                        displayGoods(Shoppingview);
                        break;
                    case 3:
                        if(DataShare.shoplist.get(deletId).getBuycount()>1)
                        {
                            DataShare.shoplist.get(deletId).setBuycount(DataShare.shoplist.get(deletId).getBuycount()-1);
                            displayGoods(Shoppingview);
                        }
                        else
                        {
                            DataShare.shoplist.remove(deletId);
                            displayGoods(Shoppingview);
                            window.dismiss();
                        }

                        break;
                }
            }
        });
    }
    private    void displayGoods(View parentview)
    {
        //简单适配器的使用
       // GoodsDataAdapter=new SimpleAdapter(getActivity(),getGoodslist(),R.layout.shoppngcart,new String[]{"pic","name","num","price"},new int[]{R.id.im_goods,R.id.buyname,R.id.buynum,R.id.buyprice});
        //Goodslist.setAdapter(GoodsDataAdapter);
        shoppingcartAdapter adapter=new shoppingcartAdapter(getContext(),DataShare.shoplist);
        Goodslist.setAdapter(adapter);
        TextView tv_allprice=(TextView) parentview.findViewById(R.id.allprice);
        tv_allprice.setText("总计￥："+String.valueOf(DataShare.getshoplistTotalMoney()));
        TextView tv_order=(TextView)parentview.findViewById(R.id.order);
        tv_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DataShare.shoplist.size()!=0)
                {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(),OrderActivity.class);
                    startActivity(intent);
                }


            }
        });

    }

}
