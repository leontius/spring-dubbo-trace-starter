package com.geex.trace;

import org.springframework.cloud.sleuth.autoconfig.TraceAutoConfiguration;
import org.springframework.cloud.sleuth.instrument.messaging.TraceSpanMessagingAutoConfiguration;
import org.springframework.cloud.sleuth.instrument.messaging.TraceSpringIntegrationAutoConfiguration;
import org.springframework.cloud.sleuth.instrument.web.TraceHttpAutoConfiguration;
import org.springframework.cloud.sleuth.instrument.web.TraceWebAutoConfiguration;
import org.springframework.cloud.sleuth.instrument.web.client.TraceWebAsyncClientAutoConfiguration;
import org.springframework.cloud.sleuth.instrument.web.client.TraceWebClientAutoConfiguration;
import org.springframework.cloud.sleuth.instrument.web.client.feign.TraceFeignClientAutoConfiguration;
import org.springframework.cloud.sleuth.log.SleuthLogAutoConfiguration;
import org.springframework.cloud.sleuth.metric.TraceMetricsAutoConfiguration;
import org.springframework.cloud.sleuth.zipkin.ZipkinAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created with IntelliJ IDEA.
 * Date: 18/5/22
 * Time: 下午4:00
 * Description:
 *
 * @author Leon
 */
@Configuration
@Import({
        ZipkinAutoConfiguration.class,
        TraceDubboAutoConfiguration.class,
        TraceAutoConfiguration.class,
        TraceMetricsAutoConfiguration.class,
        SleuthLogAutoConfiguration.class,
        TraceSpanMessagingAutoConfiguration.class,
        TraceSpringIntegrationAutoConfiguration.class,
        TraceHttpAutoConfiguration.class,
        TraceWebAutoConfiguration.class,
        TraceWebClientAutoConfiguration.class,
        TraceWebAsyncClientAutoConfiguration.class,
        TraceFeignClientAutoConfiguration.class
})
public class TraceDubboManualConfiguration {
}