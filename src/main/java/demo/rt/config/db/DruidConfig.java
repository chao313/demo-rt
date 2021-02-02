package demo.rt.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * Created by arnold.zhu on 6/13/2017.
 */
@Configuration
public class DruidConfig {

	private Logger LOGGER = LoggerFactory.getLogger(DruidConfig.class);

	@Value("${spring.datasource.url-temp}")
	private String dbUrltemp;

	@Value("${spring.datasource.username-temp}")
	private String usernametemp;

	@Value("${spring.datasource.password-temp}")
	private String passwordtemp;


	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.testOnReturn}")
	private boolean testOnReturn;

	@Value("${spring.datasource.filters}")
	private String filters;

	@Value("${spring.datasource.logSlowSql}")
	private String logSlowSql;


	@Bean(name = "druidDataSourceStoneTemp")
	@Qualifier("druidDataSourceStoneTemp")
	@Primary
	public DataSource druidDataSourceStoneTemp() {
		DruidDataSource datasource = new DruidDataSource();
		try {
			//logger.info("test");
			datasource.setUrl(dbUrltemp);
			datasource.setUsername(usernametemp);
			try {
				if (dbUrltemp.indexOf("stonetemp") > -1 && StringUtils.isEmpty(passwordtemp)) {
					passwordtemp = DBPasswordCenterAgency.GetLastedPassword("stonetemp", usernametemp);
					LOGGER.info("获取stonetemp密码成功！");
				}
			} catch (Exception e) {
				LOGGER.error(">{{op_type=tranForOneCodeException, result=\"{}\"}}", "DBPasswordCenterAgency ERR");
				return null;
			}
			if (StringUtils.isEmpty(passwordtemp)) {
				LOGGER.error(">{{op_type=tranForOneCodeException, result=\"{}\"}}", "DBPasswordCenterAgency password null");
				return null;
			}
			datasource.setPassword(passwordtemp);
			datasource.setDriverClassName(driverClassName);
			datasource.setInitialSize(initialSize);
			datasource.setMinIdle(minIdle);
			datasource.setMaxActive(maxActive);
			datasource.setMaxWait(maxWait);
			datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
			datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
			datasource.setValidationQuery(validationQuery);
			datasource.setTestWhileIdle(testWhileIdle);
			datasource.setTestOnBorrow(testOnBorrow);
			datasource.setTestOnReturn(testOnReturn);
			datasource.setFilters(filters);
		} catch (Exception e) {
			LOGGER.error(">{{op_type=tranForOneCodeException, result=\"{}\"}}", "druid configuration initialization filter");
		}
		return datasource;
	}

	@Bean(name = "tempJdbcTemplate")
	public JdbcTemplate tempJdbcTemplate(@Qualifier("druidDataSourceStoneTemp") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}