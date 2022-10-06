Run with custom mutiny buffer size:

`mvn quarkus:dev -Djvm.args="-Dmutiny.buffer-size.s=10"`


Produce messages:

`seq 100 | kcat -b localhost:9092 -P -t words-in`

Consume messages:

`kcat -b localhost:9092 -C -t words-out`