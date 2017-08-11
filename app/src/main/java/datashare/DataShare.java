package datashare;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entity.BillEntity;
import entity.Goods;
import entity.User;

/**
 * Created by liyang on 2017/4/21.
 */

public class DataShare {
    public static User user=new User();
    public static String ip_app="http://10.210.32.226:8080/ServletForApp/";
    public static List<Goods> shoplist =new ArrayList<Goods>();
    public  static List<Goods> wantlist=new ArrayList<Goods>();
    public  static  List<BillEntity> billlist=new ArrayList<BillEntity>();
    public static List<Map<String,Object>> DataList=new ArrayList<Map<String, Object>>();
    public static List<Object> eva_success=new ArrayList<Object>();
    //判断是否添加过该商品，若如果返回-1就没有添加，否则已经添加过该商品
    public  static int isExistGoods(int id)
    {
        for(int i=0;i<shoplist.size();i++)
        {
            if(shoplist.get(i).getId()==id)
            {
                return i;
            }
        }
        return -1;
    }
    public static float getshoplistTotalMoney()
    {
        float money=0.0f;
        for(int i=0;i<shoplist.size();i++)
        {
            money=money+shoplist.get(i).getplatformprice()*shoplist.get(i).getBuycount();
        }
        return money;
    }
    //判断是否加入了喜欢列表
    public  static int isExistwantGoods(int id)
    {
        for(int i=0;i<wantlist.size();i++)
        {
            if(wantlist.get(i).getId()==id)
            {
                return i;
            }
        }
        return -1;
    }
}
