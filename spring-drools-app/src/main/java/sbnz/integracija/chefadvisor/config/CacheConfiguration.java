package sbnz.integracija.chefadvisor.config;

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
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

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
            createCache(cm, sbnz.integracija.chefadvisor.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, sbnz.integracija.chefadvisor.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, sbnz.integracija.chefadvisor.domain.User.class.getName());
            createCache(cm, sbnz.integracija.chefadvisor.domain.Authority.class.getName());
            createCache(cm, sbnz.integracija.chefadvisor.domain.User.class.getName() + ".authorities");
            createCache(cm, sbnz.integracija.chefadvisor.domain.Ingredient.class.getName());
            createCache(cm, sbnz.integracija.chefadvisor.domain.DishType.class.getName());
            createCache(cm, sbnz.integracija.chefadvisor.domain.DishType.class.getName() + ".dishes");
            createCache(cm, sbnz.integracija.chefadvisor.domain.IngredientType.class.getName());
            createCache(cm, sbnz.integracija.chefadvisor.domain.IngredientType.class.getName() + ".ingredientModels");
            createCache(cm, sbnz.integracija.chefadvisor.domain.UnitType.class.getName());
            createCache(cm, sbnz.integracija.chefadvisor.domain.UnitType.class.getName() + ".ingredientModels");
            createCache(cm, sbnz.integracija.chefadvisor.domain.IngredientModel.class.getName());
            createCache(cm, sbnz.integracija.chefadvisor.domain.IngredientModel.class.getName() + ".ingredients");
            createCache(cm, sbnz.integracija.chefadvisor.domain.IngredientModel.class.getName() + ".unitTypes");
            createCache(cm, sbnz.integracija.chefadvisor.domain.IngredientModel.class.getName() + ".ingredientTypes");
            createCache(cm, sbnz.integracija.chefadvisor.domain.Dish.class.getName());
            createCache(cm, sbnz.integracija.chefadvisor.domain.Dish.class.getName() + ".ingredients");
            createCache(cm, sbnz.integracija.chefadvisor.domain.Dish.class.getName() + ".types");
            createCache(cm, sbnz.integracija.chefadvisor.domain.Dish.class.getName() + ".users");
            createCache(cm, sbnz.integracija.chefadvisor.domain.CalorieConfiguration.class.getName());
            createCache(cm, sbnz.integracija.chefadvisor.domain.Rating.class.getName());
            createCache(cm, sbnz.integracija.chefadvisor.domain.Rating.class.getName() + ".dishes");
            createCache(cm, sbnz.integracija.chefadvisor.domain.Dish.class.getName() + ".ratings");
            createCache(cm, sbnz.integracija.chefadvisor.domain.Ingredient.class.getName() + ".ingredients");
            createCache(cm, sbnz.integracija.chefadvisor.domain.AlarmTriggerTemplate.class.getName());
            createCache(cm, sbnz.integracija.chefadvisor.domain.SpamDetectionTemplate.class.getName());
            createCache(cm, sbnz.integracija.chefadvisor.domain.Alarm.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
