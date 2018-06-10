package com.james.mall.base;

import com.james.mall.constant.ResponseHintConstant;
import com.james.mall.util.JwtTokenUtil;
import com.james.mall.util.ResponseUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class MallHandlerInterceptor implements HandlerInterceptor {
    private Logger logger = Logger.getLogger(MallHandlerInterceptor.class.getName());

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("preHandle:"+request.getRequestURI());
//        Cookie[] cookies = request.getCookies();
//        if(cookies!=null&&cookies.length>0){
//            for (int i = 0; i < cookies.length; i++) {
//                logger.warning("cookie"+i+":"+cookies[i].getName()+"--"+cookies[i].getValue());
//            }
//        }

//        对于部分接口,记录接口名称次数+用户id

//        return jwtTokenValid(request,response);
        return jwtTokenValid(request,response);
    }

    private boolean jwtTokenValid(HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader(JwtTokenUtil.JWT_TOKEN_HEADER);
        String username = null;
        boolean preResult = false;
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(token);
                Boolean validateResult = jwtTokenUtil.validateToken(token);

//                String uid = request.getParameter("uid");
//uid get方法可以获取值,post方法获取不到.uid应在请求头传过来,或放在cookie中,或者直接使用jwt的uid
//                System.out.println("username:" + username );
//                System.out.println("uid:" + uid );
//                System.out.println("validateResult:" + validateResult);
                if (validateResult && username != null) {
                    preResult = true;
                }
            } catch (IllegalArgumentException | ExpiredJwtException | SignatureException e) {
                e.printStackTrace();
            }
        } else {
            logger.warning("couldn't find bearer string, will ignore the header");
        }
        if (!preResult) {
            response.setContentType("text/plain;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            PrintWriter out;
            try {
                out = response.getWriter();
                out.write(ResponseUtil.getFailedString(ResponseHintConstant.UNAUTHORIZED));
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized");
        }
        request.setAttribute("uid",username);// controller中获取 @RequestAttribute String uid
        return preResult;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        logger.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        logger.info("afterCompletion");
    }
}
