[![Build Status](https://app.travis-ci.com/stanovov/job4j_url_shortcut.svg?branch=master)](https://app.travis-ci.com/stanovov/job4j_url_shortcut)
[![codecov](https://codecov.io/gh/stanovov/job4j_url_shortcut/branch/master/graph/badge.svg)](https://codecov.io/gh/stanovov/job4j_url_shortcut)

![](https://img.shields.io/badge/Maven-=_3-red)
![](https://img.shields.io/badge/Java-=_17-orange)
![](https://img.shields.io/badge/Spring-=_5-darkorange)
![](https://img.shields.io/badge/PostgerSQL-=_9-blue)
![](https://img.shields.io/badge/Checkstyle-lightgrey)

# job4j_url_shortcut

+ [О проекте](#О-проекте)
+ [Технологии](#Технологии)
+ [Использование](#Использование)
+ [Контакты](#Контакты)

## О проекте

RESTful веб-приложение коротких ссылок. Чтобы обеспечить безопасность пользователей, все ссылки на сайте заменяются
ссылками на наш сервис. Имеются модели данных: сайт и url. Чтобы начать пользоваться сервисом, необходимо
зарегистрировать сайт. После регистрации сайта, сервис выдаст токен. По токену можно конвертировать новые ссылки (url) в
короткие ключи. После чего, любой пользователь, может быть перенаправлен на необходимый урл по ключу.

## Технологии

+ **Java 17**, **Spring (Boot, Data JPA, Web, Security (JWT))**;
+ **PostgreSQL**, **Liquibase**;
+ Сборщик проектов **Maven**;
+ Непрерывная интеграция - **Travis CI**;
+ Инструмент для анализа стиля кода - **Checkstyle**;

## Использование

### Сайты

`GET /site/registration` - регистрирует новый сайт в системе и выдает логин и пароль для дальнейшего получения токенов.

`POST /login` - позволяет получить токен для сайта (в теле передается выданные логин и пароль)

### URL-ссылки

`POST /url/convert` - необходимо использовать токен в заголовке. Конвертирует ссылку, возвращая ключ, с помощью которого можно перейти непосредственно на переданную ссылку

`GET /redirect/{code}` - используется без токена. Позволяет перейти по ключу с ассоциированной по нему ссылкой

`GET /statistic` - необходимо использовать токен в заголовке. Возвращает все ссылки и количество переходов по ним.

## Контакты

Становов Семён Сергеевич

Email: sestanovov@gmail.com

Telegram: [@stanovovss](https://t.me/stanovovss)