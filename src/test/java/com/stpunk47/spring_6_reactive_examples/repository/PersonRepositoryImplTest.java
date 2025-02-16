package com.stpunk47.spring_6_reactive_examples.repository;

import com.stpunk47.spring_6_reactive_examples.domain.Person;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

class PersonRepositoryImplTest {

    PersonRepository personRepository = new PersonRepositoryImpl();

    @Test
    void testMonoByIdBlock() {

        Mono<Person> personMono = personRepository.getById(1);

        Person person = personMono.block();

        System.out.println(person.toString());

    }

    @Test
    void testGetByIdSubscriber() {

        Mono<Person> personMono = personRepository.getById(1);

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testMapOperation() {

        Mono<Person> personMono = personRepository.getById(2);

        personMono.map(Person::getFirstName)
                .subscribe(System.out::println);


    }
}