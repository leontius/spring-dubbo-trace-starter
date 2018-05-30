package com.geex.trace;


import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanInjector;
import org.springframework.cloud.sleuth.Tracer;

/**
 * Created with IntelliJ IDEA.
 * Date: 18/5/21
 * Time: 下午1:40
 * Description:
 *
 * @author Leon
 */
@Activate(group = {Constants.CONSUMER}, order = -9000)
public class ConsumerSpanFilter implements Filter {


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        boolean isTraceDubbo = false;
        Tracer tracer = null;
        SpanInjector spanInjector = null;
        try {
            tracer = ApplicationContextAwareBean.CONTEXT.getBean(Tracer.class);
            spanInjector = ApplicationContextAwareBean.CONTEXT.getBean(DubboSpanInjector.class);
            isTraceDubbo = (tracer != null && spanInjector != null);
            if (isTraceDubbo) {
                String spanName = invoker.getUrl().getParameter("interface") + ":" + invocation.getMethodName();
                Span newSpan = tracer.createSpan(spanName);
                spanInjector.inject(newSpan, RpcContext.getContext());
                newSpan.logEvent(Span.CLIENT_SEND);
            }

            Result result = invoker.invoke(invocation);
            return result;

        } finally {
            if (isTraceDubbo) {
                if (tracer.isTracing()) {
                    tracer.getCurrentSpan().logEvent(Span.CLIENT_RECV);
                    tracer.close(tracer.getCurrentSpan());
                }

            }
        }
    }

}
