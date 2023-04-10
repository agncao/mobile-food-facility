* to run the app you need to have docker and docker-compose installed

```
$ docker-compose up -d
$ docker build -t app:1.0 .
$ docker run -d -p 8080:8081 app:1.0

```

* to return a set of food trucks, go to http://localhost:8080/api/food/list