package com.nns.wd.config;

import com.nns.wd.services.RequestService;
import com.nns.wd.services.redis.RedisRequestService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * Created by Nirav on 09/05/2016.
 */
@Profile("MockService")
@Configuration
public class MockServicesConfig {

    @Bean
    @Primary
    public RequestService requestService(){
        return Mockito.mock(RedisRequestService.class);
    }
}
