package com.springboot.training.spaceover.spacemission.manager.utils.interceptors;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.TRACE_ID;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.TRACE_ID_HEADER;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.UNDEFINED_SERVICE_OPERATION;

import com.springboot.training.spaceover.spacemission.manager.utils.annotatations.ServiceOperation;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MdcInitInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put(TRACE_ID, Optional.ofNullable(request.getHeader(TRACE_ID_HEADER)).orElse(UUID.randomUUID().toString()));
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Optional<ServiceOperation> serviceOperationAnnotation = Optional
                    .ofNullable(handlerMethod.getMethodAnnotation(ServiceOperation.class));
            MDC.put(SERVICE_OPERATION, serviceOperationAnnotation.isPresent() ? serviceOperationAnnotation.get().value()
                    : UNDEFINED_SERVICE_OPERATION);
        }
        return true;
    }

}
