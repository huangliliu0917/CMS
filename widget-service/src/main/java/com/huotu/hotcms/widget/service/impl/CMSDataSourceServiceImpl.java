/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.service.impl;

import com.huotu.hotcms.service.common.ContentType;
import com.huotu.hotcms.service.entity.AbstractContent;
import com.huotu.hotcms.service.entity.Article;
import com.huotu.hotcms.service.entity.Category;
import com.huotu.hotcms.service.entity.Download;
import com.huotu.hotcms.service.entity.GalleryItem;
import com.huotu.hotcms.service.entity.Link;
import com.huotu.hotcms.service.entity.Site;
import com.huotu.hotcms.service.entity.Video;
import com.huotu.hotcms.service.model.BaseModel;
import com.huotu.hotcms.service.model.DataModel;
import com.huotu.hotcms.service.model.DataObject;
import com.huotu.hotcms.service.model.LinkModel;
import com.huotu.hotcms.service.model.widget.VideoModel;
import com.huotu.hotcms.service.repository.ArticleRepository;
import com.huotu.hotcms.service.repository.CategoryRepository;
import com.huotu.hotcms.service.repository.DownloadRepository;
import com.huotu.hotcms.service.repository.GalleryItemRepository;
import com.huotu.hotcms.service.repository.GalleryRepository;
import com.huotu.hotcms.service.repository.LinkRepository;
import com.huotu.hotcms.service.repository.NoticeRepository;
import com.huotu.hotcms.service.repository.VideoRepository;
import com.huotu.hotcms.widget.CMSContext;
import com.huotu.hotcms.widget.entity.PageInfo;
import com.huotu.hotcms.widget.repository.PageInfoRepository;
import com.huotu.hotcms.widget.service.CMSDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CJ
 */
