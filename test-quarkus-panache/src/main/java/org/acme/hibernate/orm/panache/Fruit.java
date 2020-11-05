package org.acme.hibernate.orm.panache;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Cacheable
public class Fruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 40, unique = true)
    public String name;

    public Fruit() {
    }

    public Fruit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
