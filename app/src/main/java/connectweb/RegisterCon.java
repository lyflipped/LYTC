package connectweb;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by liyang on 2017/4/21.
 */

public class RegisterCon {
    private static String path="http://10.108.167.190:8080/ServletForApp/";    //设置服务器的地址
    private static String username;
    private static String password;
    private static boolean registerpan;
    private  static int flag=0;
    private static String registerurl;

    public void setLoginurl(String uid,String pwd)
    {
        this.registerurl=path+"UserAction?type=register&uid="+uid+"&pwd="+pwd;
        this.username=uid;
        this.password=pwd;
    }
    public boolean getregisterpan()
    {
        return registerpan;
    }
    public int getFlag()
    {
        return flag;
    }

    public void requestlogin() {
        // 网络通信属于典型的耗时操作，开启新线程进行网络请求
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
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                flag=1;
                System.out.println("handler中的json流:"+msg.obj.toString());
                String ans=msg.obj.toString();
                userRegister(ans);

            }
        }
    };
    public  void userRegister (String ans)
    {

        try
        {
            JSONObject tmp=new JSONObject(ans);
            registerpan= tmp.getBoolean("ans");
            System.out.println("registerpan赋值之后："+registerpan);

        }catch (JSONException e)
        {
            e.getStackTrace();
        }

    }
}
