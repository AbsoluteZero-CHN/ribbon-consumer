package cn.noload.consumer.common.config;

import cn.noload.consumer.common.filter.HystrixInitializeContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author caohao
 * @version 2018/5/5
 */
@Configuration
public class ApplicationConfigrutaion {


    @Bean
    public HystrixInitializeContextFilter hystrixInitializeContextFilter() {
        return new HystrixInitializeContextFilter();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        return new FilterRegistrationBean() {{
            setFilter(hystrixInitializeContextFilter());
            setOrder(Ordered.LOWEST_PRECEDENCE);
            setUrlPatterns(Stream.of("/*").collect(Collectors.toSet()));
        }};
    }
}
