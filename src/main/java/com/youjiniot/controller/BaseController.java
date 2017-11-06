package com.youjiniot.controller;

import com.github.pagehelper.Page;
import com.youjiniot.common.Const;
import com.youjiniot.utils.Logger;
import com.youjiniot.utils.PageData;
import com.youjiniot.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by train on 17/2/16.
 */
public class BaseController {

    public Page getPage(HttpServletRequest request){
        int pageNum=1;
        int pageSize=Const.ROWS_PER_PAGE;
        if(StringUtils.isNumeric(request.getParameter("page"))){
            pageNum=Integer.parseInt(request.getParameter("page").toString());
        }
        if(StringUtils.isNumeric(request.getParameter("pageSize"))){
            pageSize=Integer.parseInt(request.getParameter("pageSize").toString());
        }
        return new Page(pageNum,pageSize);
    }

    protected Logger logger = Logger.getLogger(this.getClass());
    /** new PageData对象
     * @return
     */
    public PageData getPageData(){
        return new PageData(this.getRequest());
    }
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }
    /**得到32位的uuid
     * @return
     */
    public String get32UUID(){
        return UuidUtil.get32UUID();
    }

    public Object getTokenData() {
        return getRequest().getAttribute("tokenData");
    }

    // 获取USER_ID
    public String getUserId() {
        Map data = (Map) getTokenData();
        if (data != null) {
            String userId = String.valueOf(data.get("tokenData"));
            return userId;
        }
        return null;
    }
}
