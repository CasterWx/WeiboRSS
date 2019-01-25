package com.weibo.web;

/**
 * @author CasterWx  AntzUhl
 * @site https://github.com/CasterWx
 * @company Henu
 * @create 2019-01-24-20:36
 */
public class WebBean {
    // ����
    private String title;
    private String time ;
    private String desc ;
    private String url ;

    @Override
    public String toString() {
        return " WebBean{" +
                "����='" + title + '\'' +
                ", ʱ��='" + time + '\'' +
                ", ժҪ='" + desc + '\'' +
                ", ����='" + url + '\'' +
                "}\r\n".replace(" ","");
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getDesc() {
        return desc;
    }

    public String getUrl() {
        return url;
    }
}
