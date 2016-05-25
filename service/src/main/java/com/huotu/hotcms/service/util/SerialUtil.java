package com.huotu.hotcms.service.util;

/**
 * Created by hzbc on 2016/5/18.
 */

import com.huotu.hotcms.service.entity.Site;

import java.util.Date;

/**
 * Serial 生成类
 */
public class SerialUtil {

    /**
     *
     * @param serial
     * @return
     */
    public static String formartSerial(String serial){
        return "site_"+serial+"_serial";
    }

    /**
     * 根绝站点生成 可以识别站点而且是唯一存在的serial
     * @param site 相应的站点
     * @return 生成的serial
     */
    public static String formartSerial(Site site){
        return "site_"+site.getSiteId()+"_serial_"+new Date().getTime();
    }

    /**
     * 根据序列号解析出站点ID
     * @param serial 序列号
     * @return  站点ID
     */
    public long convert2SiteId(String serial){
       return Long.valueOf(serial.split("_")[1]);
    }
}
