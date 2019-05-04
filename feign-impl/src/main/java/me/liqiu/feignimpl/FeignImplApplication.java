package me.liqiu.feignimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.liqiu.feignapi.vo.TestVo;
import me.liqiu.feignapi.vo.TestVo1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class FeignImplApplication {

    @PostMapping(value = "/echoTest", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String echoTest(@RequestBody TestVo test) {
        try {
            return new ObjectMapper().writeValueAsString(test);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{ \"failed\": true }";
        }
    }

    @PostMapping(value = "/echoTest1")
    public String echoTest(TestVo test, TestVo1 test1) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            StringBuilder sb = new StringBuilder();
            sb.append(mapper.writeValueAsString(test));
            sb.append("\n");
            sb.append(mapper.writeValueAsString(test1));
            return sb.toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{ \"failed\": true }";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(FeignImplApplication.class, args);
    }

}
