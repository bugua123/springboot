package com.wzw.microboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @SpringBootApplication 替代了@SpringBootConfiguration、@EnableAutoConfiguration、@ComponentScan这三个注解的功能
 * @SpringBootConfiguration：该注解继承自@Configuration，一般与@Bean配合使用，使用这两个注解就可以创建一个简单的spring配置类，可以用来替代相应的xml配置文件。
 * @EnableAutoConfiguration：该注解的意思就是Springboot可以根据你添加的jar包来配置你项目的默认配置，比如当你添加了mvc的jar包，它就会自动配置web项目所需的配置
 * @ComponentScan：顾名思义该注解是用来扫描组件的，只要组件上有@component及其子注解@Service、@Repository、@Controller等，springboot会自动扫描到并纳入Spring 容器进行管理，
 * 有点类似xml文件中的<context:component-scan>，该注解不填属性的话就是默认扫描启动类所在的包，或者启动类所在包的下一级，所以启动类要放在最外层
 *
 */

@MapperScan("com.wzw.microboot.mapper")
@SpringBootApplication(scanBasePackages={"com.wzw.*"})
public class MicrobootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicrobootApplication.class, args);
    }

}
