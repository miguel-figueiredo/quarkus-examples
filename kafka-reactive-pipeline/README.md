Produce messages:

`seq 500 | kcat -b localhost:9092 -P -t words-in`

Consume messages:

`kcat -b localhost:9092 -C -t words-out`