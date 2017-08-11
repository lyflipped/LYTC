package com.example.liyang.mynewfragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import java.util.Objects;

import datashare.DataShare;
import entity.BillEntity;
import entity.Goods;

public class OrderDetailActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView ordergoodslist;
    private  BillEntity thebill;
    private static String order_status;
    private static int item_position;
    private final static int REQUEST_CODE=1;     //和订单详情中的商品列表成对
    //private List<Map<String,Object>> DataList;
    private shoppingcartAdapter shAdapter;
   // private static List<Object> eva_success=new ArrayList<Object>();
    public final static int ResultCode=2;                 //和订单列表的返回值成对
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        thebill=(BillEntity) bundle.getSerializable("BillObj");
        order_status=bundle.getString("order_status");
        System.out.println("order_status:"+order_status);
        InitView();
        ordergoodslist.setOnItemClickListener(this);

    }
    private void InitView()
    {
        ordergoodslist=(ListView)findViewById(R.id.order_detail);
        if(DataShare.DataList.size()==0)
        DataShare.DataList=getTripList(thebill.getGlist());
        showGoodsList();
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        int i=0;
        for(i=0;i<DataShare.eva_success.size();i++)
        {
            if(Integer.valueOf(DataShare.eva_success.get(i).toString())==position)
                break;
        }
        if(order_status.equals("4")&&i==DataShare.eva_success.size()) {
            item_position=position;
            Goods details = thebill.getGlist().get(position);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("GoodObj", (Serializable) details);
            intent.putExtras(bundle);
            intent.setClass(this, EvaluateActivity.class);
            startActivityForResult(intent,REQUEST_CODE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode==REQUEST_CODE)
        {
            if (resultCode==EvaluateActivity.RESULT_CODE)
            {
                Bundle bundle=data.getExtras();
                String str=bundle.getString("ANS");
                if(str.equals("success")) {
                    thebill.getGlist().remove(item_position);
                    if(thebill.getGlist().size()==0)
                    {
                        Intent intent = new Intent();
                        intent.putExtra("ANS", "success");
                        intent.putExtra("billId",thebill.getId());
                        setResult(ResultCode, intent);
                        finish();
                    }
                    shAdapter.notifyDataSetChanged();

                }
              //  Toast.makeText(this, str, Toast.LENGTH_LONG).show();
            }
        }
    }
    public  void showGoodsList()
    {

        System.out.println("glist对应的价格="+thebill.getPaymoney());

//         adapter=new SimpleAdapter(this,DataShare.DataList,
//                R.layout.shoppngcart,new String[]{"image","name","number","totalprice","eva_status"},
//                new int[]{R.id.im_goods,R.id.buyname,R.id.buynum,R.id.buyprice,R.id.eva_status});
//        ordergoodslist.setAdapter(adapter);
      shAdapter=new shoppingcartAdapter(this,thebill.getGlist());
        ordergoodslist.setAdapter(shAdapter);
//        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
//            @Override
//            public boolean setViewValue(View view, Object o, String s) {
//                if((view instanceof ImageView)&(o instanceof Bitmap))
//                {
//                    ImageView imageView=(ImageView) view;
//                    Bitmap bitmap=(Bitmap) o;
//                    imageView.setImageBitmap(bitmap);
//                    return true;
//                }
//                else
//                {
//                    return false;
//                }
//            }
//        });
    }
    public List<Map<String ,Object>> getTripList(List<Goods> thebillgoodslist)
    {
        List<Map<String , Object>> list=new ArrayList<Map<String, Object>>();
        for(int i=0;i<thebillgoodslist.size();i++)
        {
            Map<String ,Object> map=new HashMap<String, Object>();
            Goods goods= thebillgoodslist.get(i);
//            try
//            {
//                URL picurl= new URL(goods.getimageurl());
//                Bitmap pngBM = BitmapFactory.decodeStream(picurl.openStream());
//                map.put("image",pngBM);
//            }catch (Exception e)
//            {
//                e.getStackTrace();
//            }
            map.put("image",R.drawable.panda);
            map.put("name","商品名称："+goods.getfoodname());
            map.put("number","数量*"+goods.getBuycount());
            map.put("totalprice","总价：￥"+goods.getTotalprice());
            map.put("eva_status","");
            list.add(map);
        }
        return  list;
    }
}
