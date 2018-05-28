package com.geex.trace;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * Date: 18/5/21
 * Time: 下午1:40
 * Description:
 *
 * @author Leon
 */
public class ApplicationContextAwareBean implements ApplicationContextAware {
    public static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
        System.setProperty("spring.application.name", CONTEXT.getId());
    }
}