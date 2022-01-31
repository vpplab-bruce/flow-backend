package io.vpplab.flow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowApplication.class, args);
    }

//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean sessionFactoryBean =  new SqlSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource);
//        Resource[] resources = new PathMatchingResourcePatternResolver().getResources()
//    }

}
