/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.service.common;

/**
 * 页面布局模版枚举--对应页面布局模版(LayoutEnum)
 * */
public enum LayoutTemplate implements CommonEnum {
    THREE_COLUMN_LAYOUT_190x590x190(0, "<div class=\"layout-area HOT-layout-area js-layout js-hot-layout\" id=\"{layoutId}\">\n" +
            "    <div class=\"layout HOT-layout layout-box fn-clear\" >\n" +
            "      <div class=\"layout-three-left w190 bottomes\" name=\"left\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-three-middle w590\" name=\"middle\">\n" +
            "       <div class=\"layout-tool HOT-tool bottomes\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-three-right w190 bottomes\" name=\"right\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>%s" +
            "    </div>\n" +
            "  </div>"),
    WITHOUT_COLUMN_LAYOUT_990(1,"<div class=\"layout-area HOT-layout-area js-layout js-hot-layout\"  id=\"{layoutId}\">\n" +
            "    <div class=\"layout HOT-layout layout-box fn-clear\">\n" +
            "      <div class=\"layout-one w990 bottomes\" name=\"main\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>%s" +
            "    </div>\n" +
            "  </div>"),
    LEFT_RIGHT_COLUMN_LAYOUT_190x790(2,"<div class=\"layout-area HOT-layout-area js-layout js-hot-layout\"  id=\"{layoutId}\">\n" +
            "    <div class=\"layout HOT-layout layout-box fn-clear\" >\n" +
            "      <div class=\"layout-two-left w190 bottomes\" name=\"left\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-two-right w790 bottomes \" name=\"right-extra\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>%s" +
            "    </div>\n" +
            "  </div>"),
    RIGHT_PART_LAYOUT_190x390x390(3,"<div class=\"layout-area HOT-layout-area js-layout js-hot-layout\"  id=\"{layoutId}\">\n" +
            "    <div class=\"layout HOT-layout layout-box fn-clear\">\n" +
            "      <div class=\"layout-three-left w190 bottomes\" name=\"left\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-three-middle w390 bottomes\" name=\"middle-m\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-three-right w390 bottomes\" name=\"middle-r\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>%s" +
            "    </div>\n" +
            "  </div>"),
    LEFT_RIGHT_COLUMN_LAYOUT_790x190(4,"<div class=\"layout-area HOT-layout-area js-layout js-hot-layout\"  id=\"{layoutId}\">\n" +
            "    <div class=\"layout HOT-layout layout-box fn-clear\">\n" +
            "      <div class=\"layout-two-left w790 bottomes\" name=\"left\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-two-right w190 bottomes\" name=\"right-extra\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>%s" +
            "    </div>\n" +
            "  </div>"),
    LEFT_PART_LAYOUT_390x390x190(5,"<div class=\"layouttarea HOT-layout-area js-layout js-hot-layout\"  id=\"{layoutId}\">\n" +
            "    <div class=\"layout HOT-layout layout-box fn-clear\">\n" +
            "      <div class=\"layout-three-left w390 bottomes\" name=\"left\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-three-middle w390 bottomes\" name=\"middle-m\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-three-right w190 bottomes\" name=\"middle-r\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>%s" +
            "    </div>\n" +
            "  </div>"),
    THREE_COLUMN_LAYOUT_254x717x239(6, "<div class=\"layout-area HOT-layout-area js-layout js-hot-layout\"  id=\"{layoutId}\">\n" +
            "    <div class=\"layout HOT-layout layout-box fn-clear\" style=\"width:1210px;\">\n" +
            "      <div class=\"layout-three-left bottomes\" name=\"left\" style=\"width:254px;\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-three-middle bottomes\" name=\"middle\" style=\"width:717px;margin-left:0;\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-three-right bottomes\" name=\"right\" style=\"width:239px;margin-left:0;\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>%s" +
            "    </div>\n" +
            "  </div>"),
    LEFT_RIGHT_COLUMN_LAYOUT_254x956(7,"<div class=\"layout-area HOT-layout-area js-layout js-hot-layout\"  id=\"{layoutId}\">\n" +
            "    <div class=\"layout HOT-layout layout-box fn-clear\" style=\"width:1210px;\">\n" +
            "      <div class=\"layout-two-left bottomes\" name=\"left\" style=\"width:254px;margin:0;\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-two-right bottomes\" name=\"right-extra\" style=\"width:956px;margin:0;\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>%s" +
            "    </div>\n" +
            "  </div>"),
    LEFT_RIGHT_COLUMN_LAYOUT_272x718(8,"<div class=\"layout-area HOT-layout-area js-layout js-hot-layout\" id=\"{layoutId}\">\n" +
            "    <div class=\"layout HOT-layout layout-box fn-clear\">\n" +
            "      <div class=\"layout-two-left bottomes\" name=\"left\" style=\"width:272px;margin:0;\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-two-right bottomes\" name=\"right-extra\" style=\"width:718px;margin:0;\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>%s" +
            "    </div>\n" +
            "  </div>"),
    LEFT_RIGHT_COLUMN_LAYOUT_215x765(9,"<div class=\"layout-area HOT-layout-area js-layout js-hot-layout\" id=\"{layoutId}\">\n" +
            "    <div class=\"layout HOT-layout layout-box fn-clear\">\n" +
            "      <div class=\"layout-two-left w215 bottomes\" name=\"left\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-two-right w765 bottomes\" name=\"right\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>%s" +
            "    </div>\n" +
            "  </div>"),
    LEFT_RIGHT_COLUMN_LAYOUT_330x650(10,"<div class=\"layout-area HOT-layout-area js-layout js-hot-layout\" id=\"{layoutId}\">\n" +
            "    <div class=\"layout HOT-layout layout-box fn-clear\">\n" +
            "      <div class=\"layout-two-left bottomes\" style=\"width:330px;\" name=\"left\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-two-right bottomes\" style=\"width:650px;\" name=\"right-extra\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>%s" +
            "    </div>\n" +
            "  </div>"),
    LEFT_RIGHT_COLUMN_LAYOUT_650x330(11,"<div class=\"layout-area HOT-layout-area js-layout js-hot-layout\" id=\"{layoutId}\">\n" +
            "    <div class=\"layout HOT-layout layout-box fn-clear\">\n" +
            "      <div class=\"layout-two-left bottomes\" style=\"width:650px;\" name=\"left\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-two-right bottomes\" style=\"width:330px;\" name=\"right-extra\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>%s" +
            "    </div>\n" +
            "  </div>"),
    LEFT_RIGHT_PART_LAYOUT_490x490(12,"<div class=\"layout-area HOT-layout-area js-layout js-hot-layout\" id=\"{layoutId}\">\n" +
            "    <div class=\"layout HOT-layout layout-box fn-clear\">\n" +
            "      <div class=\"layout-two-left w490 bottomes\" name=\"left\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-two-right w490 bottomes\" name=\"right-extra\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>%s" +
            "    </div>\n" +
            "  </div>"),
    LEFT_CENTER_RIGHT_PART_LAYOUT_323x324x323(13,"<div class=\"layout-area HOT-layout-area js-layout js-hot-layout\" id=\"{layoutId}\">\n" +
            "    <div class=\"layout HOT-layout layout-box fn-clear\">\n" +
            "      <div class=\"layout-three-left w323 bottomes\" name=\"left\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-three-middle w324 bottomes\" name=\"middle-m\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>\n" +
            "      <div class=\"layout-three-right w323 bottomes\" name=\"middle-r\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>%s" +
            "    </div>\n" +
            "  </div>"),
    WITHOUT_COLUMN_LAYOUT_99999(14, "<div class=\"layout-area HOT-layout-area js-layout js-hot-layout\" id=\"{layoutId}\">\n" +
            "    <div class=\"layout layout-auto HOT-layout layout-box fn-clear\">\n" +
            "      <div class=\"layout-one layout-hover bottomes\" name=\"main\">\n" +
            "       <div class=\"layout-tool HOT-tool\">%s" +
            "        </div>" +
            "      </div>%s" +
            "    </div>\n" +
            "  </div>");

    private int code;
    private String value;
    LayoutTemplate(int code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public final Integer getCode() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.value;
    }

}
