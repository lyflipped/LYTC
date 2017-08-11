package Alipay;

/**
 * Created by liyang on 2017/5/27.
 */

public class PayResult {
    //这里需要和服务器通信，得到验证结果
    private String result;
    private String status;
    public  PayResult(String obj)
    {
        this.result=obj;
    }
    public String getResult()
    {
        return result;
    }
    public String getResultStatus()
    {
        return status;
    }


}
