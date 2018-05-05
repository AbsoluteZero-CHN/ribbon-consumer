package cn.noload.consumer.service;

import cn.noload.consumer.domain.User;
import cn.noload.consumer.service.hystrix.UserCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author caohao
 * @version 2018/5/5
 */
@Service
public class UserService {

    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, User> getUser(Long id) throws ExecutionException, InterruptedException {
        // 同步获取 User 对象
        User syncUser = new UserCommand(restTemplate, 1L).execute();
        // 异步获取 User 对象
        User asyncUser = new UserCommand(restTemplate, 1L).queue().get();
        return new HashMap<String, User>() {{
            put("syncUser", syncUser);
            put("asyncUser", asyncUser);
        }};
    }
}
