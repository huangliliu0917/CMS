/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.service;

import com.huotu.hotcms.service.entity.WidgetInfo;
import com.huotu.hotcms.service.entity.login.Owner;
import com.huotu.hotcms.widget.InstalledWidget;
import com.huotu.hotcms.widget.exception.FormatException;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 控件工厂服务,可以在此管理控件。
 *
 * @author CJ
 */
public interface WidgetFactoryService {

    /**
     * 安装指定控件的jar包
     *
     * @param info 控件信息
     * @param data 可以未空表示需要自行下载
     * @throws IOException
     */
    void setupJarFile(WidgetInfo info, InputStream data) throws IOException;

    /**
     * 下载widget jar文件
     *
     * @param groupId      分组id,参考maven
     * @param version      版本
     * @param widgetId     控件id
     * @param outputStream 目标
     * @throws IOException 从网络上获取失败时
     */
    void downloadJar(String groupId, String widgetId, String version, OutputStream outputStream) throws IOException;

    /**
     * 当前owner已安装的控件列表,只会展示控件的「一个」版本
     *
     * @param owner 专享商户可为空表示所有控件
     * @return 已安装控件列表, 总不会是null了不起一个空List
     */
    @Transactional(readOnly = true)
    List<InstalledWidget> widgetList(Owner owner);

    /**
     * 检查控件安装状态
     *
     * @param widgetInfo 控件包信息
     * @return 如果控件已安装则返回安装信息, 结果必然非null
     */
    List<InstalledWidget> installedStatus(WidgetInfo widgetInfo);

    /**
     * 重新载入控件
     */
    @PostConstruct
    @Transactional(readOnly = true)
    void reloadWidgets() throws IOException, FormatException;

    /**
     * 安装新的控件包
     * <p>
     * 从私有Maven仓库 http://repo.51flashmall.com:8081/nexus/content/groups/public 自动获取</p>
     * <p>这个安装将是持久化的</p>
     *
     * @param owner      专属商户
     * @param groupId    分组id,参考maven
     * @param artifactId 控件包id
     * @param version    版本
     * @param type       控件类型
     * @throws IOException     获取控件包资源时发生意外
     * @throws FormatException 控件包违约
     */
    @Transactional
    void installWidgetInfo(Owner owner, String groupId, String artifactId, String version, String type)
            throws IOException, FormatException;

    /**
     * 安装新的控件包
     * <p>
     * 从私有Maven仓库 http://repo.51flashmall.com:8081/nexus/content/groups/public 自动获取</p>
     * <p>这个安装将是持久化的</p>
     *
     * @param widgetInfo 控件包的信息
     * @throws IOException     获取控件包资源时发生意外
     * @throws FormatException 控件包违约
     */
    @Transactional
    void installWidgetInfo(WidgetInfo widgetInfo) throws IOException, FormatException;

    /**
     * 以此控件包为主,禁用该控件包相关控件的其他版本。
     * <p>
     * 需要检查每一个使用该控件的组件属性是否符合要求。</p>
     * <p>
     * 如果使用了缓存系统,包括组件缓存和页面缓存,更新以后都需要清理缓存。</p>
     * <p>如果顺利完成更新则应该将过往版本的控件包标记为禁用,并且移除已安装控件实例。</p>
     *
     * @param widgetInfo  控件包
     * @param ignoreError 忽略错误 true 忽略，false 不忽略 默认不忽略错误
     * @throws IllegalStateException 不支持的控件包，检查后不兼容旧版本
     * @throws IOException           查找列表page列表失败等
     */
    void primary(WidgetInfo widgetInfo, boolean ignoreError) throws IllegalStateException, IOException;

}
