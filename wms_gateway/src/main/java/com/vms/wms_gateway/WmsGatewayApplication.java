package com.vms.wms_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class WmsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WmsGatewayApplication.class, args);
    }

}
