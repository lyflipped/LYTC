package com.example.liyang.mynewfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Filterable;
import android.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import datashare.DataShare;
import entity.Goods;

import static com.avos.avoscloud.Messages.OpType.query;

/**
 * Created by liyang on 2017/5/3.
 */

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {
    private View view;
    private SearchView searchView;
    private ListView ls;
    private Goods realgoods;
    private String[] GoodsId;
    private String[] mStrings;
    private static String path = DataShare.ip_app+"SearchAction?type=namelist";
    private static String path_ans = DataShare.ip_app+"SearchAction?type=search&Id=";
    private static String path_ans_id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.searchfragment, container, false);
        getNameList();
        InitView(view);
        return view;
    }

    private void InitView(View parent) {
        searchView = (SearchView) parent.findViewById(R.id.searchview);
        searchView.setIconifiedByDefault(false);
//       ls=(ListView) parent.findViewById(R.id.ls);
//        ls.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, mStrings));
//        ls.setTextFilterEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("查找商品");


    }

    public boolean onQueryTextChange(String newtext) {
        if (TextUtils.isEmpty(newtext)) {
            // 清除ListView的过滤
            ls.clearTextFilter();
        } else {
            // 使用用户输入的内容对ListView的列表项进行过滤
            ls.setFilterText(newtext);

        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        //在此处执行查询逻辑
        int Id=getId(query);
        setPath_ans(Id);
        getSearchAns();
        Toast.makeText(getContext(), "您的选择是:" + query, Toast.LENGTH_SHORT).show();
        return false;
    }
    private int getId(String query)
    {
        int id=0;
        for(int i=0;i<mStrings.length;i++)
        {
            if(query.equals(mStrings[i])) {
                id = i;
                break;
            }
        }
        return Integer.valueOf(GoodsId[id]);
    }
    private void setPath_ans(int Id)
    {
        path_ans_id=path_ans+Id;
    }
    private void setmStrings(String namelist, View parent) {
        String ans="";
        try
        {
            JSONObject tmp=new JSONObject(namelist);
            ans=tmp.getString("namelist");
            System.out.println("namelist="+namelist);
        }catch (JSONException e)
        {
            e.getStackTrace();
        }
        final String[] mString = ans.split(",");
        mStrings=new String[mString.length];
        GoodsId=new String[mString.length];
        for(int i=0;i<mString.length;i++)
        {
            String [] tmp=mString[i].split("\\|");
            mStrings[i]=tmp[1];
            GoodsId[i]=tmp[0];
        }
        ls = (ListView) parent.findViewById(R.id.ls);
        ls.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mStrings));
        ls.setTextFilterEnabled(true);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchView.setQuery(adapterView.getItemAtPosition(i).toString(),true);
            }
        });
    }

    private void getNameList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(path);
                    connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                    connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                    connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                    connection.setReadTimeout(8000); // 设置网络报文收发超时时间
                    InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = response.toString();
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    boolean pan = false;
                    System.out.println("handler中的json流:" + msg.obj.toString());
                    String ans = msg.obj.toString();
                    setmStrings(ans, view);
                    break;
                case 2:
                    String ans1=msg.obj.toString();
                    System.out.println("进入case2了"+ans1);
                    getTheGoods(ans1);
                    break;
                case 3:
                    Toast.makeText(getContext(),"搜索成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("GoodObj",(Serializable) realgoods);
                    intent.putExtras(bundle);
                    intent.setClass(getActivity(),DetailsActivity.class);
                    startActivity(intent);
            }
        }

    } ;
    private void getSearchAns()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(path_ans_id);
                    connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                    connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                    connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                    connection.setReadTimeout(8000); // 设置网络报文收发超时时间
                    InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = response.toString();
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void getTheGoods(String ans)
    {
        try
        {
                JSONObject tmp= new JSONObject(ans);
                 realgoods=new Goods();
                realgoods.setfoodname(tmp.getString("name"));
                realgoods.setId(tmp.getInt("id"));
                realgoods.setplatformprice((float) tmp.getDouble("price"));
                realgoods.setimageurl(tmp.getString("imageurl"));
                realgoods.settexxtintroduce(tmp.getString("textintroduce"));
                realgoods.setEvaluation(tmp.getString("evaluation"));
            Message msglist=new Message();
            msglist.what=3;
            handler.sendMessage(msglist);

        }catch (JSONException e)
        {
            e.getStackTrace();
        }
    }
}
