/*package com.ind.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
//@ConditionalOnMissingBean(name ="jpaAuditingHandler")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditConfig {

	@Bean
	public AuditorAware<String> auditorProvider(){
		return new AuditorAwareImpl();
	}
}
*/