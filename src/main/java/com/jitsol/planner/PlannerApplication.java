package com.jitsol.planner;

import com.jitsol.planner.loader.ILoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
@Configuration
public class PlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Object[] {PlannerApplication.class}, args);
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_PROTOTYPE)
	public ILoader loader(@Value("${loader.type}") String loaderClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

		String loaderClassFullname = loaderClassName.contains(".") ? loaderClassName : String.format("%s.%s", ILoader.class.getPackage().getName(), loaderClassName);

		Class loaderClass = Class.forName(loaderClassFullname);

		return (ILoader)loaderClass.newInstance();
	}
}