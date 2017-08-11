package entity;

/**
 * Created by liyang on 2017/4/21.
 */

public class User {
    private String Id;
    private String uid;
    private String pwd;
    public void setId(String id)
    {
        this.Id=id;
    }
    public void setuid(String uid)
    {
        this.uid=uid;
    }
    public void setpwd(String pwd)
    {
        this.pwd=pwd;
    }
    public String getId()
    {
        return Id;
    }
    public String getuid()
    {
        return uid;
    }
    public String getpwd()
    {
        return pwd;
    }
}
