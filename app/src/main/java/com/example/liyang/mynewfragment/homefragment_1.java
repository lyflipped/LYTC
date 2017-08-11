package com.example.liyang.mynewfragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datashare.DataShare;
import entity.Goods;

/**
 * Created by liyang on 2017/4/5.
 */

public class homefragment_1 extends Fragment implements AdapterView.OnItemClickListener{
    private ListView PopGoodsList;
    private static String popgoodspath= DataShare.ip_app+"GoodsAction?type=pop";    //设置服务器的地址
    private List<Goods> myPopList;
    private View home1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(home1==null) {
            home1 = inflater.inflate(R.layout.homefragment_1, container, false);
            PopGoodsList = (ListView) home1.findViewById(R.id.recommandlist);
            PopGoodsList.setOnItemClickListener(this);
            getPopGoodsList();
        }
            return home1;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Goods details=myPopList.get(position);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("GoodObj",(Serializable) details);
        intent.putExtras(bundle);
        intent.setClass(getActivity(),DetailsActivity.class);
        startActivity(intent);
    }

    //先获取商品列表
    private void getPopGoodsList()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    // String urlreal=path+"UserAction?type=login&uid="+username+"&pwd="+password;
                    URL url = new URL(popgoodspath);
                    connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                    connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET
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
    //获取推荐商品列表
    public void getPopList(String ans)
    {
        myPopList = new ArrayList<Goods>();
        JSONArray myjson;
        try
        {
            myjson=new JSONArray(ans);
            for(int i=0;i<myjson.length();i++)
            {
                JSONObject tmp= myjson.getJSONObject(i);
                Goods realgoods=new Goods();
                realgoods.setfoodname(tmp.getString("name"));
                realgoods.setId(tmp.getInt("id"));
                realgoods.setplatformprice((float) tmp.getDouble("price"));
                realgoods.setimageurl(tmp.getString("imageurl"));
                realgoods.settexxtintroduce(tmp.getString("textintroduce"));
                realgoods.setEvaluation(tmp.getString("evaluation"));
                myPopList.add(realgoods);
            }
            Message msglist=new Message();
            msglist.what=2;
            handler.sendMessage(msglist);

        }catch (JSONException e)
        {
            e.getStackTrace();
        }
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
                    getPopList(ans);
                    break;
                case 2:
                    System.out.println("进入case2了");
                    Toast.makeText(getContext(),"精品推荐页加载成功",Toast.LENGTH_SHORT).show();
                    showGoodsList();
                    break;

            }
        }
    };
    //写listview 显示函数  使用简单适配器
    public  void showGoodsList()
    {
        TeChanAdapter adapter=new TeChanAdapter(getContext(),myPopList);
        PopGoodsList.setAdapter(adapter);
    }

}
