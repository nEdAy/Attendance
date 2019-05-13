package cn.neday.iflytek;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * ┃　　　┃ 神兽保佑
 * ┃　　　┃ BUG退散！
 * ┃　　　┗━━━┓
 * ┃　　　　　　　┣┓
 * ┃　　　　　　　┏┛
 * ┗┓┓┏━┳┓┏┛
 * ┃┫┫　┃┫┫
 * ┗┻┛　┗┻┛
 * <p>
 * Spring Boot应用启动类
 * <p>
 * Created by shengsu on 17/10/21.
 */

@EnableAsync
@EnableScheduling // 是否开启定时任务
@SpringBootApplication // Spring Boot 应用的标识
@MapperScan("cn.neday.iflytek") // mybatis扫描路径
public class IflytekApplication {

    /**
     * main函数作为主入口。SpringApplication引导应用，并将Application本身作为参数传递给run方法。
     * 具体run方法会启动嵌入式的Tomcat并初始化Spring环境及其各Spring组件。
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(IflytekApplication.class, args);
    }
}
