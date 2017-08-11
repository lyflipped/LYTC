package com.example.liyang.mynewfragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datashare.DataShare;
import entity.BillEntity;
import entity.Goods;

public class ShowBillActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private  static List<BillEntity> mylist=DataShare.billlist;
    private ShowBillActivity context;
    private ListView BillList;
    private static String order_status;
    private final static int RequestCode=2;
    private static int finished_position;
    private static List<Map<String,Object>> billlist;
    private SimpleAdapter adapter;
    private String changestatuspath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill);
        context=this;
        BillList=(ListView)findViewById(R.id.bills);
        Intent intent= getIntent();
        order_status=intent.getStringExtra("order_status");
        System.out.println(order_status);

        showBillList();
        BillList.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            finished_position=position;
            BillEntity be=getMylist(order_status).get(position);
            Intent intent= new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("BillObj",(Serializable) be);
             bundle.putString("order_status",order_status);
            intent.putExtras(bundle);

            intent.setClass(this,OrderDetailActivity.class);
            startActivityForResult(intent,RequestCode);
    }

    //写listview 显示函数  使用简单适配器
    public  void showBillList()
    {
        billlist=getTripList();
        adapter=new SimpleAdapter(this,billlist,
                R.layout.showbillitem,new String[]{"pic","uid","paymoney","status","address","btime"},
                new int[]{R.id.im_dingdan,R.id.order_name,R.id.order_totalmoney,R.id.order_status,R.id.order_address,R.id.order_time});
        BillList.setAdapter(adapter);
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

    //将获取到的list转化成可以简单适配器可以使用的list
    public  List<Map<String ,Object>> getTripList() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        switch (order_status)
        {
            case "0"://全部订单

                for (int i = 0; i < mylist.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    BillEntity be = mylist.get(i);
                    map.put("pic", R.drawable.dingdan2);
                    map.put("uid", "下单用户：" + be.getUid());
                    map.put("paymoney", "支付金额：" + be.getPaymoney());
                    switch (be.getStatus())
                    {
                        case 1:
                            map.put("status", "订单状态：待付款");
                            break;
                        case 2:
                            map.put("status", "订单状态：待发货");
                            break;
                        case 3:
                            map.put("status", "订单状态：待收货");
                            break;
                        case 4:
                            map.put("status", "订单状态：待评价");
                            break;

                    }
                    map.put("address", "收货地址：" + be.getAddress());
                    map.put("btime", "下单时间：" + be.getBtime());
                    list.add(map);
                }
                break;

            case "1"://待付款订单
                //List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < mylist.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    BillEntity be = mylist.get(i);
                    if (be.getStatus() == 1) {
                        map.put("pic", R.drawable.dingdan2);
                        map.put("uid", "下单用户：" + be.getUid());
                        map.put("paymoney", "支付金额：" + be.getPaymoney());
                        map.put("status", "订单状态：待付款");
                        map.put("address", "收货地址：" + be.getAddress());
                        map.put("btime", "下单时间：" + be.getBtime());
                        list.add(map);
                    }
                }
               break;
            case "2"://待发货订单
               // List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < mylist.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    BillEntity be = mylist.get(i);
                    if (be.getStatus() == 2) {
                        map.put("pic", R.drawable.dingdan2);
                        map.put("uid", "下单用户：" + be.getUid());
                        map.put("paymoney", "支付金额：" + be.getPaymoney());
                        map.put("status", "订单状态：待发货");
                        map.put("address", "收货地址：" + be.getAddress());
                        map.put("btime", "下单时间：" + be.getBtime());
                        list.add(map);
                    }
                }
                break;

            case "3"://待收货订单
                //List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < mylist.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    BillEntity be = mylist.get(i);
                    if (be.getStatus() == 3) {
                        map.put("pic", R.drawable.dingdan2);
                        map.put("uid", "下单用户：" + be.getUid());
                        map.put("paymoney", "支付金额：" + be.getPaymoney());
                        map.put("status", "订单状态：待收货");
                        map.put("address", "收货地址：" + be.getAddress());
                        map.put("btime", "下单时间：" + be.getBtime());
                        list.add(map);
                    }
                }
                break;

            case "4"://待评价订单
                //List<Map<String, Object>> list4 = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < mylist.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    BillEntity be = mylist.get(i);
                    if (be.getStatus() == 4) {
                        map.put("pic", R.drawable.dingdan2);
                        map.put("uid", "下单用户：" + be.getUid());
                        map.put("paymoney", "支付金额：" + be.getPaymoney());
                        map.put("status", "订单状态：待评价");
                        map.put("address", "收货地址：" + be.getAddress());
                        map.put("btime", "下单时间：" + be.getBtime());
                        list.add(map);
                    }
                }
                break;


        }
        Collections.reverse(list);
        return  list;
    }
    private  List<BillEntity> getMylist(String order_status)
    {
        List<BillEntity> newMylist=new ArrayList<BillEntity>();
        switch (order_status)
        {
            case "0":
                newMylist=DataShare.billlist;
                break;
            case "1":
                for (int i = 0; i < mylist.size(); i++) {
                    BillEntity be = mylist.get(i);
                    if (be.getStatus() == 1) {
                        newMylist.add(be);
                    }
                }
                break;
            case "2":
                for (int i = 0; i < mylist.size(); i++) {
                    BillEntity be = mylist.get(i);
                    if (be.getStatus() == 2) {
                        newMylist.add(be);
                    }
                }
                break;
            case "3":
                for (int i = 0; i < mylist.size(); i++) {
                    BillEntity be = mylist.get(i);
                    if (be.getStatus() == 3) {
                        newMylist.add(be);
                    }
                }
                break;
            case "4":
                for (int i = 0; i < mylist.size(); i++) {
                    BillEntity be = mylist.get(i);
                    if (be.getStatus() == 4) {
                        newMylist.add(be);
                    }
                }
                break;
        }
        Collections.reverse(newMylist);
        return newMylist;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode==RequestCode)
        {
            if (resultCode==OrderDetailActivity.ResultCode)
            {
                Bundle bundle=data.getExtras();
                String str=bundle.getString("ANS");
                System.out.println("str="+str);
                if(str.equals("success")) {
                    //更改订单状态  并remove掉这个订单从待评价列表里

                    int evaluatedbill_id=bundle.getInt("billId");
                    changestatuspath=DataShare.ip_app+"BillAction?type=changestatus&Id="+evaluatedbill_id+"&status=6";
                    changestatus();
                     //Toast.makeText(this, "此订单评价完成", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    private void changestatus()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    // String urlreal=path+"UserAction?type=login&uid="+username+"&pwd="+password;

                    URL url = new URL(changestatuspath);
                    connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                    connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                    connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                    connection.setReadTimeout(8000); // 设置网络报文收发超时时间
                    InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = response.toString();
                    Log.e("WangJ", response.toString());
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what)
            {
                case 1:
                    System.out.println("handler中的json流:"+msg.obj.toString());
                    String ans=msg.obj.toString();
                    getchangedans(ans);
                    break;
                case 2:
                    Toast.makeText(context,"订单评价成功",Toast.LENGTH_SHORT).show();
                    billlist.remove(finished_position);
                    adapter.notifyDataSetChanged();
                    break;
                case 3:
                    Toast.makeText(context,"订单评价失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private void getchangedans(String ans)
    {
        boolean pan;
        try
        {
            JSONObject tmp=new JSONObject(ans);
            pan=tmp.getBoolean("ans");
            if (pan==true)
            {
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
            }
            else
            {
                Message msg = new Message();
                msg.what = 3;
                handler.sendMessage(msg);
            }
        }catch (JSONException e)
        {
            e.getStackTrace();
        }
    }
}
