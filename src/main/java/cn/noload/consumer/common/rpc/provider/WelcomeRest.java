package cn.noload.consumer.common.rpc.provider;

import cn.noload.consumer.common.rpc.provider.service.WelcomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author caohao
 * @version 2018/4/27
 */
@RestController
@RequestMapping("welcome")
public class WelcomeRest {

    private final WelcomeService welcomeService;

    public WelcomeRest(WelcomeService welcomeService) {
        this.welcomeService = welcomeService;
    }

    @GetMapping(value = "/rest")
    public String rest() {
        return welcomeService.welcome();
    }
}
