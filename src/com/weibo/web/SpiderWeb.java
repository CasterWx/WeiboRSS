package com.weibo.web;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author CasterWx  AntzUhl
 * @site https://github.com/CasterWx
 * @company Henu
 * @create 2019-01-24-19:46
 */

public class SpiderWeb {
    public static RandomAccessFile randomAccessFile = null ;
    public static HashMap<String,WebBean> hashMap = new HashMap<>();
    public static String getWebViewer(String u) throws Exception{
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault() ;
        HttpGet httpPost = new HttpGet(u);
        CloseableHttpResponse response=closeableHttpClient.execute(httpPost);
        HttpEntity entity=response.getEntity();
        String ux = EntityUtils.toString(entity,"utf-8") ;
        return ux;
    }

    public static void userId(String id) {
        WebBean webBean = null ;
        if (randomAccessFile==null){
            try {
                randomAccessFile = new RandomAccessFile("outlog.txt","rw");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            String out = getWebViewer("https://rsshub.app/weibo/user2/"+id);
//            System.out.println(out);
            String con = "<title>(.*?)</title>" ;
            Pattern ah = Pattern.compile(con);
            Matcher mr = ah.matcher(out) ;
            if (mr.find()) {
                String user = mr.group().replace("<title><![CDATA[","").replace("]]></title>","") ;
                System.out.println(user);
            }
            out = out.substring(out.indexOf("<item>"));
//            System.out.println(out);
            String title = "<title>(.*?)</title>" ;
            String time = "<pubDate>(.*?)</pubDate>";
            String desc = "<description>(.*?)</description>";
            String url = "<guid>(.*?)</guid>" ;
            Pattern titleAh = Pattern.compile(title);
            Pattern timeAh = Pattern.compile(time);
            Pattern descAh = Pattern.compile(desc);
            Pattern urlAh = Pattern.compile(url) ;
            Matcher titleMr = titleAh.matcher(out) ;
            Matcher timeMr = timeAh.matcher(out) ;
            Matcher descMr = descAh.matcher(out) ;
            Matcher urlMr = urlAh.matcher(out);
            while (titleMr.find()&timeMr.find()&descMr.find()&urlMr.find()) {
                webBean = new WebBean();
                String tit = titleMr.group().replace("<title><![CDATA[","").replace("]]></title>","");
                webBean.setTitle(tit);
                String des = descMr.group().replace("<description><![CDATA[","").replace("]]></description>","");
                webBean.setDesc(des);
                String tim = timeMr.group().replace("<pubDate>","").replace("</pubDate>","");
                SimpleDateFormat sf = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss zzz", Locale.ENGLISH);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String dateTime = "";
                try {
                    date = sf.parse(tim);
                    dateTime = sdf.format(date);
                }catch (ParseException e) {
                    e.printStackTrace();
                }
                webBean.setTime(dateTime);
                String ur = urlMr.group().replace("<guid>","").replace("</guid>","");
                webBean.setUrl(ur);
                randomAccessFile.seek(randomAccessFile.length());
                randomAccessFile.write(webBean.toString().getBytes());
                hashMap.put(ur,webBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void putMap() {
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            WebBean value = hashMap.get(key);
            System.out.println(key + " ： " + value.toString());
        }

    }

    public static void init(String id){
        userId(id);
        putMap();
        if (randomAccessFile==null){
            try {
                randomAccessFile = new RandomAccessFile("outlog.txt","rw");
                randomAccessFile.seek(randomAccessFile.length());
                randomAccessFile.write("初始化完成...\r\n".getBytes());
                randomAccessFile.write("--------------------------\r\n".getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
