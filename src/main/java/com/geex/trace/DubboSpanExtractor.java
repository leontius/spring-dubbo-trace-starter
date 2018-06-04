package com.geex.trace;

import com.alibaba.dubbo.rpc.RpcContext;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Span.SpanBuilder;
import org.springframework.cloud.sleuth.SpanExtractor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Date: 18/5/21
 * Time: 下午1:40
 * Description:
 *
 * @author Leon
 */
public class DubboSpanExtractor implements SpanExtractor<RpcContext> {

    private final Random random;


    public DubboSpanExtractor(Random random) {
        this.random = random;
    }

    @Override
    public Span joinTrace(RpcContext carrier) {
        Map<String, String> attachments = carrier.getAttachments();
        if (attachments.get(Span.TRACE_ID_NAME) == null) {
            // can't build a Span without trace id
            return null;
        }
        boolean skip = Span.SPAN_NOT_SAMPLED.equals(attachments.get(Span.SAMPLED_NAME));
        long traceId = Span
                .hexToId(attachments.get(Span.TRACE_ID_NAME));
        long spanId = attachments.get(Span.SPAN_ID_NAME) != null
                ? Span.hexToId(attachments.get(Span.SPAN_ID_NAME))
                : this.random.nextLong();
        return buildParentSpan(carrier, skip, traceId, spanId);
    }

    private Span buildParentSpan(RpcContext carrier, boolean skip,
                                 long traceId, long spanId) {
        Map<String, String> attachments = carrier.getAttachments();
        SpanBuilder span = Span.builder().traceId(traceId).spanId(spanId);
        String processId = attachments.get(Span.PROCESS_ID_NAME);
        String parentName = attachments.get(Span.SPAN_NAME_NAME);

        if (StringUtils.hasText(parentName)) {
            span.name(parentName);
        }
        if (StringUtils.hasText(processId)) {
            span.processId(processId);
        }
        if (attachments.get(Span.PARENT_ID_NAME) != null) {
            span.parent(Span.hexToId(attachments.get(Span.PARENT_ID_NAME)));
        }
        span.remote(true);
        if (skip) {
            span.exportable(false);
        }

        // 方法上有参数的情况
        if (carrier.getParameterTypes() != null &&
                carrier.getParameterTypes().length > 0) {
            Map<String, String> tags = new HashMap<>(10);
            for (int i = 0; i < carrier.getParameterTypes().length; i++) {
                String parameterType = carrier.getParameterTypes()[i].getCanonicalName();
                String arg = carrier.getArguments()[i].toString();
                tags.put(parameterType, arg);
            }

            // 参数放入span
            if (!tags.isEmpty()) {
                span.tags(tags);
            }
        }

        return span.build();
    }
}
