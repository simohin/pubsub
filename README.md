# Для запуска:

### Собрать

`gradle :subscriber:build && gradle :publisher:build`

### Запустить

`docker-compose up -d --build`

### Логи

`docker-compose logs -f publisher`
`docker-compose logs -f supscriber`

### Остановить

`docker-compose down`
