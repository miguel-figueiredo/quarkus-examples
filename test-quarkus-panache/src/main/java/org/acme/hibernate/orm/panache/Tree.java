/*
 *
 * Talkdesk Confidential
 *
 * Copyright (C) Talkdesk Inc. 2020
 *
 * The source code for this program is not published or otherwise divested
 * of its trade secrets, irrespective of what has been deposited with the
 * U.S. Copyright Office. Unauthorized copying of this file, via any medium
 * is strictly prohibited.
 *
 */
package org.acme.hibernate.orm.panache;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    List<Fruit> fruits;

    public Tree() {
        // Required by Hibernate
    }

    public Tree(final List<Fruit> fruits) {
        this.fruits = fruits;
    }

    public List<Fruit> getFruits() {
        return fruits;
    }
}
