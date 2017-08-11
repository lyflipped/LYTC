package com.example.liyang.mynewfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

import entity.Goods;

/**
 * Created by liyang on 2017/5/25.
 */

public class shoppingcartAdapter extends BaseAdapter {
    private Context context;
    private List<Goods> list;

    public shoppingcartAdapter(Context context, List<Goods>  list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<Goods> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //获得一个布局填充器，将xml布局文件转换为View对象
        View view= LayoutInflater.from(context).inflate(R.layout.shoppngcart,null);
        ImageView iv_goodspic=(ImageView)view.findViewById(R.id.im_goods);
        TextView tv_des=(TextView)view.findViewById(R.id.buyname);
        TextView tv_price= (TextView) view.findViewById(R.id.buyprice);
        TextView tv_num= (TextView) view.findViewById(R.id.buynum);
        // iv_goodspic.setImageResource(R.drawable.guolan);
        System.out.println("我在适配器执行getview");
        tv_des.setText(list.get(position).getfoodname());
        //System.out.println("只是适配器里面的des"+list.get(position).getGoodsdes());
        tv_num.setText("数量* "+String.valueOf(list.get(position).getBuycount()));
        tv_price.setText("价格 ￥"+list.get(position).getTotalprice());
        String url=list.get(position).getimageurl();
        System.out.println("我在适配器内执行图片下载");
        ImageLoader.getInstance().displayImage(url,iv_goodspic,options); //选项加载

        return view;
    }

    //4.5 参考http://www.cnblogs.com/tianzhijiexian/p/4034304.html
    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.chengzi) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.drawable.buou) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.dang) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
            .build();
}