package org.acme.hibernate.orm.panache;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;

@QuarkusTest
class FruitRepositoryTest {

    @Inject
    FruitRepository fruitRepository;

    @Test
    @Transactional
    void persist() {
        Fruit fruit = new Fruit("Guava");

        fruitRepository.persist(fruit);
    }

    @Test
    void findStartedWith() {
        final List<Fruit> fruits = fruitRepository.findStartedBy("A");

        assertEquals(List.of("Apple"), fruits.stream().map(Fruit::getName).collect(toList()));
    }

    @Test
    void findAll() {
        final List<Fruit> fruits = fruitRepository.listAll();

        assertEquals(List.of("Cherry", "Apple", "Banana"), fruits.stream().map(Fruit::getName).collect(toList()));
    }

    @Test
    void findAllPaginated() {
        final List<Fruit> fruits = fruitRepository.findAll().page(0, 1).list();

        assertEquals(List.of("Cherry"), fruits.stream().map(Fruit::getName).collect(toList()));
    }
}