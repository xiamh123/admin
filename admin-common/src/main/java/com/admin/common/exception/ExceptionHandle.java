package com.admin.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller类统一异常处理
 * @author  xiamh
 * @date    18/6/3
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * 系统异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object globalException(Exception e) {
        e.printStackTrace();

        /** 方法内编写统一异常处理逻辑 */
        logger.info("[系统异常]:{}", e.getMessage());
        return e.getMessage();
    }

    /**
     * 业务异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BatchException.class)
    @ResponseBody
    public Object businessException(BatchException e) {
        e.printStackTrace();

        /** 方法内编写统一业务异常处理逻辑 */
        logger.info("[业务异常]:{}", e.toString());
        return e;
    }

}



