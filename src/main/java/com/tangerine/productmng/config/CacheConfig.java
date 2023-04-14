package com.tangerine.productmng.config;

import com.tangerine.productmng.service.product.model.ProductModel;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

@EnableCaching
@Configuration
@Profile("!test")
public class CacheConfig {

    @Bean
    public javax.cache.CacheManager inMemoryCacheManager() {
        CachingProvider provider = Caching.getCachingProvider();
        javax.cache.CacheManager cacheManager = provider.getCacheManager();


        var productByIdConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Long.class,
                ProductModel.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(10, MemoryUnit.MB));

        cacheManager.createCache("productById", Eh107Configuration.fromEhcacheCacheConfiguration(productByIdConfiguration));
        return cacheManager;
    }

}
