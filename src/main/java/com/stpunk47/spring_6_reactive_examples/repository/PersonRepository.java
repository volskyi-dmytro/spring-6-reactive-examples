package com.stpunk47.spring_6_reactive_examples.repository;

import com.stpunk47.spring_6_reactive_examples.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {

    Mono<Person> getById(Integer id);

    Flux<Person> findAll();

}
