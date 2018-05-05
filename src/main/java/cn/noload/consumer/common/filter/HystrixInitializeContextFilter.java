package cn.noload.consumer.common.filter;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * Hystrix 初始化上下文 Filter
 *
 * @author caohao
 * @version 2018/5/5
 */
public class HystrixInitializeContextFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(HystrixInitializeContextFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("HystrixInitializeContextFilter has been init...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HystrixRequestContext.initializeContext();
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
