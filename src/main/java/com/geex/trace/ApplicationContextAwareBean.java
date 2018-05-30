package com.geex.trace;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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
    private static final String SPRING_SLEUTH_SAMPLER_PERCENTAGE = "spring.sleuth.sampler.percentage";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
        if (!System.getProperties().containsKey(SPRING_SLEUTH_SAMPLER_PERCENTAGE)) {
            System.setProperty(SPRING_SLEUTH_SAMPLER_PERCENTAGE, "1");
        }
    }
}