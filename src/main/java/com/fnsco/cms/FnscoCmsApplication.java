package com.fnsco.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动类
 *
 * @Author Bob.zhu
 * @Date 2018-07-30 11:55
 **/

@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
@ComponentScan(basePackages = {"com.fnsco.cms"})
@MapperScan("com.fnsco.cms.dao")
public class FnscoCmsApplication {
    public static void main(String[] args) throws Exception {
        new SpringApplication(FnscoCmsApplication.class).run(args);
    }


}
