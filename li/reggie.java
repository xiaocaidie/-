package li;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.RequestContextFilter;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@Configuration
@EnableTransactionManagement
public class reggie {
    public static void main(String[] args) {
        SpringApplication.run(reggie.class, args);
        log.info("项目启动成功");
    }
}
