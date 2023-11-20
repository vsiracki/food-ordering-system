### KAFKA ###
```shell 
 docker-compose -f common.yml -f zookeeper.yml up
```
```shell 
 docker-compose -f common.yml -f kafka_cluster.yml up
```
```shell 
 docker-compose -f common.yml -f elastic_cluster.yml up
```
### INIT KAFKA TOPICS(CALL only ONCE) ###
```shell 
 docker-compose -f common.yml -f init_kafka.yml up
```

```shell 
 docker ps
```
```shell 
 docker stop $(docker ps -a -q)
```
