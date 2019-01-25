package com.weibo.web;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author CasterWx  AntzUhl
 * @site https://github.com/CasterWx
 * @company Henu
 * @create 2019-01-24-22:12
 */

// 实时监控
public class FindWeiBo implements Runnable{
    public String userId ;
    public String username ;
    public FindWeiBo(String userId){
        this.userId = userId ;
    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile("outlog.txt","rw");
                randomAccessFile.seek(randomAccessFile.length());
                randomAccessFile.write(("正在监控【"+username+"】用户.\r\n").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("正在监控【"+username+"】用户.");
            userId(userId);
        }
    }

    public void userId(String id) {
        WebBean webBean = null ;
        try {
            String out = SpiderWeb.getWebViewer("https://rsshub.app/weibo/user2/"+id);
//            System.out.println(out);
            String con = "<title>(.*?)</title>" ;
            Pattern ah = Pattern.compile(con);
            Matcher mr = ah.matcher(out) ;
            if (mr.find()) {
                String user = mr.group().replace("<title><![CDATA[","").replace("]]></title>","") ;
                username = user ;
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
                if (SpiderWeb.hashMap.size()!=0&&!SpiderWeb.hashMap.containsKey(ur)){
                    System.out.println("新增微博: "+webBean.toString());
                    SendQQMailUtil.Send("1325200471@qq.com","antzuhl1998@gmail.com","digwcnzqlfjghjhg",webBean);
                    SpiderWeb.hashMap.put(ur,webBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
