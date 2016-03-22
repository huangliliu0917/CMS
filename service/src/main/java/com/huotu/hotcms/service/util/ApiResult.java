/*
 * ��Ȩ����:���ݻ�ͼ�Ƽ����޹�˾
 * ��ַ:�㽭ʡ�����б��������˽ֵ���İ·�ǻ�E��B��4¥
 *
 *  (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District 2013-2015. All rights reserved.
 */

package com.huotu.hotcms.service.util;

/**
 * �ӿڷ���ʵ��
 * Created by liual on 2015-09-21.
 */
public class ApiResult<T> {

    /**
     * ���ؽ������
     */
    private String msg;

    private int code;

    /**
     * ��������
     */
    private T data;

    public ApiResult() {
        super();
    }

    public ApiResult(String msg,int code) {
        this.msg = msg;
        this.code = code;
    }

    public ApiResult(T obj) {
        this.data = obj;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
