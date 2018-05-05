package cn.noload.consumer.service.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @author caohao
 * @version 2018/5/5
 */
@Service
public class WelcomeService {

    private final RestTemplate restTemplate;

    public WelcomeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "fallback",
            /**
             * 指定了如果发生 HystrixTimeoutException 异常不执行 fallbackMethod 方法
             * 会被直接封装在 HystrixBadRequestException 中抛出
             * */
            ignoreExceptions = {})
    @CacheResult(cacheKeyMethod = "getCacheKey")
    public String welcome(Long id) {
        return restTemplate.getForEntity("http://eureka-provider/hello/index/{id}", String.class, new HashMap<String, Object>() {{
            put("id", id);
        }}).getBody();
    }

    /**
     * 服务降级
     *
     * Hystrix 命令执行失败时的后备方法
     * */
    public String fallback(Long id, Throwable e) {
        return "error";
    }

    /**
     * 定义缓存 Key 的方法
     * 返回值必须为 {@code String} 类型
     * */
    private String getCacheKey(Long id) {
        return String.valueOf(id);
    }
}
