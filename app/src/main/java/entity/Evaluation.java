package entity;

/**
 * Created by liyang on 2017/5/2.
 */

public class Evaluation {
    private String userid;
    private  String time;
    private String eva_content;
    private String Stars;
    public void setUserid(String userid)
    {
        this.userid=userid;
    }
    public void setTime(String time)
    {
        this.time=time;
    }
    public void setEva_content(String content)
    {
        this.eva_content=content;
    }
    public void setStars(String stars)

    {
        this.Stars=stars;
    }
    public String getUserid()
    {
        return userid;
    }
    public String getTime()
    {
        return time;
    }
    public String getEva_content()
    {
        return eva_content;
    }
    public String getStars()
    {
        return Stars;
    }

}
