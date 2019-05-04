package me.liqiu.feignapi;

import me.liqiu.feignapi.config.MergeToFormParams;
import me.liqiu.feignapi.vo.TestVo;
import me.liqiu.feignapi.vo.TestVo1;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@FeignClient("service-feign-impl")
public interface TestFeign {

    @PostMapping(value = "/echoTest")
    String echoTest(TestVo test);

    @PostMapping(value = "/echoTest1", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    String echoTest1(@MergeToFormParams TestVo test1,
                     @MergeToFormParams TestVo1 test2);

}
