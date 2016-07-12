/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.cms.manage.page.support;

import org.assertj.core.api.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/**
 * 具备一个添加form和一个展示数据列表的div
 *
 * @param <T> 相关资源类型
 * @author CJ
 */
public abstract class AbstractCRUDPage<T> extends AbstractContentPage {

    protected WebElement form;
    private WebElement body;

    /**
     * @param bodyId    body的id
     * @param formId    form的id
     * @param webDriver driver
     */
    protected AbstractCRUDPage(String bodyId, String formId, WebDriver webDriver) {
        super(webDriver);
        body = webDriver.findElement(By.id(bodyId));
        form = webDriver.findElement(By.id(formId));
    }

    @Override
    public void validatePage() {
        normalValid();
    }

    @Override
    public WebElement getBody() {
        return body;
    }

    /**
     * 添加一个资源,并且提交表单
     *
     * @param value              数值
     * @param otherDataSubmitter 作为额外输入字段的辅助,可以添加一个消耗者
     * @param <X>                返回的页面类型
     * @return 添加以后的页面
     */
    public <X extends AbstractCRUDPage<T>> X addEntityAndSubmit(T value
            , BiConsumer<AbstractCRUDPage<T>, WebElement> otherDataSubmitter) {
        fillValueToForm(value);
        if (otherDataSubmitter != null) {
            otherDataSubmitter.accept(this, form);
        }
        form.findElement(By.className("btn-primary")).click();
        return (X) initPage(getClass());
    }

    /**
     * 将数值表述在表单中
     *
     * @param value 资源值
     */
    protected abstract void fillValueToForm(T value);

    /**
     * 子类可以替换该实现。
     *
     * @return 所有数据row, 规则是一个资源必然跟一个row配对。
     */
    public List<WebElement> listTableRows() {
        beforeDriver();
        // //*[@id="DataTables_Table_0"]/tbody/tr[1]
        return body.findElements(By.cssSelector("tbody>tr"));
    }

    /**
     * 说了是寻找,其实是从{@link #listTableRows()}结果里面找一个符合参数的谓语
     *
     * @param value 目标资源
     * @return 可以判定元素描述的是目标资源的谓语
     */
    public abstract Predicate<? super WebElement> findRow(T value);

    /**
     * 校验这个来自{@link #listTableRows()}数据row完全符合value的数据
     *
     * @param value 目标资源
     * @return 可以判定袁术描述的是'完全'符合目标资源的谓语
     */
    protected abstract Predicate<WebElement> rowPredicate(T value);

    /**
     * 校验这个来自{@link #listTableRows()}数据row完全符合value的数据
     *
     * @param value 目标资源
     * @return 一个校验器
     */
    public Condition<? super WebElement> rowCondition(T value) {
        return new Condition<>(rowPredicate(value), "此元素不符合" + value);
    }


}
