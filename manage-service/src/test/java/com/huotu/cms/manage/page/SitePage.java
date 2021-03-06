/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.cms.manage.page;

import com.huotu.cms.manage.page.support.AbstractCRUDPage;
import com.huotu.cms.manage.page.support.BodyId;
import com.huotu.hotcms.service.entity.Site;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.api.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author CJ
 */
@BodyId("fa-puzzle-piece")
public class SitePage extends AbstractCRUDPage<Site> {
    private static final Log log = LogFactory.getLog(SitePage.class);

    @FindBy(id = "logo-uploader")
    private WebElement uploader;

    public SitePage(WebDriver webDriver) {
        super("siteForm", webDriver);
    }

    @Override
    protected void howToOpenResource(WebElement webElement) {
        webElement.findElements(By.tagName("button")).stream()
                .filter((button) -> button.getText().contains("编辑"))
                .findAny().orElseThrow(IllegalStateException::new)
                .click();
    }

    public void uploadLogo(String name, Resource resource) throws IOException {
        System.out.println(webDriver.getPageSource());
        Path tempFile = Files.createTempFile(name, name);
        Files.copy(resource.getInputStream(), tempFile, REPLACE_EXISTING);
        tempFile.toFile().deleteOnExit();

        System.out.println(uploader.isDisplayed());
        System.out.println(uploader.findElement(By.className("qq-upload-button")).isDisplayed());

//        StringSelection stringSelection = new StringSelection(tempFile.toAbsolutePath().toString());
//        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        uploader.findElement(By.className("qq-upload-button")).click();

//        Thread.sleep(1000);
//        Robot robot = new Robot();
//        click(robot, KeyEvent.VK_ENTER);
//        robot.keyPress(KeyEvent.VK_CONTROL);
//        robot.keyPress(KeyEvent.VK_V);
//        robot.keyRelease(KeyEvent.VK_CONTROL);
//        robot.keyRelease(KeyEvent.VK_V);
//        click(robot, KeyEvent.VK_ENTER);
//        WebElement uploaderFile = uploader.findElement(By.tagName("input"));
//        uploaderFile.sendKeys(tempFile.toString());
        System.out.println(webDriver.switchTo().activeElement());
        webDriver.switchTo().activeElement().sendKeys(tempFile.toAbsolutePath().toString());
        System.out.println(webDriver.getPageSource());
//        uploader.findElement(By.className("qq-upload-button")).sendKeys(tempFile.toAbsolutePath().toString());
        System.out.println();
    }

    @Override
    protected void fillValueToForm(Site value) {
        WebElement form = getForm();
//        uploadLogo("thumbnail.png",new ClassPathResource("thumbnail.png"));

        inputText(form, "name", value.getName());
        inputText(form, "title", value.getTitle());
        inputText(form, "description", value.getDescription());
        inputText(form, "copyright", value.getCopyright());
        inputTags(form, "keywords", value.getKeywords().split(","));
//        inputSelect(form, "siteType", value.getSiteType().getValue().toString());

        log.info("to click submit for add site.");
    }

    @Override
    public List<WebElement> listTableRows() {
        beforeDriver();
        // .panel-body>.row>div
        // //*[@id="fa-puzzle-piece"]/div[2]/div/div[2]/div/div[1]

        return webDriver.findElements(By.cssSelector(".panel-body>.row>div"))
                .stream()
                .filter(webElement -> !webElement.getAttribute("class").contains("site-add"))
                .collect(Collectors.toList());
    }

    @Override
    public Predicate<? super WebElement> findRow(Site value) {
        return webElement -> {
            String id = webElement.getAttribute("data-id");
            return !StringUtils.isEmpty(id) && value.getSiteId().toString().equals(id);
        };
    }

