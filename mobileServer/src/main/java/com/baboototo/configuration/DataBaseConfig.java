package com.baboototo.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class DataBaseConfig implements TransactionManagementConfigurer {
	
	@Value("${database.driverClassName}")
	private String driverClassName;
	@Value("${database.url}")
	private String url;
	@Value("${database.username}")
	private String username;
	@Value("${database.password}")
	private String password;
	
	@Bean
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setJdbcUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setConnectionTestQuery("select 1");
		dataSource.setMaximumPoolSize(10);
		dataSource.setAutoCommit(false);
		dataSource.addDataSourceProperty("cachePrepStmts", true);
		dataSource.addDataSourceProperty("prepStmtCacheSize", 250);
		dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
		return dataSource;
	}
	
	@Bean
	public SqlMapClientFactoryBean sqlMapClient() {
		SqlMapClientFactoryBean sqlMapClient = new SqlMapClientFactoryBean();
		sqlMapClient.setConfigLocation(new ClassPathResource("conf/database/ibatis-config.xml"));
		sqlMapClient.setDataSource(dataSource());
		return sqlMapClient;
	}
	
	@Bean
	public SqlMapClientTemplate sqlMapClientTemplate() {
		org.springframework.orm.ibatis.SqlMapClientTemplate template = new SqlMapClientTemplate();
		template.setSqlMapClient((SqlMapClient) sqlMapClient().getObject());
		return template;
	}
	
	@Bean   
	public DataSourceTransactionManager transactionManager() {
	    return new DataSourceTransactionManager(dataSource());
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return transactionManager();
	}
	
}