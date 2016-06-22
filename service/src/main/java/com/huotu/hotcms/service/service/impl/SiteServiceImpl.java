/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.service.service.impl;

import com.huotu.hotcms.service.common.ConfigInfo;
import com.huotu.hotcms.service.entity.Article;
import com.huotu.hotcms.service.entity.Category;
import com.huotu.hotcms.service.entity.CustomPages;
import com.huotu.hotcms.service.entity.Download;
import com.huotu.hotcms.service.entity.Gallery;
import com.huotu.hotcms.service.entity.GalleryList;
import com.huotu.hotcms.service.entity.Link;
import com.huotu.hotcms.service.entity.Notice;
import com.huotu.hotcms.service.entity.Site;
import com.huotu.hotcms.service.entity.Template;
import com.huotu.hotcms.service.entity.Video;
import com.huotu.hotcms.service.model.widget.WidgetDefaultPage;
import com.huotu.hotcms.service.model.widget.WidgetPage;
import com.huotu.hotcms.service.repository.ArticleRepository;
import com.huotu.hotcms.service.repository.CategoryRepository;
import com.huotu.hotcms.service.repository.CustomPagesRepository;
import com.huotu.hotcms.service.repository.DownloadRepository;
import com.huotu.hotcms.service.repository.GalleryListRepository;
import com.huotu.hotcms.service.repository.GalleryRepository;
import com.huotu.hotcms.service.repository.HostRepository;
import com.huotu.hotcms.service.repository.LinkRepository;
import com.huotu.hotcms.service.repository.NoticeRepository;
import com.huotu.hotcms.service.repository.SiteRepository;
import com.huotu.hotcms.service.repository.TemplateRepository;
import com.huotu.hotcms.service.repository.VideoRepository;
import com.huotu.hotcms.service.service.SiteService;
import com.huotu.hotcms.service.util.HttpUtils;
import com.huotu.hotcms.service.util.PageData;
import com.huotu.hotcms.service.util.SerialUtil;
import com.huotu.hotcms.service.widget.service.PageResolveService;
import me.jiangcai.lib.resource.service.ResourceService;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by chendeyu on 2015/12/24.
 */
