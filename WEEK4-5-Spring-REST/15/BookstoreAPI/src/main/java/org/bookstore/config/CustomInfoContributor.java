package org.bookstore.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("API", Map.of(
                "name", "BookstoreAPI",
                "description", "An API for managing bookstore operations",
                "version", "1.0.0"
        ));
    }
}
