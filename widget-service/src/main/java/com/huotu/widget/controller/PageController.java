package com.huotu.widget.controller;

import com.huotu.widget.model.ResultModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hzbc on 2016/5/27.
 */
@Controller
@RequestMapping("/pages")
public interface PageController {
    /**
     * <p>��ȡҳ��{@link com.huotu.widget.model.Page}</p>
     * @param customerId �̻�ID �ݶ�
     * @return API��������ģ�� {@link com.huotu.widget.model.ResultModel}
     */
    ResultModel getPage(long customerId);

    /**
     * <p>�������{@link com.huotu.widget.model.Page}</p>
     * @return API��������ģ�� {@link com.huotu.widget.model.ResultModel}
     */
    ResultModel savePage();

    /**
     * <p>���ҳ��{@link com.huotu.widget.model.Page}</p>
     * @param customerId �̻�ID
     * @return API��������ģ�� {@link com.huotu.widget.model.ResultModel}
     */
    ResultModel addPage(long customerId);

    /**
     * <p>ɾ������{@link com.huotu.widget.model.Page}</p>
     * @param pageId ҳ��ID
     * @return API��������ģ�� {@link com.huotu.widget.model.ResultModel}
     */
    ResultModel deletePage(long pageId);
}
