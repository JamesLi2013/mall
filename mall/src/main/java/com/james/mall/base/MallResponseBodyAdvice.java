package com.james.mall.base;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Enums;
import com.james.mall.bean.BaseResponseBean;
import com.james.mall.util.ResponseUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.logging.Logger;

@ControllerAdvice
public class MallResponseBodyAdvice implements ResponseBodyAdvice<Object>{
    private Logger logger=Logger.getLogger(this.getClass().getName());

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//  rest controller方法返回类型为其它对象,返回值为空也是这个     converterType:org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
//   rest controller方法返回类型为String     converterType:org.springframework.http.converter.StringHttpMessageConverter
        if("org.springframework.http.converter.json.MappingJackson2HttpMessageConverter".equals(converterType.getName())
                ||"org.springframework.http.converter.StringHttpMessageConverter".equals(converterType.getName())){
            return true;
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof BaseResponseBean) return body;//适配controller直接返回BaseResponseBean的情况,防止嵌套
        if("org.springframework.http.converter.json.MappingJackson2HttpMessageConverter".equals(selectedConverterType.getName())){
            return ResponseUtil.turnData(body);
        }else if("org.springframework.http.converter.StringHttpMessageConverter".equals(selectedConverterType.getName())){
            return ResponseUtil.getSuccessString(body.toString());
        }
        return body;
    }

}
