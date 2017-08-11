package com.example.liyang.mynewfragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import datashare.DataShare;
import entity.BillEntity;
import entity.Goods;
import java.text.SimpleDateFormat;
public class OrderActivity extends AppCompatActivity {
    private List<Goods> orderinfolist;
    private ListView orderinfo;
    private static String addbiipath;
    private static boolean judge;
    private BillEntity billEntity;
    private String gids="";
    private String gnums="";
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        orderinfo=(ListView) findViewById(R.id.orderitem);
        scrollView=(ScrollView) findViewById(R.id.order_scroll);
        InitView();
    }
    private void InitView()
    {
        AutoCompleteTextView people_name=(AutoCompleteTextView) findViewById(R.id.peoname);
        final AutoCompleteTextView people_address=(AutoCompleteTextView) findViewById(R.id.peoaddress);
        final EditText phone=(EditText) findViewById(R.id.persphone);
        Spinner gettime=(Spinner) findViewById(R.id.spinner_time);
        Spinner affordway=(Spinner)findViewById(R.id.spinner_buyway);
        Button submit=(Button)findViewById(R.id.submitorder);
        String [] time={"只工作日送货（双休日及节假日除外）","只在节假日收货","只在节假日和双休日收货","任意时间均可"};
        ArrayAdapter<String > adapter_time=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,time);
        adapter_time.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        gettime.setAdapter(adapter_time);
        String [] way={"现金","货到付款","网银在线支付","支付宝","微信支付"};
        ArrayAdapter<String > adapter_way=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,way);
        adapter_way.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        affordway.setAdapter(adapter_way);
       // simpleAdapter=new SimpleAdapter(this,getGoodslist(),R.layout.shoppngcart,new String[]{"pic","name","num","price"},new int[]{R.id.im_goods,R.id.buyname,R.id.buynum,R.id.buyprice});
        //orderinfo.setAdapter(simpleAdapter);
        shoppingcartAdapter adapter=new shoppingcartAdapter(this,DataShare.shoplist);
        orderinfo.setAdapter(adapter);
        orderinfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        TextView tv_affordmoney=(TextView) findViewById(R.id.affordmoney);
        tv_affordmoney.setText("应支付总金额：￥"+String.valueOf(DataShare.getshoplistTotalMoney()));
        //下面三句话  获取系统时间
        SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy.MM.dd-HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        final String btime=formatter.format(curDate);
        //将订单保存一下
        billEntity=new BillEntity();
        billEntity.setAddress(people_address.getText().toString());
        billEntity.setUid(DataShare.user.getuid());
        billEntity.setStatus(1);
        billEntity.setBtime(phone.getText().toString());
        billEntity.setTotalprice(DataShare.getshoplistTotalMoney());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddbiipath(DataShare.user.getuid(),phone.getText().toString(),btime,String.valueOf(DataShare.getshoplistTotalMoney()),people_address.getText().toString());
                addBillCon();
            }
        });

    }

    private void setAddbiipath(String uid,String phone,String btime,String totalparice,String address)
    {
        for(int i=0;i<DataShare.shoplist.size();i++) {
            this.gids =gids+ String.valueOf(DataShare.shoplist.get(i).getId())+",";
            this.gnums=gnums+String.valueOf(DataShare.shoplist.get(i).getBuycount())+",";
        }
        System.out.println("gids="+gids);
        System.out.println("gnums="+gnums);
        this.addbiipath=DataShare.ip_app+"BillAction?type=add&uid="+uid+"&gids="+gids+"&gnums="+gnums+
                "&phone"+phone+"&btime="+btime+"&totalprice="+totalparice+"&address="+address;
    }
    private void addBillCon()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    // String urlreal=path+"UserAction?type=login&uid="+username+"&pwd="+password;
                    URL url = new URL(addbiipath);
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
                    addBill(ans);
                    break;
                case 2:
                    System.out.println("进入case2了");
                   // Toast.makeText(getContext(),"精品推荐页加载成功",Toast.LENGTH_SHORT).show();
                    if(judge==true)
                    {
                        Toast.makeText(getApplicationContext(),"下单成功",Toast.LENGTH_SHORT).show();
                        DataShare.shoplist.clear();
                        Intent  intent=new Intent();
                        Bundle bundle= new Bundle();
                        bundle.putSerializable("BillObj",billEntity);
                        intent.putExtras(bundle);
                        intent.setClass(getApplication(),payActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"提交失败",Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    };
    public boolean addBill(String ans)
    {
        boolean pan =false;
        try
        {
            JSONObject tmp=new JSONObject(ans);
            pan=tmp.getBoolean("ans");
            judge=pan;
            Message flag=new Message();
            flag.what=2;
            handler.sendMessage(flag);
        }catch (JSONException e)
        {
            e.getStackTrace();
        }
        return pan;
    }

}
