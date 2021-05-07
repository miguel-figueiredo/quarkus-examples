/*
 *
 * Talkdesk Confidential
 *
 * Copyright (C) Talkdesk Inc. 2021
 *
 * The source code for this program is not published or otherwise divested
 * of its trade secrets, irrespective of what has been deposited with the
 * U.S. Copyright Office. Unauthorized copying of this file, via any medium
 * is strictly prohibited.
 *
 */
package org.test.resteasyjackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

@ApplicationScoped
@Slf4j
public class UserRepository {

    public static final String ELASTIC_SEARCH_INDEX = "index-name";

    private final RestHighLevelClient elasticSearchClient;

    @Inject
    public UserRepository() {
        this.elasticSearchClient = restHighLevelClient();
    }

    public void persist(final User user) {
        try {
            elasticSearchClient.index(createIndexRequest(user), RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCompany(final User user) {
        log.info("Updating user {}", user);
        try {
            elasticSearchClient.update(createUpdateRequest(user), RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(restClientBuilder());
    }

    private static RestClientBuilder restClientBuilder() {
        return RestClient.builder(HttpHost.create("http://localhost:9200"))
            .setHttpClientConfigCallback(builder -> builder.setDefaultCredentialsProvider(createElasticSearchCredentials()));
    }

    private static CredentialsProvider createElasticSearchCredentials() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "password"));
        return credentialsProvider;
    }

    private static IndexRequest createIndexRequest(final User user) throws IOException {

        final XContentBuilder contentBuilder = XContentFactory.jsonBuilder()
            .startObject()
            .field("name", user.getName())
            .field("company", user.getCompany())
            .endObject();

        return new IndexRequest(ELASTIC_SEARCH_INDEX)
            .id(user.getId())
            .source(contentBuilder);
    }

    private static UpdateRequest createUpdateRequest(User user) throws IOException {

        final XContentBuilder contentBuilder = XContentFactory.jsonBuilder()
            .startObject()
            .field("company", user.getCompany()  )
            .endObject();

        return new UpdateRequest()
            .index(ELASTIC_SEARCH_INDEX)
            .id(user.getId())
            .doc(contentBuilder);
    }
}
