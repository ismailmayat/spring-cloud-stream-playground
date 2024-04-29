# Spring Cloud Stream + Boot + Actuator + Confluent Cloud

## CREATE TOPICS 

```sh
$ confluent kafka topic create batch-in           
Created topic "batch-in".
```

```sh
 $ confluent kafka topic create batch-out
Created topic "batch-out".
```

## Metric Endpoints 

```sh  
$ curl http://localhost:8080/actuator/metrics/kafka.consumer.fetch.manager.records.consumed.total | jq
``` 