package com.example.liyang.mynewfragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVFile;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import datashare.DataShare;
import entity.Evaluation;
import entity.Goods;

import java.io.Serializable;
import java.util.*;

public class DetailsActivity extends AppCompatActivity {
    private static  Goods thegood;
    private DetailsActivity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context=this;
        Intent intent=getIntent();
        initview(intent);
      InitEvaList();
    }
    private void  initview(final Intent intent)
    {
        ImageView iv_img=(ImageView)findViewById(R.id.details_image);
        TextView tv_detailname=(TextView)findViewById(R.id.details_name);
        TextView tv_detailclass=(TextView)findViewById(R.id.details_clssification);
        TextView tv_detaildes=(TextView)findViewById(R.id.details_des);
        TextView tv_detailprice=(TextView)findViewById(R.id.details_price);
        final TextView tv_showbuycount=(TextView)findViewById(R.id.buycount);
        WebView web_text=(WebView) findViewById(R.id.text_introduce);
        final TextView tv_totalprice=(TextView)findViewById(R.id.totalprice);
        Button shopcard=(Button)findViewById(R.id.button_card);
        NumberPicker numberPicker=(NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(1);

        Bundle bundle= intent.getExtras();
        thegood=(Goods) bundle.getSerializable("GoodObj");
        thegood.setBuycount(1);
        web_text.getSettings().setJavaScriptEnabled(true);
        web_text.loadData(thegood.gettextintroduce(), "text/html; charset=UTF-8", null);
        thegood.setTotalprice(thegood.getBuycount()*thegood.getplatformprice());
        tv_totalprice.setText("共计：￥"+String.valueOf(thegood.getBuycount()*thegood.getplatformprice()));
        tv_detailname.setText(thegood.getfoodname());
        tv_detaildes.setText(thegood.getfoodname());

        tv_detailclass.setText(String.valueOf(thegood.getId()));
        tv_detailprice.setText("超值会员价 ￥"+String.valueOf(thegood.getplatformprice()));

            String url=thegood.getimageurl();
            System.out.println("我在detail执行图片下载+  这是图片的url"+url);
            ImageLoader.getInstance().displayImage(url,iv_img,options); //选项加载
            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                   thegood.setBuycount(i1);
                    tv_showbuycount.setText("共选择"+thegood.getBuycount()+"件");
                    tv_totalprice.setText("共计：￥"+String.valueOf(thegood.getBuycount()*thegood.getplatformprice()));
                    thegood.setTotalprice(thegood.getBuycount()*thegood.getplatformprice());
                }
            });
        shopcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"成功加入购物车",Toast.LENGTH_SHORT).show();
                int index=DataShare.isExistGoods(thegood.getId());
                if(index!=-1)
                {
                    DataShare.shoplist.get(index).setBuycount(DataShare.shoplist.get(index).getBuycount()+1);
                }
                else{
                    DataShare.shoplist.add(thegood);
                }

            }
        });

    }
    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.chengzi) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.drawable.tmpdir) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.panda) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
            .build();
    //将用户评价的string字符串转化成listview可以展示的evalist
    private List<Evaluation> getEvalist(String evajson)
    {
        List<Evaluation> evalist = new ArrayList<Evaluation>();
        String [] tmp=evajson.split("SOF" );
        for(int i=0;i<tmp.length;i++)
        {
            String [] tmp2=tmp[i].split("EOF");
            for(int j=0;j<tmp2.length;j++)
            {
                System.out.println("第"+i+"条评论："+tmp2[j]);
            }
            Evaluation eva=new Evaluation();
            eva.setUserid(tmp2[0].toString());
            eva.setTime(tmp2[1].toString());
            eva.setEva_content(tmp2[2].toString());
            eva.setStars(tmp2[3].toString());
            evalist.add(eva);
        }
        return evalist;
    }
    private void InitEvaList()
    {
        final ScrollView scollview=(ScrollView) findViewById(R.id.detail_scollview) ;

        ListView eva_list=(ListView) findViewById(R.id.eva_list);
        Eva_Adapter myEvaAdapter=new Eva_Adapter(context,getEvalist(thegood.getEvaluation()));
        eva_list.setAdapter(myEvaAdapter);
        eva_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scollview.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

}
