package cn.noload.consumer.service;

import cn.noload.consumer.domain.User;
import cn.noload.consumer.service.hystrix.UserCollapseCommand;
import cn.noload.consumer.service.hystrix.UserCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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
        User syncUser = new UserCommand(restTemplate, id).execute();
        // 异步获取 User 对象
        User asyncUser = new UserCommand(restTemplate, id).queue().get();
        return new HashMap<String, User>() {{
            put("syncUser", syncUser);
            put("asyncUser", asyncUser);
        }};
    }

    /**
     * 单次获取 User
     * */
    public User getSingleUser(Long id) {
        try {
            return new UserCommand(restTemplate, id).queue().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User collapseGetUser(Long id) throws ExecutionException, InterruptedException {
        return new UserCollapseCommand(this, id).queue().get();
    }

    public List<User> findAll(Collection<Long> userIds) {
        return restTemplate.getForObject("http://eureka-provider/user/batch/{ids}", List.class, StringUtils.join(userIds, ","));
    }
}
