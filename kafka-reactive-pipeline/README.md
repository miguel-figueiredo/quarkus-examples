Run with custom mutiny buffer size:

`mvn quarkus:dev -Djvm.args="-Dmutiny.buffer-size.s=10"`


Produce messages:

`seq 100 | kcat -b localhost:9092 -P -t words-in`

Consume messages:

`kcat -b localhost:9092 -C -t words-out`

Available topics:

words-in -> words-out
words-delayed-in -> words-delayed-out
words-split-in -> words-split-out
words-emitter-in -> words-emitter-out
words-manual-ack-in -> words-manual-ack-out
words-manual-ack-emitter-in -> words-manual-ack-emitter-out