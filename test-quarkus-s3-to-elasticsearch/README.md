# test-s3-to-elasticsearch project

Create parquet from CSV:

```
pip3 install pyarrow csv2parquet
csv2parquet test.csv
```

Run

```
docker-compose up
curl http://localhost:8080/consume
```

The Elastic Search index should have an updated company.