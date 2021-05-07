#!/bin/bash

aws s3 mb s3://bucket --endpoint-url=http://localhost:4566
aws s3 cp /docker-entrypoint-initaws.d/test.parquet s3://bucket --endpoint-url=http://localhost:4566