package me.liqiu.feignapi.vo;

import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
public class TestVo {

    private String name;

    private Long id;

    private TimeUnit unit;
}
