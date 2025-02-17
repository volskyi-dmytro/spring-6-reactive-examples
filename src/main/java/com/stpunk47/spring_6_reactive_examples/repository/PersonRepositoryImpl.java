package com.stpunk47.spring_6_reactive_examples.repository;

import com.stpunk47.spring_6_reactive_examples.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {

    Person one = Person.builder().id(1).firstName("Ava").lastName("Adams").build();
    Person two = Person.builder().id(2).firstName("Bill").lastName("Brighton").build();
    Person three = Person.builder().id(3).firstName("Cindy").lastName("Corners").build();
    Person four = Person.builder().id(4).firstName("Dave").lastName("Derby").build();


    @Override
    public Mono<Person> getById(Integer id) {
        if (id == null) {
            return Mono.empty();
        }

        return findAll()
                .filter(person -> id.equals(person.getId()))
                .next()
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<Person> findAll() {

        return Flux.just(one, two, three, four);
    }
}