    @Override
    protected Predicate<WebElement> rowPredicate(Site site) {
        return webElement -> {
            try {
                WebElement image = webElement.findElement(By.tagName("img"));
                String imageSrc = image.getAttribute("src");

                if (site.getLogoUri() == null) {
                    assertThat(imageSrc)
                            .contains(site.getName());
                } else {
                    assertThat(imageSrc)
                            .endsWith(site.getLogoUri());
                }

                //应该存在上架 或者下架的button
                List<WebElement> buttons = webElement.findElements(By.tagName("button"));
                String exceptedButtonName = site.isEnabled() ? "下架" : "上架";
                String badButtonName = site.isEnabled() ? "上架" : "下架";
                assertThat(buttons)
                        .haveAtLeast(1, new Condition<>(button
                                -> button.getText().contains(exceptedButtonName), "需要一个" + exceptedButtonName))
                        .doNotHave(new Condition<>(button
                                -> button.getText().contains(badButtonName), "不要" + badButtonName));

                // site-alert 下方显示的名字
                WebElement alert = webElement.findElement(By.className("site-alert"));
                if (site.isAbleToRun()) {
                    assertThat(alert.getAttribute("class"))
                            .contains("text-success");
                    assertThat(alert.getText())
                            .contains(site.getName());
                } else {
                    assertThat(alert.getAttribute("class"))
                            .contains("text-danger");
                }

                //预览按钮
                List<WebElement> previews = webElement.findElements(By.className("site-preview"));
                if (site.isAbleToRun() && site.isEnabled())
                    assertThat(previews).isNotEmpty();
                else
                    assertThat(previews).isEmpty();

            } catch (RuntimeException ex) {
                printThisPage();
                throw ex;
            }
            // 如果site存在logo则路径需是那个
            return true;
        };
    }

    /**
     * @return 模板列表
     */
    public List<WebElement> listTemplateRows() {
        beforeDriver();
        WebElement root = webDriver.findElement(By.id("template"));
        if (!root.isDisplayed()) {
            // 点击下呗
            webDriver.findElements(By.tagName("li"))
                    .stream()
                    .filter((ele) -> {
                        List<WebElement> as = ele.findElements(By.tagName("a"));
                        return !as.isEmpty() && as.get(0).getText().equals("模板");
                    })
                    .peek(System.out::println)
                    .findAny()
                    .orElseThrow(() -> new IllegalStateException("没有找到显示模板的按钮"))
                    .findElement(By.tagName("a")).click();
        }
        assertThat(root.isDisplayed())
                .as("模板tab打不开")
                .isTrue();

        return root.findElements(By.cssSelector("div.template-site"));
    }

    /**
     * 点赞 或者取消赞
     *
     * @param templateRow 模板
     */
    public void laud(WebElement templateRow) {
        WebElement lauds = templateRow.findElement(By.className("template-lauds"));
        // fa-thumbs-up
        boolean state = lauds.findElements(By.className("fa-thumbs-up")).isEmpty();
        String text = lauds.getText();

        lauds.findElement(By.tagName("span")).click();
        assertThat(lauds.findElements(By.className("fa-thumbs-up")).isEmpty())
                .isEqualTo(!state);
    }

    public void preview(WebElement templateRow, String newTitle) {
        beforeDriver();
        String current = webDriver.getWindowHandle();
        WebElement preview = templateRow.findElement(By.className("site-preview")).findElement(By.tagName("a"));
        Actions builder = new Actions(webDriver);
        builder
                .moveToElement(templateRow)
                .click(preview)
                .build().perform();

        String blankWindow = webDriver.getWindowHandles().stream()
                .filter(x -> !x.equals(current))
                .findFirst()
                .orElseThrow(IllegalStateException::new);

//        webDriver.switchTo().parentFrame();
        webDriver.switchTo().window(blankWindow);
        assertThat(webDriver.getTitle())
                .isEqualTo(newTitle);
        webDriver.close();
        webDriver.switchTo().window(current);
    }

    /**
     * 使用模板
     *
     * @param templateRow
     * @param append
     */
    public void use(WebElement templateRow, boolean append) throws InterruptedException {
        beforeDriver();
        WebElement button = templateRow.findElement(By.className("template-use"));
        WebElement modal = webDriver.findElement(By.id("useTemplateModal"));

        button.click();
        waitOn((wait) -> wait.until(new com.google.common.base.Predicate<WebDriver>() {
            @Override
            public boolean apply(@Nullable WebDriver input) {
                return modal.isDisplayed();
            }
        }));
        assertThat(modal.isDisplayed())
                .isTrue();

        modal.findElements(By.tagName("label")).stream()
                .filter((label) -> append == label.getText().contains("追加"))
                .findAny().orElseThrow(IllegalStateException::new)
                .click();

        modal.findElement(By.className("btn-primary")).click();
    }
}
