package com.ding.sj;

import com.dangdang.ddframe.rdb.sharding.config.yaml.api.YamlShardingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 入口类
 * Created by ding on 2017/9/27.
 */
@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).run(args);
        System.out.println("spring-boot started");
    }

    /*@Bean(name = "shardingDataSource")
    @ConditionalOnExpression("false ")
    public DataSource shardingDataSource() throws IOException, SQLException {
        return new YamlShardingDataSource(
                new File(Application.class.getResource("/META-INF/withAssignedDataSource.yaml").getFile()));
    }

    @Bean(name = "shardingJdbcTemplate")
    @ConditionalOnExpression("false ")
    public JdbcTemplate primaryJdbcTemplate(
            @Qualifier("shardingDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }*/


}
