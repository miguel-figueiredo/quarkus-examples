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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.s3a.Constants;

@ApplicationScoped
@Slf4j
public class UserReader {

    private final ObjectMapper mapper;

    public UserReader() {
        this.mapper = new ObjectMapper();
    }

    public Stream<User> stream() {
        try {
            return new ParquetRecordReader(getConfiguration(), getPath()).stream()
                .map(this::toJson)
                .map(this::toUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJson(String str) {
        return ("{" + str.replaceAll("(\\w+)", "\"$1\"").replaceAll("\\n", ",") + "}")
            .replace(",}", "}");
    }

    private User toUser(final String json) {
        log.info("Convert: {}", json);
        try {
            return mapper.readValue(json, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private org.apache.hadoop.fs.Path getPath() {
        return new org.apache.hadoop.fs.Path(URI.create("s3a://bucket/test.parquet"));
    }

    private Configuration getConfiguration() {
        final Configuration conf = new Configuration();
        conf.set(Constants.ENDPOINT, "http://localhost:4566");
        conf.set(Constants.ACCESS_KEY, "s3");
        conf.set(Constants.SECRET_KEY, "s3");
        conf.set(Constants.MAXIMUM_CONNECTIONS, "20");
        conf.set(Constants.MAX_THREADS, "2");
        conf.set(Constants.KEEPALIVE_TIME, "300");
        conf.set(Constants.PATH_STYLE_ACCESS, "true");
        conf.set(Constants.SECURE_CONNECTIONS, "false");
        conf.set(Constants.CHANGE_DETECT_MODE, Constants.CHANGE_DETECT_MODE_NONE);
        return conf;
    }
}
