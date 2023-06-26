package com.example.MunDeuk.global.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.MunDeuk.repository.postgres",
    entityManagerFactoryRef = "pgsqlFactoryBean",
    transactionManagerRef = "pgsqlTransactionManager"
)
public class JdbcConnConfig {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Value("${spring.datasource.username}")
  private String dbUsername;

  @Value("${spring.datasource.password}")
  private String dbPassword;


  @Bean(name = "pgsqlDataSource")
  public DataSource getDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl(dbUrl);
    dataSource.setUsername(dbUsername);
    dataSource.setPassword(dbPassword);
    return dataSource;
  }

  @Bean(name = "pgsqlFactoryBean")
  public LocalContainerEntityManagerFactoryBean getFactoryBean(EntityManagerFactoryBuilder builder) {
    LocalContainerEntityManagerFactoryBean factoryBean = builder
        .dataSource(getDataSource())
        .packages("com.example.MunDeuk.models.postgres")
        .build();
    factoryBean.setJpaProperties(getJpaProperties());
    return factoryBean;
  }

  @Bean(name = "pgsqlTransactionManager")
  public PlatformTransactionManager getTransactionManager(EntityManagerFactoryBuilder builder) {
    LocalContainerEntityManagerFactoryBean factoryBean = getFactoryBean(builder);
    if (factoryBean != null && factoryBean.getObject() != null) {
      return new JpaTransactionManager(factoryBean.getObject());
    } else {
      throw new IllegalStateException("Failed to create transaction manager. EntityManagerFactory is null.");
    }
  }

  private Properties getJpaProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    properties.put("hibernate.hbm2ddl.auto", "create");
    properties.put("hibernate.format_sql", "true");
    properties.put("hibernate.show_sql", "true");
    return properties;
  }
}