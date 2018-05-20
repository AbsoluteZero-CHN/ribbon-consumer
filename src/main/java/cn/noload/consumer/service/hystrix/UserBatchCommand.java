package cn.noload.consumer.service.hystrix;

import cn.noload.consumer.domain.User;
import cn.noload.consumer.service.UserService;
import com.netflix.hystrix.HystrixCommand;

import java.util.List;

import static com.netflix.hystrix.HystrixCommandGroupKey.Factory.asKey;

/**
 * @author caohao
 * @version 2018/5/6
 */
public class UserBatchCommand extends HystrixCommand<List<User>> {

    private UserService userService;
    private List<Long> userIds;

    public UserBatchCommand(UserService userService, List<Long> userIds) {
        super(Setter.withGroupKey(asKey("userServiceCommand")));
        this.userService = userService;
        this.userIds = userIds;
    }

    @Override
    protected List<User> run() throws Exception {
        return userService.findAll(this.userIds);
    }
}
