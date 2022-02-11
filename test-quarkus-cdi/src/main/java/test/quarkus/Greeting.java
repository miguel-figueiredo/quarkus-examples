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

import java.util.UUID;

public class Greeting {
    private final String id;

    public Greeting() {
        id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return id;
    }
}
