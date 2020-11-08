package com.adverity.wka.codechallenge.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Wojciech Kaczmarek
 */
@Configuration
@EnableJpaRepositories("com.adverity.wka.codechallenge.domain")
public class PersistenceConfig {

}
