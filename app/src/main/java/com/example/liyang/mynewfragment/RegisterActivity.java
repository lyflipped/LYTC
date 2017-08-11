package com.example.liyang.mynewfragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

import connectweb.ConnectWeb;
import connectweb.RegisterCon;
import datashare.DataShare;


/**
 * Created by hjm on 2017/4/1.
 */

public class RegisterActivity extends Activity implements View.OnClickListener{
    private EditText et_write_name;
    private EditText et_write_pwd;
    private EditText et_write_repwd;
    private TextView tv_register;
    private static String path= DataShare.ip_app;    //设置服务器的地址
    private String  username;
    private String password;
    private String registerurl;
    private static boolean registerpan;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register);
        initView();

    }

    private void initView(){
        TextView iv_back=(TextView) findViewById(R.id.fanhui);
        et_write_name=(EditText)findViewById(R.id.et_write_name);
        et_write_pwd=(EditText)findViewById(R.id.et_write_pwd);
        et_write_repwd=(EditText)findViewById(R.id.et_write_repwd);
       tv_register=(TextView)findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);
        iv_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.tv_register:
                String name=et_write_name.getText().toString().trim();
               String pwd=et_write_pwd.getText().toString().trim();
                String repwd=et_write_repwd.getText().toString().trim();

                if(repwd.equals(pwd)) {
                    if (name != null && pwd != null && name.length() > 0 && pwd.length() > 0) {

                        System.out.println("判断之前的registerpan：" + registerpan);
                        seturl(name,pwd);
                        register();
                        System.out.println("判断之后的registerpan：" + registerpan);

                        } else{
                            Toast.makeText(RegisterActivity.this, "请输入完善信息", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                    }


                break;

            case R.id.fanhui:
                finish();
                break;
        }
    }

    public  void seturl(String uid,String pwd)
    {
        this.username=uid;
        this.password=pwd;
        this.registerurl=path+"UserAction?type=register&uid="+uid+"&pwd="+pwd+"&id=0&evaluate=test";
    }
    /*为了实现子线程结束之后主线程在进行操作，必须在handler中实现你要在主线程的操作。
    *创建一个子线程来实现注册访问服务器
     */
    public void register()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    // String urlreal=path+"UserAction?type=login&uid="+username+"&pwd="+password;
                    URL url = new URL(registerurl);
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
    Handler handler=new Handler()
    {
        public void handleMessage(Message msg)
        {
            //此处可以实现   子线程完成之后的想要的操作；
            switch (msg.what)
            {
                case 1:
                    userRegister(msg.obj.toString());
                    tv_register=(TextView)findViewById(R.id.tv_register);
                    tv_register.setOnClickListener(RegisterActivity.this);
                    break;
                case 2:
                    if (registerpan == true) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(RegisterActivity.this, "未知原因，注册失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }



        }
    };
    private void userRegister(String ans)
    {

        try
        {
            JSONObject tmp=new JSONObject(ans);
            registerpan= tmp.getBoolean("ans");
            System.out.println("registerpan赋值之后："+registerpan);
            Message flag=new Message();
            flag.what=2;
            handler.sendMessage(flag);


        }catch (JSONException e)
        {
            e.getStackTrace();
        }

    }
}
