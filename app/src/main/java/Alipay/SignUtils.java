package Alipay;

/**
 * Created by liyang on 2017/5/27.
 */

public class SignUtils {
    //这里一般讲RSA_PRIVATE放在服务器，所以这里的sign函数应该是讲order发到服务器然后得到sign的结果。
    public static String sign(String order,String RSA_PRIVATE)
    {
        return order;
    }

}
