package com.example.liyang.mynewfragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.avos.avoscloud.AVUser;

public class Welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //欢迎界面需要全屏显示
       final Window window=this.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_welcome);


        ImageView iv= (ImageView) this.findViewById(R.id.showlogo);
        welcome();
    }

    public  void login()
    {
            Intent intent =new Intent(Welcome.this,LoginActivity.class);
            startActivity(intent);
            Log.i("tag","直接进入");
            Welcome.this.finish();

    }
    public void welcome()
    {
        new Thread(new Runnable(){
            public void run() {
                try
                {
                    Thread.sleep(2000);
                    Message m = new Message();
                    logHandler.sendMessage(m);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    Handler logHandler = new Handler()
    {
        public  void handleMessage (Message msg)
        {
            //welcome1();
            login();
        }
    };

    public  void welcome1()
    {
        Intent intent =new Intent(Welcome.this,LoginActivity.class);
        startActivity(intent);
        Log.i("tag","qingdongle ");
        Welcome.this.finish();
    }


    public boolean onKeyDowm(int keyCode , KeyEvent event)
    {
        if(keyCode==4)
        {
            Log.i("main","退了");
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        return  super.onKeyDown(keyCode, event);
    }
}
