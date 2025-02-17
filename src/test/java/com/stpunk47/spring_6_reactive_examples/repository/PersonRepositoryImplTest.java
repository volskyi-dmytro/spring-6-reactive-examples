package com.stpunk47.spring_6_reactive_examples.repository;

import com.stpunk47.spring_6_reactive_examples.domain.Person;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void testFluxBlockFirst() {

        Flux<Person> personFlux = personRepository.findAll();

        Person person = personFlux.blockFirst();

        System.out.println(person.toString());
    }

    @Test
    void testFluxSubscriber() {

        Flux<Person> personFlux = personRepository.findAll();

        personFlux.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testFluxMap() {
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.map(Person::getFirstName)
                .subscribe(System.out::println);

    }

    @Test
    void testFluxToList() {
        Flux<Person> personFlux = personRepository.findAll();

        Mono<List<Person>> listMono = personFlux.collectList();

        listMono.subscribe(list -> {
            list.forEach(person -> System.out.println(person.getFirstName()));
        });      
                
    }

    @Test
    void testFilterOnName() {
        personRepository.findAll()
                .filter(person -> person.getFirstName().equals("Bill"))
                .subscribe(person -> System.out.println(person.getFirstName()));
    }

    @Test
    void testGetById() {
        Mono<Person> billMono = personRepository.findAll().filter(person -> person.getFirstName().equals("Bill"))
                .next();

        billMono.subscribe(person -> System.out.println(person.getFirstName()));
    }

    @Test
    void testFindPersonByIdNotFound() {

        Flux<Person> personFlux = personRepository.findAll();

        final Integer id = 8;

        Mono<Person> personMono =
                personFlux.filter(person -> person.getId() == id).next()
                        .switchIfEmpty(Mono.error(new RuntimeException("Person not found with id: " + id)));;

        personMono.subscribe(
                person -> System.out.println(person.toString()),  // onNext
                error -> System.out.println("Error: " + error.getMessage()),  // onError
                () -> System.out.println("Completed"));  // onComplete
    }

    @Test
    void testGetByIdFound() {
        // Test when person exists
        Mono<Person> personMono = personRepository.getById(3);

        assertTrue(personMono.hasElement().block());

    }

    @Test
    void testGetByIdNotFound() {
        // Test when person exists
        Mono<Person> personMono = personRepository.getById(6);

        assertFalse(personMono.hasElement().block());

    }


}