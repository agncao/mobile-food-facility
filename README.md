* to run the app you need to have docker and docker-compose installed

```
$ docker-compose up -d
$ docker build -t app:1.0 .
$ docker run  app:1.0

```

* to return a set of food trucks, go to http://localhost:8081/api/food/list