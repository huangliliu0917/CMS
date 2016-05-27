package com.huotu.widget.model;


/**
 * Created by hzbc on 2016/5/27.
 */

/**
 * �ӿڷ��ؽ������ģ��,������
 * <ul>
 *     <li>��Ӧ��-code</li>
 *     <li>��Ӧ��Ϣ-msg</li>
 *     <li>��Ӧ��������-data</li>
 * </ul>
 */
public class ResultModel {
    private final Integer code;
    private final String msg;
    private final Object data;

    public  ResultModel(int code,String msg,Object data)
    {
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

}
