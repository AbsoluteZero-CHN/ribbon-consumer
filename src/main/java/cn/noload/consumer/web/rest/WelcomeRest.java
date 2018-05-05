package cn.noload.consumer.web.rest;

import cn.noload.consumer.service.hystrix.WelcomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/rest/{id}")
    public String rest(@PathVariable Long id) {
        return welcomeService.welcome(id);
    }
}
