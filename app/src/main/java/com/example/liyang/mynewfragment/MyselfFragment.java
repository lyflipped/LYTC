package com.example.liyang.mynewfragment;

        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.os.AsyncTask;
        import android.text.TextUtils;

        import com.avos.avoscloud.AVUser;

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
        import java.lang.String;
        import java.lang.*;
        import java.util.ArrayList;
        import java.util.List;

        import datashare.DataShare;
        import entity.BillEntity;
        import entity.Goods;


/**
 * Created by liyang on 2017/4/5.
 */

public class MyselfFragment extends Fragment {
    private static  String ShowBillpath;
    private TextView tvusername;
    private TextView tvhuizhang;
    private TextView dengchu;
    private TextView tvdingdan;
    private TextView tvdfk;
    private TextView tvdfh;
    private TextView tvdsh;
    private TextView tvshoucang;
    private TextView tvpingjia;
    private TextView tvshouhou;
    private View myselfview;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            myselfview = inflater.inflate(R.layout.userinfo, container, false);
            DataShare.billlist.clear();
            setShowBillpath();
            getBillListCon();
           // initview(myselfview);
            setDengchu(myselfview);
            return myselfview;

    }
    private  void  setShowBillpath()
    {
        this.ShowBillpath=DataShare.ip_app+"BillAction?type=list&uid="+ DataShare.user.getuid();
    }
    private void initview(View parentview)
    {
        tvusername=(TextView)parentview.findViewById(R.id.mys_name);
        tvusername.setText(DataShare.user.getuid());
        tvhuizhang=(TextView) parentview.findViewById(R.id.mys_huizhang);
        tvhuizhang.setText("金牌用户");
        //依次对各个项目设置监听事件。
        tvdingdan=(TextView)parentview.findViewById(R.id.dingdan);
        tvdingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),ShowBillActivity.class);
                intent.putExtra("order_status","0");
                startActivity(intent);
            }
        });
        tvdfk=(TextView) parentview.findViewById(R.id.daifukuan);
        tvdfk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),ShowBillActivity.class);
                intent.putExtra("order_status","1");
                startActivity(intent);

            }
        });
        tvdfh=(TextView) parentview.findViewById(R.id.daifahuo);
        tvdfh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),ShowBillActivity.class);
                intent.putExtra("order_status","2");
                startActivity(intent);

            }
        });
        tvdsh=(TextView) parentview.findViewById(R.id.daishouhuo);
        tvdsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),ShowBillActivity.class);
                intent.putExtra("order_status","3");
                startActivity(intent);

            }
        });
        tvshoucang=(TextView) parentview.findViewById(R.id.shoucang);
        tvshoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                

            }
        });
        tvpingjia=(TextView) parentview.findViewById(R.id.pingjia);
        tvpingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),ShowBillActivity.class);
                intent.putExtra("order_status","4");
                startActivity(intent);

            }
        });
        tvshouhou=(TextView) parentview.findViewById(R.id.shouhou);
        tvshouhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void setDengchu(View parent)
    {
        dengchu=(TextView)parent.findViewById(R.id.mys_dengchu);
        dengchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId())
                {
                    case R.id.mys_dengchu:
                        Intent intent=new Intent();
                        intent.setClass(getContext(),LoginActivity.class);
                        startActivity(intent);
                        break;


                }
            }
        });
    }
    private void getBillListCon()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    // String urlreal=path+"UserAction?type=login&uid="+username+"&pwd="+password;
                    URL url = new URL(ShowBillpath);
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
                    getBillList(ans);
                    break;
                case 2:
                    Toast.makeText(getContext(),"个人中心加载成功",Toast.LENGTH_SHORT).show();
//                    for(int i=0;i<DataShare.billlist.size();i++)
//                        System.out.println("billlist中的情况：第"+i+"个订单中的glist大小="+DataShare.billlist.get(i).getGlist().size());
                    initview(myselfview);

                    break;

            }
        }
    };
    public void getBillList(String ans)
    {
        System.out.println("进入订单函数");
        JSONArray myjson;
        try
        {
            myjson=new JSONArray(ans);
            for(int i=0;i<myjson.length();i++)
            {
                System.out.println("进入订单下载");
               JSONObject tmp = myjson.getJSONObject(i);
              BillEntity be=new BillEntity();
                be.setId(tmp.getInt("Id"));
                be.setUid(tmp.getString("uid"));
           //    be.setGnums(tmp.getString("gnums"));
                be.setStatus(tmp.getInt("status"));
               be.setAddress(tmp.getString("address"));
                be.setCtime(tmp.getInt("ctime"));
                be.setBtime(tmp.getString("btime"));
                be.setPaymoney(tmp.getString("paymoney"));
                be.setGlist(getshopgoodsList(tmp.getString("glistinjson")));
                System.out.println("billlist中glist赋值后的大小："+be.getGlist().size());
                DataShare.billlist.add(be);
            }
            Message flag=new Message();
            flag.what=2;
            handler.sendMessage(flag);
        }catch (JSONException e)
        {
            e.getStackTrace();
        }

    }

    public List<Goods> getshopgoodsList(String ans)
    {
        System.out.println("进入订单商品函数");
        List<Goods> myshopgoodsList = new ArrayList<Goods>();
        JSONArray myjson;
        try
        {
            myjson=new JSONArray(ans);
            for(int i=0;i<myjson.length();i++)
            {
                System.out.println("进入订单商品加载");
                JSONObject tmp= myjson.getJSONObject(i);
                Goods realgoods=new Goods();
                realgoods.setfoodname(tmp.getString("name"));
                realgoods.setId(tmp.getInt("id"));
                realgoods.setplatformprice((float) tmp.getDouble("price"));
                realgoods.setimageurl(tmp.getString("imageurl"));
                realgoods.settexxtintroduce(tmp.getString("textintroduce"));
                realgoods.setBuycount(tmp.getInt("buynum"));
                realgoods.setTotalprice(realgoods.getBuycount()*realgoods.getplatformprice());
                myshopgoodsList.add(realgoods);
            }

        }catch (JSONException e)
        {
            e.getStackTrace();
        }
        return  myshopgoodsList;
    }
}
