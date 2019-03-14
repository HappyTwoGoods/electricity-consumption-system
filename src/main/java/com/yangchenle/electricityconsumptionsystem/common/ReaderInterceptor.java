package com.yangchenle.electricityconsumptionsystem.common;

import com.yangchenle.electricityconsumptionsystem.constant.SessionParameters;
import com.yangchenle.electricityconsumptionsystem.dto.ReaderAccountDTO;
import com.yangchenle.electricityconsumptionsystem.dto.UserDTO;
import com.yangchenle.electricityconsumptionsystem.service.ReaderTableService;
import com.yangchenle.electricityconsumptionsystem.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class ReaderInterceptor implements HandlerInterceptor {
    @Resource
    private ReaderTableService readerTableService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        try {
            String phone = (String) session.getAttribute(SessionParameters.PHONE);
            ReaderAccountDTO readerAccountDTO = readerTableService.readerLogin(phone);
            if (readerAccountDTO == null) {
                ManagerInterceptor.sendJson(response);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("用户未登陆");
        }
        ManagerInterceptor.sendJson(response);
        return false;
    }
}
