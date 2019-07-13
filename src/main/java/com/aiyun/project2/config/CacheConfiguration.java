package com.aiyun.project2.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.aiyun.project2.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.aiyun.project2.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.aiyun.project2.domain.User.class.getName());
            createCache(cm, com.aiyun.project2.domain.Authority.class.getName());
            createCache(cm, com.aiyun.project2.domain.User.class.getName() + ".authorities");
            createCache(cm, com.aiyun.project2.domain.Orders.class.getName());
            createCache(cm, com.aiyun.project2.domain.Customer.class.getName());
            createCache(cm, com.aiyun.project2.domain.Collect.class.getName());
            createCache(cm, com.aiyun.project2.domain.Variety.class.getName());
            createCache(cm, com.aiyun.project2.domain.Cinema.class.getName());
            createCache(cm, com.aiyun.project2.domain.Round.class.getName());
            createCache(cm, com.aiyun.project2.domain.Movie.class.getName());
            createCache(cm, com.aiyun.project2.domain.Director.class.getName());
            createCache(cm, com.aiyun.project2.domain.City.class.getName());
            createCache(cm, com.aiyun.project2.domain.Play.class.getName());
            createCache(cm, com.aiyun.project2.domain.Actor.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
