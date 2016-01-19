package com.huotu.hotcms.admin.controller;

import com.huotu.hotcms.admin.service.StaticResourceService;
import com.huotu.hotcms.admin.util.web.CookieUser;
import com.huotu.hotcms.service.entity.Category;
import com.huotu.hotcms.service.entity.Download;
import com.huotu.hotcms.service.repository.CategoryRepository;
import com.huotu.hotcms.service.service.DownloadService;
import com.huotu.hotcms.service.util.ResultOptionEnum;
import com.huotu.hotcms.service.util.ResultView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by chendeyu on 2016/1/11.
 */
@Controller
@RequestMapping("/download")
public class DownloadController {
    private static final Log log = LogFactory.getLog(DownloadController.class);

    @Autowired
    private DownloadService downloadService;

    @Autowired
    private StaticResourceService resourceServer;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CookieUser cookieUser;


        @RequestMapping("/downloadList")
        public ModelAndView downloadList(@RequestParam(value = "id",defaultValue = "0") Long id) throws Exception
        {
            ModelAndView modelAndView=new ModelAndView();
            try{
            Download download= downloadService.findById(id);
            Category category =download.getCategory();
            modelAndView.addObject("download",download);
            modelAndView.setViewName("/view/contents/downloadList.html");
            }catch (Exception ex){
                log.error(ex.getMessage());
            }

            return  modelAndView;
        }





    @RequestMapping(value = "/addDownload")
    public ModelAndView addDownload(Integer customerId) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/view/widget/addDownload.html");
        return  modelAndView;
    }

    /*
  * 修改图库
  * */
    @RequestMapping("/updateDownload")
    public ModelAndView updateDownload(@RequestParam(value = "id",defaultValue = "0") Long id,Integer customerId) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        try{
        modelAndView.setViewName("/view/contents/updateDownload.html");
        Download download= downloadService.findById(id);
        Category category= download.getCategory();
        Integer modelType = category.getModelId();
        Set<Category> categorys=categoryRepository.findByCustomerIdAndModelId(customerId, modelType);
            String downloadFile="";
            if(!StringUtils.isEmpty(download.getDownloadUrl())) {
                downloadFile = resourceServer.getResource(download.getDownloadUrl()).toString();
            }
        modelAndView.addObject("downloadFile",downloadFile);
        modelAndView.addObject("categorys",categorys);
        modelAndView.addObject("download",download);
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return modelAndView;
    }

    /*
      * 更新下载
      * */
    @RequestMapping(value = "/saveDownload",method = RequestMethod.POST)
    @Transactional(value = "transactionManager")
    @ResponseBody
    public ResultView saveLink(Download download,Long categoryId){
        ResultView result=null;
        try {
            Long id = download.getId();
            Category category = categoryRepository.getOne(categoryId);
            if(id!=null)
            {
                Download downloadOld = downloadService.findById(download.getId());
                download.setCreateTime(downloadOld.getCreateTime());
                download.setUpdateTime(LocalDateTime.now());
            }
            else{
                download.setCreateTime(LocalDateTime.now());
                download.setUpdateTime(LocalDateTime.now());
            }
            download.setCategory(category);
            downloadService.saveDownload(download);
            result = new ResultView(ResultOptionEnum.OK.getCode(), ResultOptionEnum.OK.getValue(), null);
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
            result=new ResultView(ResultOptionEnum.FAILE.getCode(),ResultOptionEnum.FAILE.getValue(),null);
        }
        return  result;
    }




    @RequestMapping(value = "/deleteDownload",method = RequestMethod.POST)
    @ResponseBody
    public ResultView deleteDownload(@RequestParam(name = "id",required = true,defaultValue = "0") Long id,int customerId,HttpServletRequest request) {
        ResultView result=null;
        try{
            if(cookieUser.isSupper(request)) {
                Download download = downloadService.findById(id);
                download.setDeleted(true);
                downloadService.saveDownload(download);
                result=new ResultView(ResultOptionEnum.OK.getCode(),ResultOptionEnum.OK.getValue(),null);
            }
            else {
                result=new ResultView(ResultOptionEnum.NO_LIMITS.getCode(),ResultOptionEnum.NO_LIMITS.getValue(),null);
            }
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
            result=new ResultView(ResultOptionEnum.FAILE.getCode(),ResultOptionEnum.FAILE.getValue(),null);
        }
        return  result;
    }



}
