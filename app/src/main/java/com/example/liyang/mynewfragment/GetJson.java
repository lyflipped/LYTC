package com.example.liyang.mynewfragment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/**
 * Created by liyang on 2017/4/11.
 */

public class GetJson {
    private static String urlPath = Constant.URL_jptjlist;

    /**
     * @throws IOException
     */
    public static String getstrjson() {
        //ServerFactory.getServer(8080).start();
        //列出原始数据

            StringBuilder json = new StringBuilder();

        try{
            URL oracle = new URL(GetJson.urlPath);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
            String Strjson = json.toString();
            System.out.println("原始数据:");
            System.out.println(Strjson.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();


    }
}
