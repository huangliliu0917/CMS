/*
 * ��Ȩ����:���ݻ�ͼ�Ƽ����޹�˾
 * ��ַ:�㽭ʡ�����б��������˽ֵ���İ·�ǻ�E��B��4¥
 *
 *  (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District 2013-2015. All rights reserved.
 */

package com.huotu.hotcms.service.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by liual on 2015-10-19.
 */
public class SignBuilder {
    /**
     * ����һ��signǩ��
     * ����ֵΪ�յ�
     *
     * @param params ��ǩ��������key�����map
     * @param prefix ǩ��ǰ׺
     * @param suffix ǩ����׺
     * @return ���ؼ�Ȩ��Ϣ�ַ���
     */
    public static String buildSignIgnoreEmpty(Map<String, Object> params, String prefix, String suffix) throws UnsupportedEncodingException {
        if (prefix == null)
            prefix = "";
        if (suffix == null)
            suffix = "";
        StringBuilder stringBuilder = new StringBuilder(prefix);
        Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            if (!StringUtils.isEmpty(next.getValue())) {
                stringBuilder.append(next.getKey()).append(String.valueOf(next.getValue()));
            }
        }
        stringBuilder.append(suffix);
        return DigestUtils.md5Hex(stringBuilder.toString().getBytes("utf-8"));
    }

    /**
     * ����һ��signǩ��
     * ������ֵΪ�յ�
     *
     * @param params ��ǩ��������key�����map
     * @param prefix ǩ��ǰ׺
     * @param suffix ǩ����׺
     * @return ���ؼ�Ȩ��Ϣ�ַ���
     */
    public static String buildSign(Map<String, String> params, String prefix, String suffix) throws UnsupportedEncodingException {
        if (prefix == null)
            prefix = "";
        if (suffix == null)
            suffix = "";
        StringBuilder stringBuilder = new StringBuilder(prefix);
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            stringBuilder.append(next.getKey()).append(String.valueOf(next.getValue()));
        }
        stringBuilder.append(suffix);
        return DigestUtils.md5Hex(stringBuilder.toString().getBytes("utf-8"));
    }
}
