package com.lxy;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
//全局过滤器示例，以登录为例
@Component
@Order(-1)//在拥有多个过滤器的情况下，需设置过滤器优先级
public class LoginFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> params = request.getQueryParams();
        //2.获取参数中的用户名称参数
        String username = params.getFirst("lxy");
        //3.判断参数值是否符合规定：例如是否符合登录身份为admin
        if ("admin".equals(username)) {
            //4.是 放行
            return chain.filter(exchange);
        }
        //5.否 拦截
        //5.1. 设置响应码 方便用户查看
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        //5.2. 拦截请求
        return exchange.getResponse().setComplete();
    }
}
