package com.huotu.widget.model;

/**
 * Created by hzbc on 2016/5/27.
 */

/**
 * ģ��ѡ��
 */
public enum ResultOptionsEnum {
    SUCCESS(202,"�ɹ�"),
    FAIL(500,"ʧ��");

    private int code;
    private String value;

    ResultOptionsEnum(int code, String value){
        this.code=code;
        this.value=value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
