package com.tangerine.productmng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles({"test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource({"classpath:application-test.properties"})
@DirtiesContext
public abstract class AbstractIntegrationTest {

    @LocalServerPort
    protected int localPort;
    @Autowired
    protected TestRestTemplate restTemplate;

    protected String getBaseUrl(String path) {
        return String.format("http://localhost:%s/api/%s", localPort, path);
    }

    @Bean
    public CacheManager getCacheManager() {
        return new NoOpCacheManager();
    }

}
