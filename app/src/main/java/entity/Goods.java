package entity;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liyang on 2017/4/21.
 */

public class Goods implements Serializable{
    private  int id;
    private String foodname;
    private float platformprice;
    private int buycount;
    private String personmoney;
    private String address;
    private String imageurl;
    private String textintroduce;
    private String briefintroduce;
    private float totalprice;
    private String evaluation;
    public void setEvaluation(String eva)
    {
        this.evaluation=eva;
    }

    public void setfoodname(String foodname)
    {
        this.foodname=foodname;
    }
    public void setplatformprice(float platformprice)
    {
        this.platformprice=platformprice;
    }
    public void setpersonmoney(String personmoney)
    {
        this.personmoney=personmoney;
    }
    public void setaddress(String address)
    {
        this.address=address;
    }
    public void setimageurl(String url)
    {
        this.imageurl=url;
    }
    public void settexxtintroduce(String textintroduce)
    {
        this.textintroduce=textintroduce;
    }
    public void setbriefintroduce(String briefintroduce)
    {
        this.briefintroduce=briefintroduce;
    }
    public void setBuycount(int buycount){this.buycount=buycount;}
    public void setTotalprice(float totalprice)
    {
        this.totalprice=totalprice;
    }
    public String getfoodname()
    {
        return foodname;
    }
    public float getplatformprice()
    {
        return platformprice;
    }
    public String getpersonmoney()
    {
        return personmoney;
    }
    public String getaddress()
    {
        return address;
    }

    public int getBuycount() {
        return buycount;
    }
    public void setId(int id)
    {
        this.id=id;
    }

    public String getimageurl()
    {
        return imageurl;
    }
    public String gettextintroduce()
    {
        return textintroduce;
    }
    public String getbriefintroduce()
    {
        return briefintroduce;
    }
    public int getId(){return id;}
    public float getTotalprice()
    {
        return  totalprice;
    }
    public String getEvaluation()
    {

        return evaluation;
    }


}