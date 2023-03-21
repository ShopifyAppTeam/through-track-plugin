package com.appteam.template.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SampleControllerTest {
    @Autowired
    private SampleController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }
}
