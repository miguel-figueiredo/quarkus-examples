package org.acme.hibernate.orm.panache;

import static org.junit.jupiter.api.Assertions.*;

import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;

@QuarkusTest
class TreeRepositoryTest {

    @Inject
    EntityManager em;

    @Test
    @Transactional
    void persistTree() {
        Fruit fruit = new Fruit("Orange");
        Tree tree = new Tree(List.of(fruit));

        new TreeRepository().persist(tree);

        System.err.println(tree.id);
        for(Fruit f : tree.fruits){
            System.err.println(f.id);
        }

        em.clear();

        final Tree t = new TreeRepository().findById(1L);

        System.err.println(t.getFruits());

        final Fruit f = new FruitRepository().findById(1L);
        System.err.println(f.id);
    }
}