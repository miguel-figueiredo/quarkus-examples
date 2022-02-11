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
package test.quarkus;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;
import java.util.Optional;

@ConfigMapping(prefix = "aws")
public interface AwsConfiguration {

    @WithName("access.key.id")
    Optional<String> accessKeyId();

    @WithName("secret.access.key")
    Optional<String> secretAccessKey();

    @WithName("region")
    Optional<String> region();

    @WithName("s3.endpoint.override")
    Optional<String> s3EndpointOverride();

    @WithName("sqs.endpoint.override")
    Optional<String> sqsEndpointOverride();

    @WithName("transcribe.endpoint.override")
    Optional<String> transcribeEndpointOverride();
}
