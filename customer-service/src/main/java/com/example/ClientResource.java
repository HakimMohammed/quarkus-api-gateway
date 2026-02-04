package com.example;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "/")
public interface ClientResource extends PanacheEntityResource<Client, Long> {
}
