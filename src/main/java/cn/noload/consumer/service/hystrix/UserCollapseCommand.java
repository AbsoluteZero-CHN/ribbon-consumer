package cn.noload.consumer.service.hystrix;

import cn.noload.consumer.domain.User;
import cn.noload.consumer.service.UserService;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.netflix.hystrix.HystrixCollapserKey.Factory.asKey;

/**
 * 请求合并器
 *
 * @author caohao
 * @version 2018/5/6
 */
public class UserCollapseCommand extends HystrixCollapser<List<User>, User, Long> {

    private UserService userService;
    private Long id;

    public UserCollapseCommand(UserService userService, Long id) {
        super(Setter.withCollapserKey(asKey("userCollapseCommand"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
        this.userService = userService;
        this.id = id;
    }

    @Override
    public Long getRequestArgument() {
        return id;
    }

    @Override
    protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, Long>> collection) {
        int count = 0;

        for (CollapsedRequest<User, Long> collapsedRequest : collection) {
            User user = batchResponse.get(count++);
            collapsedRequest.setResponse(user);
        }
    }

    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Long>> collection) {
        List<Long> userIds = new ArrayList<Long>(collection.size());

        userIds.addAll(collection.stream().map(CollapsedRequest :: getArgument).collect(Collectors.toList()));

        return new UserBatchCommand(userService, userIds);
    }
}
