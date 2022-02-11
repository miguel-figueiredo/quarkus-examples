/*
 *
 * Talkdesk Confidential
 *
 * Copyright (C) Talkdesk Inc. 2022
 *
 * The source code for this program is not published or otherwise divested
 * of its trade secrets, irrespective of what has been deposited with the
 * U.S. Copyright Office. Unauthorized copying of this file, via any medium
 * is strictly prohibited.
 *
 */
package test.quarkus;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@ApplicationScoped
public class TestLister {

    @Inject
    TestEntityRepository repository;

//    @Transactional
    @ActivateRequestContext
    public void process() {
        final List<TestEntity> resultList = repository.findAll();
        System.out.println(resultList);
    }
}
