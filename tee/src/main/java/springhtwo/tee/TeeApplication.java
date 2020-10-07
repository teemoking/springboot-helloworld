package springhtwo.tee;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.BeanProperty;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * //@author 86187
 *
 *
//@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		JdbcTemplateAutoConfiguration.class
})
//@Slf4j
public class TeeApplication{

//public class TeeApplication implements CommandLineRunner {
//
//	@Autowired
//	private DataSource dataSource;
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//
//	public static void main(String[] args) {
//		SpringApplication.run(TeeApplication.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception{
//		showConnection();
//		showdata();
//	}
//	private void  showConnection() throws SQLException {
//		log.info(dataSource.toString());
//		Connection con = dataSource.getConnection();
//		log.info(con.toString());
//		con.close();
//	}
//	private void showdata(){
//		jdbcTemplate.queryForList("SELECT * FROM FOO").forEach(row->log.info(row.toString()));
//	}
	public static void main(String[] args) {
		SpringApplication.run(TeeApplication.class, args);
	}
//	@Bean
//	@ConfigurationProperties("foo.datasource")
	public DataSourceProperties fooDataSourceProperties(){
		return new DataSourceProperties();
	}
//	@Bean
	public DataSource fooDataSource(){
		DataSourceProperties dataSourceProperties = fooDataSourceProperties();
		log.info("foo datasource:{}",dataSourceProperties.getUrl());
		return 	dataSourceProperties.initializeDataSourceBuilder().build();
	}
//	@Bean
//	@Resource
	public PlatformTransactionManager fooTxManager(DataSource fooDataSource){
		return new DataSourceTransactionManager(fooDataSource);
	}
//	@Bean
//	@ConfigurationProperties("bar.datasource")
	public DataSourceProperties barDataSourceProperties(){
		return new DataSourceProperties();
	}
//	@Bean
	public DataSource barDataSource(){
		DataSourceProperties dataSourceProperties = barDataSourceProperties();
		log.info("bar datasource:{}",dataSourceProperties.getUrl());
		return 	dataSourceProperties.initializeDataSourceBuilder().build();
	}
//	@Bean
//	@Resource
	public PlatformTransactionManager barTxManager(DataSource barDataSource){
		return new DataSourceTransactionManager(barDataSource);
	}

}
**/
@SpringBootApplication
@Slf4j
public class TeeApplication implements CommandLineRunner{
	//	@Autowired
	//	private DataSource dataSource;
	//	@Autowired
	//	private JdbcTemplate jdbcTemplate;
	@Autowired
	private FooDao fooDao;
	@Autowired
	private BatchFooDao batchFooDao;

	public static void main(String[] args){
		SpringApplication.run(TeeApplication.class,args);
	}
	@Bean
	@Autowired
	public SimpleJdbcInsert simpleJdbcInsert(JdbcTemplate jdbcTemplate){
		return new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("FOO").usingGeneratedKeyColumns("ID");
	}
	@Bean
	@Autowired
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource){
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public void run(String... args) throws Exception{
		//log.info(dataSource.toString());
		fooDao.insertData();
		batchFooDao.batchInsert();
		fooDao.listData();

	}

}
