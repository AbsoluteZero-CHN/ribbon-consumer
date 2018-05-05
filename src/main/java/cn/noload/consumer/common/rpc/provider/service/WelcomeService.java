package cn.noload.consumer.common.rpc.provider.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @HystrixCommand(fallbackMethod = "fallback")
    public String welcome() {
        return restTemplate.getForEntity("http://eureka-provider/hello/index", String.class).getBody();
    }

    public String fallback() {
        return "error";
    }
}
