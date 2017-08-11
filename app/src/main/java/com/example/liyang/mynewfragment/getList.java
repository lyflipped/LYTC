package com.example.liyang.mynewfragment;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyang on 2017/4/14.
 */

public class getList {
    private  List<GOODSLIST> showlist ;
    private List<Map<String,Object>> goodsdatalist;
    public List<GOODSLIST> getNewtestlist() {
        showlist =new ArrayList<GOODSLIST>();
        //newtest.clear();

        AVQuery<AVObject> avQuery = new AVQuery<>("GoodsDetail");
        avQuery.whereEqualTo("user", AVUser.getCurrentUser());
        avQuery.include("user");
        avQuery.orderByDescending("createAt");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                // object 就是 id 为 558e20cbe4b060308e3eb36c 的 Todo 对象实例

                if (objects != null && objects.size() > 0) {
                    System.out.println("从数据库得到的数据个数：" + objects.size());

                    for (AVObject object : objects) {
                        AVUser user = object.getAVUser("user");
                        AVFile pic = object.getAVFile("pic");
                        String des = object.getString("des");
                        System.out.println("des=" + des);
                        String price = object.getString("price");
                        // System.out.println("price="+price);
                        String type = object.getString("type");
                        // System.out.println("type="+type);
                        GOODSLIST g1 = new GOODSLIST(des, type, price, pic);
                        System.out.println("g1中的des=" + g1.getGoodsdes());
                        showlist.add(g1);
                        //System.out.println("tmp的count：" + tmp.size());

                        //System.out.println("newtest中的des="+newtest.isEmpty());

                    }
                    //Collections.reverse(newtest)
                    System.out.println("return之前newtest的count：" + showlist.size());

                }
                System.out.println("跳出if的count：" + showlist.size());
            }


        });
        System.out.println("newtest的count：" + showlist.size());

        return showlist;

    }
    public List<Map<String,Object>> getGoodslist()
    {
        goodsdatalist=new ArrayList<Map<String, Object>>();

        Map<String,Object>map=new HashMap<String, Object>();
        map.put("pic",R.drawable.buou);
        map.put("name","创意布偶杯");
        map.put("num","1");
        map.put("price","￥100");
        goodsdatalist.add(map);

        Map<String,Object> map1=new HashMap<String, Object>();
        map1.put("pic",R.drawable.kafeiji);
        map1.put("name","创意咖啡机");
        map1.put("num","1");
        map1.put("price","￥100000");
        goodsdatalist.add(map1);


        Map<String,Object>map2=new HashMap<String, Object>();
        map2.put("pic",R.drawable.pixie);
        map2.put("name","名牌皮鞋");
        map2.put("num","1");
        map2.put("price","￥1000");
        goodsdatalist.add(map2);



        return goodsdatalist;
    }
  public  List<GOODSLIST> getshowlist()
  {
      return this.showlist;
  }
}
