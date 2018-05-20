package cn.noload.consumer.web.rest;

import cn.noload.consumer.domain.User;
import cn.noload.consumer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author caohao
 * @version 2018/5/5
 */
@RestController
@RequestMapping("/user")
public class UserResource {

    private final static Logger logger = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable("id") Long id) throws ExecutionException, InterruptedException {
        Map<String, User> userMap = userService.getUser(id);
        logger.info(userMap.toString());
        return userMap.get("asyncUser");
    }

    @GetMapping("/batch/{ids}")
    public List<User> findAll(@PathVariable List<Long> ids) {
        List<User> resp = new ArrayList<User>(ids.size());
        ids.forEach(id -> resp.add(userService.getSingleUser(id)));
        return resp;
    }

    @GetMapping("/collapse/{id}")
    public User collapseGetUser(@PathVariable Long id) throws ExecutionException, InterruptedException {
        return userService.collapseGetUser(id);
    }
}
