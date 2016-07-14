/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.cms.manage.page;

import com.huotu.cms.manage.page.support.AbstractContentPage;
import com.huotu.cms.manage.page.support.AbstractFrameParentPage;
import com.huotu.cms.manage.page.support.BodyId;
import com.huotu.hotcms.service.entity.login.Owner;
import org.openqa.selenium.WebDriver;
import org.springframework.core.annotation.AnnotationUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 管理员登录所看到的
 *
 * @author CJ
 */
public class AdminPage extends AbstractFrameParentPage {
    public AdminPage(WebDriver webDriver) {
        super(webDriver);
    }


    @Override
    public void validatePage() {
        assertThat(webDriver.getTitle())
                .contains("后台管理");
    }

    /**
     * 去指定页面
     *
     * @param pageClazz 页面的类型
     * @param <T>       类型参数
     * @return 新页面实例
     */
    public <T extends AbstractContentPage> T toPage(Class<? extends T> pageClazz) {
        beforeDriver();
        try {
            clickMenuByClass(AnnotationUtils.findAnnotation(pageClazz, BodyId.class).value());
        } catch (NullPointerException ex) {
            throw new IllegalStateException("必须标注BodyId 否则找不到相对的链接:" + pageClazz);
        }
        T page = initPage(pageClazz);
        page.setAdminPage(this);
        return page;
    }

    public ManageMainPage toMainPage(Owner owner) {
        beforeDriver();
        webDriver.get("http://localhost/manage/supper/as/" + owner.getId());

        return initPage(ManageMainPage.class);
    }

    public OwnerPage toOwner() {
        beforeDriver();
        clickMenuByClass("fa-home");
        return initPage(OwnerPage.class);
    }

    public WidgetPage toWidget() {
        beforeDriver();
        clickMenuByClass("fa-asterisk");
        return initPage(WidgetPage.class);
    }


}
