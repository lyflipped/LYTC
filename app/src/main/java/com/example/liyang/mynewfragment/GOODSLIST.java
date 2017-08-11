package com.example.liyang.mynewfragment;

import com.avos.avoscloud.AVFile;

import java.io.Serializable;

/**
 * Created by liyang on 2017/4/12.
 */

public  class GOODSLIST {
    private String goodsprice;
    private String goodsdes;
    private String gooodstype;
    private AVFile picture;
    public GOODSLIST(String s1,String s2,String s3,AVFile pic1)
    {
       this.goodsdes=s1;
        this.gooodstype=s2;
        this.goodsprice=s3;
        this.picture=pic1;
    }
    public void setGoodsprice(String s)
    {
        goodsprice=s;
    }
    public void setGoodsdes(String s)
    {
        goodsdes=s;
    }
    public void setPicture(AVFile pic)
    {
        picture=pic;

    }
    public void setGooodstype(String s)
    {
        gooodstype=s;
    }
    public String getGoodsprice()
    {
        return  goodsprice;
    }
    public String getGoodsdes()
    {
        return goodsdes;
    }
    public String getGooodstype()
    {
        return gooodstype;
    }
    public AVFile getPicture()
    {
        return  picture;
    }

}
