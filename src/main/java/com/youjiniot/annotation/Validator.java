package com.youjiniot.annotation;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by youjin on 2017/11/6.
 */
public abstract class Validator {
    protected abstract boolean validate(HttpServletRequest request);

    protected abstract Map<String,Object> handleError();

}
