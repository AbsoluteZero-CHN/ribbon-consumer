package cn.noload.consumer.common.rpc.provider;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author caohao
 * @version 2018/4/27
 */
@RestController
@RequestMapping("")
public class WelcomeRest {

    private final RestTemplate restTemplate;

    public WelcomeRest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/rest")
    public String rest() {
        return restTemplate.getForEntity("http://eureka-provider/hello/index", String.class).getBody();
    }
}
