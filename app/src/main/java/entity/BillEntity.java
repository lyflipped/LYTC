package entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyang on 2017/4/21.
 */

public class BillEntity implements Serializable {
    private int Id;
    private String uid;
    private String gids;
    private String gnums;
    private int status;
    private String btime;
    private String btype;
    private int ctime;
    private String address;
    private float totalprice;
    private  String paymoney;
    private List<Goods> glist;
    public void setId(int id)
    {
        this.Id=id;
    }
    public void setUid(String uid)
    {
        this.uid=uid;

    }
    public void setPaymoney(String paymoney)
    {
        this.paymoney=paymoney;
    }
    public void setGids(String gids)
    {
        this.gids=gids;
    }
    public void setGnums(String gnums)
    {
        this.gnums=gnums;
    }
    public void setStatus(int status)
    {
        this.status=status;
    }
    public void setBtime(String btime)
    {
        this.btime=btime;
    }
    public void setBtype(String btype)
    {
        this.btype=btype;
    }
    public void setCtime(int time)
    {
        this.ctime=time;
    }
    public void setAddress(String address)
    {
        this.address=address;
    }
    public void setGlist(List<Goods> glist)
    {
        this.glist=glist;
    }
  public void setTotalprice(float totalprice)
    {
        this.totalprice=totalprice;
    }
    public int getId()
    {
        return Id;
    }

    public String getUid()
    {
        return uid;
    }
    public String getGids()
    {
        return gids;
    }
    public String getGnums()
    {
        return gnums;
    }
    public int getStatus()
    {
        return status;
    }
    public String getBtime()
    {
        return btime;
    }
    public String getBtype()
    {
        return btype;
    }
    public int getCtime()
    {
        return ctime;
    }
    public String getAddress()
    {
        return address;
    }
    public  String getPaymoney()
    {
        return paymoney;
    }

    public	List<Goods> getGlist()
    {
        return glist;
    }
    public float getTotalprice()
    {
        return totalprice;
    }



}
