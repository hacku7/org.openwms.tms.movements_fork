/*
 * Copyright 2005-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.wms.app;

import io.micrometer.core.instrument.MeterRegistry;
import org.ameba.annotation.EnableAspects;
import org.ameba.app.SpringProfiles;
import org.ameba.http.PermitAllCorsConfigurationSource;
import org.ameba.i18n.AbstractTranslator;
import org.ameba.i18n.Translator;
import org.ameba.mapping.BeanMapper;
import org.ameba.mapping.DozerMapperImpl;
import org.ameba.system.NestedReloadableResourceBundleMessageSource;
import org.openwms.wms.impl.MovementRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;
import java.util.Properties;

/**
 * A MovementModuleConfiguration.
 *
 * @author Heiko Scherrer
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = MovementRepository.class)
@EntityScan(basePackageClasses = MovementRepository.class)
@EnableJpaAuditing
@EnableAspects(propagateRootCause = true)
@EnableScheduling
public class MovementModuleConfiguration {

    @Bean MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(@Value("${spring.application.name}") String applicationName) {
        return registry -> registry.config().commonTags("application", applicationName);
    }

    @Profile(SpringProfiles.DEVELOPMENT_PROFILE)
    @Bean Filter corsFiler() {
        return new CorsFilter(new PermitAllCorsConfigurationSource());
    }

    @Bean BeanMapper beanMapper() {
        return new DozerMapperImpl("META-INF/dozer/movements-bean-mappings.xml");
    }

    @Bean Translator translator() {
        return new AbstractTranslator() {
            @Override
            protected MessageSource getMessageSource() {
                return messageSource();
            }
        };
    }

    @Bean MessageSource messageSource() {
        NestedReloadableResourceBundleMessageSource messageSource = new NestedReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/META-INF/i18n");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCommonMessages(new Properties());
        return messageSource;
    }}
