package com.foo.springsecuritydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemoApplication.class, args);
    }

    @RequestMapping("/")
    public String home() {
        return "spring-boot home page";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    // 控制一个方法是否能够被调用
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/roleAuth")
    public String roleAuth() {
        return "admin auth";
    }


    // 控制一个方法是否能够被调用
    @PreAuthorize("#id < 10 and principal.username.equals(#username) and #user.username.equals('abc')")// principal是userDetail里的参数
    @PostAuthorize("returnObject%2 == 0")
    @RequestMapping("/test")
    public Integer test(Integer id, String username, User user) {
        return id;
    }


    // 对集合类型的参数进行过滤
    @PreFilter("filterObject%2 == 0")
    // 对集合类型的返回值进行过滤
    @PostFilter("filterObject%4 == 0")
    @RequestMapping("/test2")
    public List<Integer> test2(List<Integer> ids) {
        return ids;
    }
}
