# HomeWorkRealPostgres
 Домашнее задание по теме "Воркшоп: реальный Postgres" (ProductStar)

# Постановка задачи
Домашнее задание:
Для того, чтобы выполнить домашнее задание, скачай с Github весь проект по ссылке.
1. Установи Docker, как показано в воркшопе.
2. По примеру того, как мы писали методы с аннотацией @GetRequest, в классе DogsAppServices, создай метод, который возвращает топ-10 пользователей, у которых больше всего выгулов собак.
Другими словами этот метод должен группировать все строки из таблицы WALKS по логину пользователей и выбирать топ-10 таких логинов, у которых строк больше всего.
Обрати внимание:
- Метод должен быть доступен по адресу `/max_walks`, и отвечать на GET-запрос.
- Метод не должен принимать никаких аргументов.
- Метод должен использовать объект entireProjectRepo и вызывать на нем новый, тобой же реализованный метод getTop10WalkingUsersFromDb с новым SQL-запросом.
- Новый SQL-запрос должен возвращать список из 10 (или менее, если пользователей < 10) строк. Каждая строка – объект класса String. Строки – логины пользователей, у которых количество выгулов в Топ-10.
- В классе EntireProjectRepo для тебя уже подготовлен шаблон метода getTop10WalkingUsersFromDb, нужно лишь написать верный SQL-запрос и убедиться, что он работает верно.
3. Выполни команду maven clean, maven install.
4. Если удалось поднять базу данных в Докере и maven clean, maven install не выдал ошибок, то:
- Перезапусти приложение.
- Убедись с помощью утилиты curl, что нововведение работает.
- Выполни домашнее задание на локальном компьютере и отправь 2 получившихся файла "DogsAppServices.java" и "EntireProjectRepo.java" архивом на проверку.
## Подключенные зависимости и плагины(были в исхдном проекте)
- spring-boot-starter-data-jdbc - стартер для использования Spring Data JDBC.
- spring-boot-starter-web - Starter for building web, including RESTful, applications using Spring MVC. Uses Tomcat as the default embedded container
- postgresql - PostgreSQL JDBC drive
- spring-boot-starter-test - tarter for testing Spring Boot applications with libraries including JUnit Jupiter, Hamcrest and Mockito
- hibernate-core - это часть фреймворка Hibernate, который используется для связывания объектов Java с таблицами базы данных.
## Описание основных файлов
- main/java/com/roks/realpostgres/repository/Repository.java - основные методы уровня DAO
- main/java/com/roks/realpostgres/repository/Dog.java - класс объекта собаки
- main/java/com/roks/realpostgres/repository/User.java - класс объекта пользователя(выгульщика собак)
- main/java/com/roks/realpostgres/repository/Walk.java - класс объекта прогулки собаки
- main/java/com/roks/realpostgres/DogsAppServices.java - основной класс рестконтроллера
- main/java/com/roks/realpostgres/RealPostgresApplication - основной запускаемый класс
- main/resources/static/migration.sql - пример изменения первичного ключа у таблицы с зависимыми таблицами в одной транзакции в PostgreSQL
- curl-requests.txt - файл с примерами обращения к REST-сервису
## Примечания
- Для доработки и сдачи домашнего задания перенёс модуль real-postgres в отдельный проект HomeWorkRealPostgres
- Рассхождения текста ДЗ с реальным проектом - вместо класса EntireProjectRepo есть класс Repository. По косвенным признакам - это об одном и том же.  