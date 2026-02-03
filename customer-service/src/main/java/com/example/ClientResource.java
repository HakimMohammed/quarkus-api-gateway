package com.example;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;

public interface ClientResource extends PanacheEntityResource<Client, Long> {
}
