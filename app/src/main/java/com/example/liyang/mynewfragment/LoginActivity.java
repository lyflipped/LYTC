package com.example.liyang.mynewfragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

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

import connectweb.ConnectWeb;
import datashare.DataShare;
import entity.User;


public class LoginActivity extends Activity implements View.OnClickListener {
        private LoginActivity context;
        private EditText et_name;
        private EditText et_pwd;

    private static String path=DataShare.ip_app;    //设置服务器的地址
    private static String username;
    private static String password;
    private static boolean loginpan=true;
    private static String loginurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=this;

        initView();
    }
    private void initView(){
        TextView btn_login=(TextView)findViewById(R.id.tv_login);
        btn_login.setOnClickListener(this);
        TextView tv_zhuce=(TextView)findViewById(R.id.tv_register);
        tv_zhuce.setOnClickListener(this);
        et_name=(EditText)findViewById(R.id.et_name);
        et_pwd=(EditText)findViewById(R.id.et_pwd);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_login:
                Login();
                break;
            case R.id.tv_register:
                Register();
                break;
        }
    }
    private void Login(){
        String username=et_name.getText().toString();
        try {
            username = URLEncoder.encode(username, "UTF-8");
        }catch (Exception e)
        {
            e.getStackTrace();
        }
        String password=et_pwd.getText().toString();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(context,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(context,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        if(username!=null&&username.length()>0&&password!=null&&password.length()>0){
            setLoginurl(username,password);
            requestlogin();

        }else{
            Toast.makeText(context,"请填写登陆信息",Toast.LENGTH_SHORT).show();
        }
    }

    private void Register(){
        Intent intent = new Intent();
        intent.setClass(context,RegisterActivity.class);//intent.setclass开启新activity，第一个参数为当前context，第二个为打开的activity的类名
        startActivity(intent);
    }
    private void GoHome(){
        Intent intent = new Intent();
        intent.setClass(context,MainActivity.class);
        startActivity(intent);
        finish();/* 从视图堆栈中pop出调用finish（）的activity，释放资源*/
    }


    public void setLoginurl(String uid,String pwd)
    {
        this.loginurl=path+"UserAction?type=login&uid="+uid+"&pwd="+pwd+"&id=0&evaluate=test";
        this.username=uid;
        this.password=pwd;
    }
    public void requestlogin() {
        // 网络通信属于典型的耗时操作，开启新线程进行网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    // String urlreal=path+"UserAction?type=login&uid="+username+"&pwd="+password;
                    URL url = new URL(loginurl);
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
                    userLogin(ans);
                    break;
                case 2:
                        Toast.makeText(context,"登录成功",Toast.LENGTH_SHORT).show();

                        GoHome();
                    break;
                case 3:

                        Toast.makeText(context,"用户名或密码错误",Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };

    //处理获取的json流，并得出结果
    public void userLogin(String ans)
    {

        System.out.println("userLogin中的json流:"+ans);
        try
        {
            JSONObject tmp=new JSONObject(ans);
            User user=new User();
            user.setuid(tmp.getString("username"));
            user.setpwd(tmp.getString("pwd"));
            System.out.println("设置之后的uid："+user.getuid());
            System.out.println("设置之后的pwd："+user.getpwd());
            System.out.println("判断之前的username："+username);
            System.out.println("判断之前的password："+password);
            if(user.getuid().equals(username)&&user.getpwd().equals(password))
            {
                loginpan=true;
                System.out.println("判断为true");
                Message flag=new Message();
                DataShare.user=user;
                flag.what=2;
                handler.sendMessage(flag);
            }
            else
            {
                Message flag1=new Message();
                flag1.what=3;
                handler.sendMessage(flag1);
                loginpan=false;
                System.out.println("判断为false");
            }

        }catch (JSONException e)
        {
            e.getStackTrace();
        }
        //return pan;

    }
}
