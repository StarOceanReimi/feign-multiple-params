package me.liqiu.servicedemo;

import me.liqiu.feignapi.TestFeign;
import me.liqiu.feignapi.vo.TestVo;
import me.liqiu.feignapi.vo.TestVo1;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@EnableDiscoveryClient
@EnableFeignClients({"me.liqiu.feignapi"})
@SpringBootApplication(scanBasePackages = {"me.liqiu.feignapi.config"})
@RestController
public class ServiceDemoApplication {

    @Autowired
    TestFeign feign;

    @GetMapping("/test")
    public String testService() {

        TestVo vo = new TestVo();
        vo.setId(1L);
        vo.setName("Hi");
        vo.setUnit(TimeUnit.MILLISECONDS);

        String s = feign.echoTest(vo);
        System.out.println(s);
        return "Hi";
    }

    @GetMapping("/test1")
    public String testService1() {
        TestVo vo = new TestVo();
        vo.setId(1L);
        vo.setName("Hi");
        vo.setUnit(TimeUnit.MILLISECONDS);
        TestVo1 vo1 = new TestVo1();
        vo1.setAge(10);
        vo1.setName1("tetete");

        String s = feign.echoTest1(vo, vo1);
        System.out.println(s);
        return "Hi";

    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceDemoApplication.class, args);
    }

}
