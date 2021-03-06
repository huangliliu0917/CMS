///*
// * 版权所有:杭州火图科技有限公司
// * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
// *
// *  (c) Copyright Hangzhou Hot Technology Co., Ltd.
// *  Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District 2013-2015. All rights reserved.
// */
//
//package com.huotu.hotcms.service.thymeleaf.expression;
//import com.huotu.hotcms.service.thymeleaf.common.DialectHtml5AttrEnum;
//import com.huotu.hotcms.service.thymeleaf.model.ForeachDialectModel;
////import org.thymeleaf.model.IElementAttributes;
//import org.thymeleaf.model.IProcessableElementTag;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * <P>
// *     Thymeleaf 自定义方言循环html5参数解析
// * </P>
// * @since 1.0.0
// *
// * @author xhl
// */
//public class ForeachDialectAttributeFactory implements IDialectAttributeFactory {
//    private static ForeachDialectAttributeFactory instance=new ForeachDialectAttributeFactory();
//
//    /*
//    * 单例出口
//    * */
//    public static ForeachDialectAttributeFactory getInstance(){
//        if(instance==null)
//            instance=new ForeachDialectAttributeFactory();
//        return  instance;
//    }
//
//    @Override
//    public String getHtml5Attr(HttpServletRequest request,IProcessableElementTag elementTag, String name) {
////        if(elementTag!=null)
////        {
////            return elementTag.getAttributes().getValue(name);
////        }
//        return null;
//    }
//
//
//    @Override
//    public ForeachDialectModel getHtml5Attr(HttpServletRequest request,IProcessableElementTag elementTag) {
//        ForeachDialectModel model=null;
//        if(elementTag!=null)
//        {
//            model=new ForeachDialectModel();
////            IElementAttributes attributes=elementTag.getAttributes();
////            if(attributes!=null) {
////                Map<String,String> params = new HashMap<>();
//////                for(String s: ParamEnum.ARTICLE.getForeachParams()) {
//////                    String key = s;
//////                    s = attributes.getValue(ParamEnum.PARAM_PREFIX,s);
//////                    params.put(key,s);
//////                }
////                String id=attributes.getValue(DialectHtml5AttrEnum.DATA_PARAM_ID.getValue().toString());
////                String ignoreID=attributes.getValue(DialectHtml5AttrEnum.DATA_PARAM_EXCLUDEID.getValue().toString());
////                String size=attributes.getValue(DialectHtml5AttrEnum.DATA_PARAM_SIZE.getValue().toString());
////                String siteId=attributes.getValue(DialectHtml5AttrEnum.DATA_PARAM_SITEID.getValue().toString());
////                model.setIgnoreId(ignoreID);
////                if(size!=null&&size!="") {
////                    model.setSize(Integer.parseInt(size));
////                }
////                if(id!=null&&id!="") {
////                    model.setId(Long.parseLong(id));
////                }
////                if(siteId!=null&&siteId!="") {
////                    model.setSiteId(Long.parseLong(siteId));
////                }
////            }
//        }
//        return model;
//    }
//}