@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    SiteRepository siteRepository ;

    @Autowired
    HostRepository hostRepository;

    @Autowired
    ConfigInfo configInfo;

    @Autowired
    ResourceService resourceService;

    @Autowired
    PageResolveService pageResolveService;

    @Autowired TemplateRepository templateRepository;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    CustomPagesRepository customPagesRepository;
    @Autowired
    DownloadRepository downloadRepository;
    @Autowired
    GalleryRepository galleryRepository;
    @Autowired
    LinkRepository linkRepository;

    @Autowired
    NoticeRepository noticeRepository;
    @Autowired
    VideoRepository videoRepository;

    @Autowired
    GalleryListRepository galleryListRepository;



    @Override
    public PageData<Site> getPage(long ownerId, String name, int page, int pageSize) {
        PageData<Site> data = null;
        Specification<Site> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(name)) {
                predicates.add(cb.like(root.get("name").as(String.class), "%" + name + "%"));
            }
            predicates.add(cb.equal(root.get("deleted").as(String.class), false));
            predicates.add(cb.equal(root.get("owner").get("id").as(Long.class), ownerId));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<Site> pageData = siteRepository.findAll(specification,new PageRequest(page - 1, pageSize));
        if (pageData != null) {
            List<Site> site =pageData.getContent();
            for(Site site1 : site){
                site1.setHosts(null);
            }
            data = new PageData<>();
            data.setPageCount(pageData.getTotalPages());
            data.setPageIndex(pageData.getNumber());
            data.setPageSize(pageData.getSize());
            data.setTotal(pageData.getTotalElements());
            data.setRows(pageData.getContent().toArray(new Site[pageData.getContent().size()]));
        }
        return  data;
    }

    @Override
    public Site getSite(long siteId) {
        return siteRepository.findOne(siteId);
    }

    @Override
    public Boolean save(Site site) {
        siteRepository.save(site);
        return true;
    }

    @Override
    public Site saveAndFlush(Site site) {
        return siteRepository.save(site);
    }

    @Override
    public Set<Site> findByOwnerIdAndDeleted(long ownerId, boolean deleted) {
        Set<Site> siteList = siteRepository.findByOwner_IdAndDeleted(ownerId, deleted);
        for(Site site : siteList){
            site.setHosts(null);
            site.setRegion(null);
        }
        return siteList;
    }


    @Override
    public void siteCopy(long templateId, Site customerSite) throws Exception {
         //根据模板ID读取到相应的站点
        Template template=templateRepository.findOne(templateId);
        Site templateSite=template.getSite();
        List<CustomPages> customPages=customPagesRepository.findBySite(templateSite);
        for(CustomPages customPage:customPages){
            String templateResourceConfigUrl = configInfo.getResourcesConfig(templateSite) + "/" + customPage.getId() + ".xml";
            URI url = resourceService.getResource(templateResourceConfigUrl).httpUrl().toURI();
            InputStream inputStream = HttpUtils.getInputStreamByUrl(url.toURL());
            WidgetPage widgetPage = JAXB.unmarshal(inputStream, WidgetPage.class);
            pageResolveService.createPageAndConfigByWidgetPage(widgetPage,
                    customerSite.getSiteId(), false);
        }
        createDefaultWidgetPage(templateSite, customerSite);
        deepCopy(templateSite, customerSite);
    }

    /**
     * 深度复制   站点下属类目全部复制
     * @param templateSite  模板站点
     * @param customerSite  商户站点
     */
    private void deepCopy(Site templateSite, Site customerSite) {
        /*对Category的复制*/
        customerSite=siteRepository.findOne(customerSite.getSiteId());//确保查询的数据是完备的

        List<Category> categories=categoryRepository.findBySite(templateSite);
        for(Category category:categories){
            Category newCategory=new Category();
            newCategory.setSerial(SerialUtil.formartSerial(customerSite));
            newCategory.setSite(customerSite);
            newCategory.setParent(category.getParent());
            newCategory.setName(category.getName());
            newCategory.setCustom(category.isCustom());
            newCategory.setDeleted(category.isDeleted());
            newCategory.setRoute(category.getRoute());
            newCategory.setModelId(category.getModelId());
            newCategory.setOrderWeight(category.getOrderWeight());
            newCategory.setParentIds(category.getParentIds());
            newCategory.setCreateTime(category.getCreateTime());//这两个时间不确定是否要复制
            newCategory.setUpdateTime(category.getUpdateTime());
            category=categoryRepository.save(newCategory);
            itemsCopy(category,customerSite);
        }
        /*自定义页面*/
        List<CustomPages> customPages=customPagesRepository.findBySite(templateSite);
        for(CustomPages customPage:customPages){
            if(customPage.isPublish()){//只复制发布了的，不复制草稿箱中的
                CustomPages newCustomPage=new CustomPages();
                newCustomPage.setSerial(SerialUtil.formartSerial(customerSite));
                newCustomPage.setName(customPage.getName());
                newCustomPage.setOrderWeight(customPage.getOrderWeight());
                newCustomPage.setCreateTime(customPage.getCreateTime());
                newCustomPage.setDeleted(customPage.isDeleted());
                newCustomPage.setDescription(customPage.getDescription());
                newCustomPage.setHome(customPage.isHome());
                newCustomPage.setSite(customerSite);
                newCustomPage.setPublish(customPage.isPublish());
                customPagesRepository.save(newCustomPage);
            }
        }
    }

    /**
     * 其他数据源的深度复制
     * @param category 复制后的栏目
     * @param customerSite 用户站点
     */
    private void itemsCopy(Category category,Site customerSite) {
        //文章复制
       List<Article> articles=articleRepository.findByCategory(category);
        for(Article article:articles){
            Article newArticle=new Article();
            newArticle.setCategory(category);
            newArticle.setDescription(article.getDescription());
            newArticle.setDeleted(article.isDeleted());
            newArticle.setArticleSource(article.getArticleSource());
            newArticle.setAuthor(article.getAuthor());
            newArticle.setContent(article.getContent());
            newArticle.setSystem(article.isSystem());
            newArticle.setThumbUri(article.getThumbUri());
            newArticle.setOrderWeight(article.getOrderWeight());
            newArticle.setSerial(SerialUtil.formartSerial(customerSite));
            newArticle.setTitle(article.getTitle());
            newArticle.setCreateTime(article.getCreateTime());
            newArticle.setUpdateTime(article.getUpdateTime());
            articleRepository.save(newArticle);
        }
        //下载模型复制
        List<Download> downloads=downloadRepository.findByCategory(category);
        for(Download download:downloads){
            Download d=new Download();
            d.setOrderWeight(download.getOrderWeight());
            d.setUpdateTime(download.getUpdateTime());
            d.setTitle(download.getTitle());
            d.setCreateTime(download.getCreateTime());
            d.setSerial(SerialUtil.formartSerial(customerSite));
            d.setDownloadUrl(download.getDownloadUrl());
            d.setCategory(category);
            d.setDescription(download.getDescription());
            d.setDeleted(download.isDeleted());
            downloadRepository.save(d);
        }
        //图库模型复制
        List<Gallery> galleries=galleryRepository.findByCategory(category);
        for(Gallery gallery:galleries){
            Gallery g=new Gallery();
            g.setDeleted(gallery.isDeleted());
            g.setOrderWeight(gallery.getOrderWeight());
            g.setUpdateTime(gallery.getUpdateTime());
            g.setDescription(gallery.getDescription());
            g.setContent(gallery.getContent());
            g.setLinkUrl(gallery.getLinkUrl());
            g.setThumbUri(gallery.getThumbUri());
            g.setCategory(category);
            g.setSerial(SerialUtil.formartSerial(customerSite));
            g.setTitle(gallery.getTitle());
            g.setCreateTime(gallery.getCreateTime());

            g=galleryRepository.save(g);
            //图库集合复制
            List<GalleryList> galleryLists=galleryListRepository.findByGallery(gallery);
            for(GalleryList gl:galleryLists){
                GalleryList galleryList=new GalleryList();
                galleryList.setGallery(g);
                galleryList.setSite(customerSite);
                galleryList.setUpdateTime(gl.getUpdateTime());
                galleryList.setOrderWeight(gl.getOrderWeight());
                galleryList.setSerial(SerialUtil.formartSerial(customerSite));
                galleryList.setSize(gl.getSize());
                galleryList.setThumbUri(gl.getThumbUri());
                galleryList.setDeleted(gl.isDeleted());
                galleryListRepository.save(galleryList);
            }
        }

        //链接模型复制
        List<Link> links=linkRepository.findByCategory(category);
        for(Link link:links){
            Link link1=new Link();
            link1.setOrderWeight(link.getOrderWeight());
            link1.setUpdateTime(link.getUpdateTime());
            link1.setTitle(link.getTitle());
            link1.setSerial(SerialUtil.formartSerial(customerSite));
            link1.setLinkUrl(link.getLinkUrl());
            link1.setThumbUri(link.getThumbUri());
            link1.setCategory(link.getCategory());
            link1.setCreateTime(link.getCreateTime());
            link1.setDescription(link.getDescription());
            link1.setDeleted(link.isDeleted());
            linkRepository.save(link1);
        }
        //公告模型复制
        List<Notice> notices=noticeRepository.findByCategory(category);
        for(Notice notice:notices){
            Notice notice1=new Notice();
            notice1.setDeleted(notice.isDeleted());
            notice1.setOrderWeight(notice.getOrderWeight());
            notice1.setUpdateTime(notice.getUpdateTime());
            notice1.setCreateTime(notice.getCreateTime());
            notice1.setDescription(notice.getDescription());
            notice1.setContent(notice.getContent());
            notice1.setCategory(category);
            notice1.setSerial(SerialUtil.formartSerial(customerSite));
            notice1.setTitle(notice.getTitle());
            noticeRepository.save(notice1);
        }
        //视频模型复制
        List<Video> videos=videoRepository.findByCategory(category);
        for(Video video:videos){
            Video v=new Video();
            v.setOrderWeight(video.getOrderWeight());
            v.setUpdateTime(video.getUpdateTime());
            v.setTitle(video.getTitle());
            v.setSerial(SerialUtil.formartSerial(customerSite));
            v.setCategory(category);
            v.setOutLinkUrl(video.getOutLinkUrl());
            v.setThumbUri(video.getThumbUri());
            v.setVideoUrl(video.getVideoUrl());//视频地址 ？
            v.setDescription(video.getDescription());
            v.setDeleted(video.isDeleted()); //是否需要将此作为条件
            videoRepository.save(v);
        }

    }

    /** 复制并创建公共界面
     * <p>
     *     <em>目前默认公共界面如下：</em>
     *     <ul>
     *         <li>公共头部 {@link com.huotu.hotcms.service.model.widget.WidgetDefaultPage#head }</li>
     *         <li>搜索结果界面 {@link com.huotu.hotcms.service.model.widget.WidgetDefaultPage#search }</li>
     *         <li>公共尾部 {@link com.huotu.hotcms.service.model.widget.WidgetDefaultPage#bottom }</li>
     *     </ul>
     * </p>
     * @param templateSite 模板站点
     * @param customerSite 用户站点
     * @throws IOException 其他异常
     * @throws URISyntaxException 其他异常
     *
     * @since  v2.0
     */
    private void createDefaultWidgetPage(Site templateSite, Site customerSite)
            throws IOException, URISyntaxException {

        for(WidgetDefaultPage widgetDefaultPage:WidgetDefaultPage.values()){
            WidgetPage defaultWidgetPage=pageResolveService.getWidgetPageByConfig(widgetDefaultPage.name()+".xml", templateSite);
            if(defaultWidgetPage!=null){
                pageResolveService.createDefaultPageConfigByWidgetPage(defaultWidgetPage,
                        customerSite.getSiteId(), widgetDefaultPage.name());
            }
        }
    }
}
