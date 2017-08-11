package connectweb;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import entity.BillEntity;
import entity.Goods;
import entity.User;

/**
 * Created by liyang on 2017/4/21.
 */

public class ConnectWeb {

    private static String path="http://10.108.167.190:8080/ServletForApp/";    //设置服务器的地址
    private static String username;
    private static String password;
    private static boolean loginpan=true;
    private static String loginurl;
    //通过服务器获取数据库数据
    public void setLoginurl(String uid,String pwd)
    {
        this.loginurl=path+"UserAction?type=login&uid="+uid+"&pwd="+pwd;
        this.username=uid;
        this.password=pwd;
    }
    public boolean getLoginpan()
    {
        return  loginpan;
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
            if(msg.what == 1){

                boolean pan=false;
                System.out.println("handler中的json流:"+msg.obj.toString());
                String ans=msg.obj.toString();
                userLogin(ans);

            }
        }
    };
    //获取推荐商品列表
    public List<Goods> getPopList()
    {
        List<Goods> mylist = new ArrayList<Goods>();
        String url = path +"GoodsAction?type=pop";
        String data="";
        JSONArray myjson;
        try
        {
            myjson=new JSONArray(data);
            for(int i=0;i<myjson.length();i++)
            {
                JSONObject tmp= myjson.getJSONObject(i);
                Goods realgoods=new Goods();
                realgoods.setfoodname(tmp.getString("name"));
                realgoods.setId(tmp.getInt("id"));
                realgoods.setplatformprice((float) tmp.getDouble("price"));
                realgoods.setimageurl(tmp.getString("imageurl"));
                realgoods.settexxtintroduce("textintroduce");
                mylist.add(realgoods);

            }

        }catch (JSONException e)
        {
            e.getStackTrace();
        }
        return mylist;
    }

    //根据type获取推荐商品
    //type=1名店小吃  type=2 精美纪念；
    public List<Goods> getTypeList(int type)
    {
        List<Goods> mylist = new ArrayList<Goods>();
        String url = path +"GoodsAction?type=type&gtype="+type;
        String data="";
        JSONArray myjson;
        try
        {
            myjson=new JSONArray(data);
            for(int i=0;i<myjson.length();i++)
            {
                JSONObject tmp= myjson.getJSONObject(i);
                Goods realgoods=new Goods();
                realgoods.setfoodname(tmp.getString("name"));
                realgoods.setId(tmp.getInt("id"));
                realgoods.setplatformprice((float) tmp.getDouble("price"));
                realgoods.setimageurl(tmp.getString("imageurl"));
                realgoods.settexxtintroduce("textintroduce");
                mylist.add(realgoods);

            }

        }catch (JSONException e)
        {
            e.getStackTrace();
        }
        return mylist;
    }
    //用户注册判断
    public  boolean userRegister (String uid,String pwd)
    {
        boolean pan =false;
        String url= path+"UserAction?type=register&uid="+uid+"&pwd="+pwd;
        System.out.println(url);
        String ans="";
        try
        {
            JSONObject tmp=new JSONObject(ans);
            pan= tmp.getBoolean("ans");

        }catch (JSONException e)
        {
            e.getStackTrace();
        }
        return pan;
    }
    //用户登陆判断
    public void userLogin(String ans)
    {
       // boolean pan=false;
       // String url=path+"UserAction?type=login&uid="+uid+"&pwd="+pwd;
        //System.out.println("url="+url);
        //String ans=doGet(url);
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
            }
            else
            {
                loginpan=false;
                System.out.println("判断为false");
            }

        }catch (JSONException e)
        {
            e.getStackTrace();
        }
        //return pan;

    }
    //获取用户订单
    public List<BillEntity> getBillList(String uid)
    {
        List<BillEntity> mylist= new ArrayList<BillEntity>();
        String url= path+"BillAction?type=list";
        String ans="";
        JSONArray myjson;
        try
        {
            myjson=new JSONArray();
            for(int i=0;i<myjson.length();i++)
            {
                JSONObject tmp = myjson.getJSONObject(i);
                BillEntity be=new BillEntity();
                be.setUid(tmp.getString("uid"));
                be.setGnums(tmp.getString("gnums"));
                be.setStatus(tmp.getInt("status"));
                be.setAddress(tmp.getString("address"));
                be.setCtime(tmp.getInt("ctime"));
                mylist.add(be);
            }



        }catch (JSONException e)
        {
            e.getStackTrace();
        }
        return mylist;
    }
    //获取用户提交订单的结果
    public boolean addBill(String uid,String gnums,String btime,String totalparice,String address)
    {
        boolean pan =false;
        String url=path +"BillAction?type=add&uid="+uid+"&gnums="
                +gnums+"&btime="+btime+"&totalprice="+totalparice+"&address="+address;
        String ans="";
        try
        {
            JSONObject tmp=new JSONObject();
            pan=tmp.getBoolean("ans");
        }catch (JSONException e)
        {
            e.getStackTrace();
        }
        return pan;
    }

}
