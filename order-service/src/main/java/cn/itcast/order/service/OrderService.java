package cn.itcast.order.service;

import cn.itcast.feign.client.UserFeign;
import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserFeign userFeign;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        User user = userFeign.findById(order.getUserId());

        //封装user到/Order
        order.setUser(user);
        // 4.返回
        return order;
    }


   /* @Autowired
    private RestTemplate restTemplate;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);

        //利用RestTemplate发起HTTP请求，查询用户
        //url路径
        String url="http://userservice/user/"+order.getUserId();
        User user = restTemplate.getForObject(url, User.class);
        //封装user到/Order
        order.setUser(user);
        // 4.返回
        return order;
    }*/
}
