package com.james.mall.base;

import com.james.mall.bean.BaseResponseBean;
import com.james.mall.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class BizException {
    private static final Logger logger = LoggerFactory.getLogger(BizException.class);

    @ExceptionHandler({RuntimeException.class, Exception.class})
    @ResponseStatus(HttpStatus.OK)//app调用,返回通用失败信息
    @ResponseBody
    public BaseResponseBean<String> processException(HttpServletRequest request,Exception exception) {
        String errorMessage = "服务器异常";
        if (exception instanceof UserException) {
            //用户方面错误
            errorMessage = exception.getMessage();
        } else {
            // 保存日志
            // 是否需要记录 url及请求参数? 特别是数据库方面的异常.
            // 假如update表忘记加where条件,而更新整个表,将更新行数记录自定义异常中
            StringWriter writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            exception.printStackTrace(printWriter);
            printWriter.close();
            logger.error(writer.toString());
        }
        return ResponseUtil.turnFailedData(errorMessage);
    }
}
