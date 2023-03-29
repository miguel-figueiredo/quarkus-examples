package org.acme;

import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.flywaydb.core.Flyway;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class FlywayMigration {

    @ConfigProperty(name = "quarkus.datasource.jdbc.url")
    String datasourceUrl;
    @ConfigProperty(name = "quarkus.datasource.username")
    String datasourceUsername;
    @ConfigProperty(name = "quarkus.datasource.password")
    String datasourcePassword;

    public void runMigration(@Observes StartupEvent event) {
        Flyway flyway = Flyway.configure().dataSource(datasourceUrl, datasourceUsername, datasourcePassword).load();
        flyway.migrate();
    }
}