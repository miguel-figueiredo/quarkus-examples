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
package test;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class JobReactiveRepository {

    @Inject
    io.vertx.mutiny.pgclient.PgPool client;

    public JobReactiveRepository(final PgPool client) {
        this.client = client;
    }

    public Uni<Long> persist(Job job) {
        return client.preparedQuery("INSERT INTO test.job (name) VALUES ($1) RETURNING (id)", Tuple.of(job.getName()))
            .map(pgRowSet -> pgRowSet.iterator().next().getLong("id"));
    }
}
