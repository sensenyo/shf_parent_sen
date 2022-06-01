package com.atguigu.front.interceptor;
import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import result.Result;
import result.ResultCodeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInfoInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("USER") == null) {
            response.getWriter().write(JSON.toJSONString(Result.build(null, ResultCodeEnum.LOGIN_AUTH)));
            return false;
        }
        return true;
    }

}
