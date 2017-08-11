package com.example.liyang.mynewfragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import datashare.DataShare;
import entity.Goods;

public class EvaluateActivity extends AppCompatActivity {
    //在此界面，需要将用户评价封装成 userid@@@time@@@content@@@stars@@@
    //用%*%作为分隔符，这样在以后方便提取；
    private EvaluateActivity context;
    private static String eva_json;
    private static String eva_path;
    private  static Goods thegoods;
    public final static int RESULT_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_evaluate);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
       thegoods=(Goods)bundle.getSerializable("GoodObj");
        InitView();

    }
    private void InitView()
    {
        final EditText eva_text=(EditText) findViewById(R.id.eva_text);
        final RatingBar rating_ans=(RatingBar) findViewById(R.id.rating_ans);
        RatingBar rating_expree_v=(RatingBar) findViewById(R.id.express_speed);
        RatingBar rating_goodsface=(RatingBar) findViewById(R.id.goods_face);
        RatingBar rating_attitude=(RatingBar) findViewById(R.id.exp_attitude);
        Button submit_eva=(Button) findViewById(R.id.submit_eva);
        submit_eva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eva_text.getText().toString()==null)
                {
                    Toast.makeText(context,"评价不能为空",Toast.LENGTH_SHORT).show();
                }
                else {

                    setEva_json(eva_text.getText().toString(), String.valueOf(rating_ans.getRating()));
                    eva_path=DataShare.ip_app+"UserAction?type=evaluate&id="+thegoods.getId()+"&evaluate="+eva_json;
                    EvaCon();
                }
            }
        });
    }
    private void setEva_json(String eva_text,String rating_ans)
    {

        //用EOF做分隔符；
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy.MM.dd-HH:mm:ss");
        String text="";
        try {
             text = URLEncoder.encode(eva_text.toString(), "utf-8");
        }catch(Exception e)
        {
            e.getStackTrace();
        }
        Date curDate =  new Date(System.currentTimeMillis());
        final String time=formatter.format(curDate);
         eva_json= DataShare.user.getuid().toString()+"EOF"+time+"EOF"+text+"EOF"+rating_ans+"EOF";
    }
    private void EvaCon()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    // String urlreal=path+"UserAction?type=login&uid="+username+"&pwd="+password;

                    URL url = new URL(eva_path);
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
                    boolean pan=false;
                    System.out.println("handler中的json流:"+msg.obj.toString());
                    String ans=msg.obj.toString();
                    userEva(ans);
                    break;
                case 2:
                    Toast.makeText(context,"评价成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.putExtra("ANS", "success");
                    setResult(RESULT_CODE, intent);
                    finish();
                    break;
                case 3:
                    Toast.makeText(context,"评价失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private void userEva(String ans)
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