@Service("cmsDataSourceService")
public class CMSDataSourceServiceImpl implements CMSDataSourceService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private DownloadRepository downloadRepository;
    @Autowired
    private PageInfoRepository pageInfoRepository;

    @Autowired
    private GalleryRepository galleryRepository;


    @Autowired
    private GalleryItemRepository galleryItemRepository;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");


    @Override
    public List<Category> findLinkCategory() {
        return categoryRepository.findBySiteAndContentType(CMSContext.RequestContext().getSite(), ContentType.Link);
    }

    @Override
    public List<LinkModel> findLinkContent(String serial) {
        List<Link> list = linkRepository.findByCategory_SiteAndCategory_Serial(CMSContext.RequestContext().getSite(), serial);
        List<LinkModel> linkModels = new ArrayList<>();
        for (Link link : list) {
            linkModels.add(Link.toLinkModel(link));
        }
        return linkModels;
    }

    @Override
    public List<Category> findGalleryCategory() {
        return categoryRepository.findBySiteAndContentType(CMSContext.RequestContext().getSite(), ContentType.Gallery);
    }

    @Override
    public List<Category> findVideoCategory() {
        return categoryRepository.findBySiteAndContentType(CMSContext.RequestContext().getSite(), ContentType.Video);
    }

    @Override
    public List<VideoModel> findVideoContent(String serial) {
        List<Video> list = videoRepository.findByCategory_SiteAndCategory_Serial(CMSContext.RequestContext().getSite(), serial);
        List<VideoModel> videoModels = new ArrayList<>();
        for (Video video : list) {
            VideoModel videoModel = Video.toVideoModel(video);
            videoModels.add(videoModel);
        }
        return videoModels;
    }

    @Override
    public List<Category> findArticleCategory() {

        return categoryRepository.findBySiteAndContentType(CMSContext.RequestContext().getSite(), ContentType.Article);
    }

    @Override
    public List<BaseModel> findArticleContent(String serial, Integer count) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(0, count, sort);
        List<Article> list = articleRepository.findByCategory_SiteAndCategory_Serial(
                CMSContext.RequestContext().getSite(), serial, pageable).getContent();
        List<BaseModel> baseModels = new ArrayList<>();
        for (Article article : list) {
            BaseModel baseModel = Article.toBaseModel(article);
            baseModels.add(baseModel);
        }
        return baseModels;
    }

    @Override
    public DataModel findContentType(Long contentType, Integer pageNum, Integer pageSize, Long pageId, String search) {
        Site site = CMSContext.RequestContext().getSite();
        Pageable pageable = new PageRequest(pageNum - 1, pageSize, new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
        DataModel dataModel = new DataModel();
        DataObject[] data;
        Page<? extends AbstractContent> page = null;
        List<? extends AbstractContent> list;
        switch (contentType.intValue()) {
            case 0:
                page = articleRepository.findAll((root, query, cb) -> {
                    Predicate predicates = cb.equal(root.get("category").get("site"), site);
                    predicates = cb.and(predicates, cb.equal(root.get("deleted"), false));
                    if (search != null && !search.equals("")) {
                        predicates = cb.and(predicates, cb.like(root.get("title"), "%" + search + "%"));
                    }
                    return predicates;
                }, pageable);
                break;
            case 1:
                page = linkRepository.findAll((root, query, cb) -> {
                    Predicate predicates = cb.equal(root.get("category").get("site"), site);
                    predicates = cb.and(predicates, cb.equal(root.get("deleted"), false));
                    if (search != null && !search.equals("")) {
                        predicates = cb.and(predicates, cb.like(root.get("title"), "%" + search + "%"));
                    }
                    return predicates;
                }, pageable);
                break;
            case 3:
                page = noticeRepository.findAll((root, query, cb) -> {
                    Predicate predicates = cb.equal(root.get("category").get("site"), site);
                    predicates = cb.and(predicates, cb.equal(root.get("deleted"), false));
                    if (search != null && !search.equals("")) {
                        predicates = cb.and(predicates, cb.like(root.get("title"), "%" + search + "%"));
                    }
                    return predicates;
                }, pageable);
                break;
            case 2:
                page = videoRepository.findAll((root, query, cb) -> {
                    Predicate predicates = cb.equal(root.get("category").get("site").as(Site.class), site);
                    predicates = cb.and(predicates, cb.equal(root.get("deleted").as(Boolean.class), false));
                    if (search != null && !search.equals("")) {
                        predicates = cb.and(predicates, cb.like(root.get("title"), "%" + search + "%"));
                    }
                    return predicates;
                }, pageable);
                list = page.getContent();
                data = new DataObject[list.size()];
                for (int i = 0, len = list.size(); i < len; i++) {
                    Video video = (Video) list.get(i);
                    DataObject dataObject = new DataObject();
                    dataObject.setId(video.getId());
                    dataObject.setDate(video.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    dataObject.setName(video.getTitle());
                    dataObject.setThumpUri(video.getThumbUri());
                    dataObject.setUrl(pageId + "/" + dataObject.getId());
                    data[i] = dataObject;
                }
                dataModel.setData(data);
                dataModel.setPageNum(pageNum);
                dataModel.setPageSize(pageSize);
                dataModel.setTotalPages(page.getTotalPages());
                dataModel.setTotalElements(page.getTotalElements());
                return dataModel;
            case 4:
                Page<GalleryItem> pages = galleryItemRepository.findByGallery_Category_Site(site, pageable);
                List<GalleryItem> galleryItems = pages.getContent();
                data = new DataObject[galleryItems.size()];
                for (int i = 0, len = galleryItems.size(); i < len; i++) {
                    GalleryItem item = galleryItems.get(i);
                    DataObject dataObject = new DataObject();
                    dataObject.setId(item.getId());
                    dataObject.setDate(item.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
                    dataObject.setThumpUri(item.getThumbUri());
                    dataObject.setSize(item.getSize());
                    data[i] = dataObject;
                }
                dataModel.setData(data);
                dataModel.setPageNum(pageNum);
                dataModel.setPageSize(pageSize);
                dataModel.setTotalPages(pages.getTotalPages());
                dataModel.setTotalElements(pages.getTotalElements());
                return dataModel;
            case 5:
                page = downloadRepository.findAll((root, query, cb) -> {
                    Predicate predicates = cb.equal(root.get("category").get("site"), site);
                    predicates = cb.and(predicates, cb.equal(root.get("deleted"), false));
                    if (search != null && !search.equals("")) {
                        predicates = cb.and(predicates, cb.like(root.get("title"), "%" + search + "%"));
                    }
                    return predicates;
                }, pageable);
                list = page.getContent();
                data = new DataObject[list.size()];
                for (int i = 0, len = list.size(); i < len; i++) {
                    Download down = (Download) list.get(i);
                    DataObject dataObject = new DataObject();
                    dataObject.setId(down.getId());
                    dataObject.setDate(down.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
                    dataObject.setName(down.getTitle());
                    dataObject.setUrl(down.getDownloadPath());
                    data[i] = dataObject;
                }
                dataModel.setData(data);
                dataModel.setPageNum(pageNum);
                dataModel.setPageSize(pageSize);
                dataModel.setTotalPages(page.getTotalPages());
                dataModel.setTotalElements(page.getTotalElements());
                return dataModel;
            case 6:
                Page<PageInfo> pageInfos = pageInfoRepository.findAll((root, query, cb) -> {
                    Predicate predicates = cb.equal(root.get("category").get("site"), site);
                    if (search != null && !search.equals("")) {
                        predicates = cb.and(predicates, cb.like(root.get("title"), "%" + search + "%"));
                    }
                    return predicates;
                }, pageable);
                List<PageInfo> pageInfoList = pageInfos.getContent();
                data = new DataObject[pageInfoList.size()];
                for (int i = 0, len = pageInfoList.size(); i < len; i++) {
                    PageInfo pageInfo = pageInfoList.get(i);
                    DataObject dataObject = new DataObject();
                    dataObject.setId(pageInfo.getPageId());
                    dataObject.setDate(pageInfo.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
                    dataObject.setName(pageInfo.getTitle());
                    dataObject.setUrl(pageInfo.getPagePath());
                    data[i] = dataObject;
                }
                dataModel.setData(data);
                dataModel.setPageNum(pageNum);
                dataModel.setPageSize(pageSize);
                dataModel.setTotalPages(pageInfos.getTotalPages());
                dataModel.setTotalElements(pageInfos.getTotalElements());
                return dataModel;
        }
        if (page != null) {
            list = page.getContent();
            data = new DataObject[list.size()];
            getDataObject(pageId, data, list);
            dataModel.setPageNum(pageNum);
            dataModel.setPageSize(pageSize);
            dataModel.setTotalPages(page.getTotalPages());
            dataModel.setTotalElements(page.getTotalElements());
            dataModel.setData(data);
        }
        return dataModel;
    }

    private void getDataObject(Long pageId, DataObject[] data, List<? extends AbstractContent> list) {
        for (int i = 0, len = list.size(); i < len; i++) {
            DataObject dataObject = new DataObject();
            dataObject.setId(list.get(i).getId());
            dataObject.setDate(list.get(i).getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
            dataObject.setName(list.get(i).getTitle());
            dataObject.setUrl(pageId + "/" + dataObject.getId());
            data[i] = dataObject;
        }
    }

}
