package cn.noload.consumer.service.hystrix;

import cn.noload.consumer.domain.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

/**
 * @author caohao
 * @version 2018/5/5
 */
public class UserCommand extends HystrixCommand<User> {

    private final RestTemplate restTemplate;
    private final Long id;

    public UserCommand(RestTemplate restTemplate, Long id) {
        super(HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey
                        .Factory
                        .asKey("UserService")));
        this.restTemplate = restTemplate;
        this.id = id;
    }

    @Override
    protected User run() throws Exception {
        return restTemplate.getForObject("http://eureka-provider/user/{id}", User.class, id);
    }

    /**
     * 服务降级
     *
     * Hystrix 命令执行失败时的后备方法
     * */
    @Override
    protected User getFallback() {
        return new User();
    }

    /**
     * 开启请求缓存
     *
     * 使用当前返回值作为 Key, 判断如果 Key 已经存在, 则直接取用
     * */
    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }
}
