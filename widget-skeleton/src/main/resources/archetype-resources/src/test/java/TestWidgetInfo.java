/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package $

import java.io.IOException;
import java.util.function.Function;{package};

import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import com.huotu.widget.test.WidgetTest;
import org.openqa.selenium.WebElement;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
/**
 * @author CJ
 */
public class TestWidgetInfo extends WidgetTest {

    @Override
    protected boolean printPageSource() {
        return false;
    }

    @Override
    protected void editorWork(Widget widget, WebElement editor, Supplier<Map<String, Object>> currentWidgetProperties) {
        Map map = currentWidgetProperties.get();
        //todo
    }

    @Override
    protected void browseWork(Widget widget, WidgetStyle style, Function<ComponentProperties, WebElement> uiChanger) {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement webElement = uiChanger.apply(properties);

    }

    @Override
    protected void editorBrowseWork(Widget widget, Function<ComponentProperties, WebElement> uiChanger) throws IOException {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement webElement = uiChanger.apply(properties);

    }
}
