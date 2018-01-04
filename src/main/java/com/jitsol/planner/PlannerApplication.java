package com.jitsol.planner;

import com.jitsol.planner.loader.ILoader;
import com.jitsol.planner.solver.ISolver;
import com.jitsol.planner.solver.LocalSolver.LocalSolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.naming.ConfigurationException;

@SpringBootApplication
@Configuration
public class PlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Object[] {PlannerApplication.class}, args);
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_PROTOTYPE)
	public ILoader loader(@Value("${loader.type}") String loaderClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

		Class loaderClass = Class.forName(loaderClassName);

		return (ILoader)loaderClass.newInstance();
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public ISolver solver(@Value("${solver.type}") String solverType) throws ConfigurationException {

		switch (solverType.toLowerCase()) {
			case("local"):
				return new LocalSolver();
			/*case("remote"):
				return new RemoteSolver();*/
			default:
				throw new ConfigurationException("solver.type prop value can be 'local' or 'remote' only");
		}

	}
}