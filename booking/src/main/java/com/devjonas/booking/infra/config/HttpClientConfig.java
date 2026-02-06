package com.devjonas.booking.infra.config;

import com.devjonas.booking.application.client.VehicleServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientConfig {

    @Bean
    public VehicleServiceClient vehicleServiceClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(VehicleServiceClient.class);
    }
}
