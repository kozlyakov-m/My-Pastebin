[![Build Status](https://travis-ci.org/kozlyakov-m/My-Pastebin.svg?branch=master)](https://travis-ci.org/kozlyakov-m/My-Pastebin)
[![codecov](https://codecov.io/gh/kozlyakov-m/My-Pastebin/branch/master/graph/badge.svg)](https://codecov.io/gh/kozlyakov-m/My-Pastebin)
[![Heroku App Status](http://heroku-shields.herokuapp.com/my-pastebin)](https://my-pastebin.herokuapp.com/api)
[![Docker Image Version (latest by date)](https://img.shields.io/docker/v/kozlyakovm/my-pastebin)](https://hub.docker.com/r/kozlyakovm/my-pastebin)

#### Запуск:
`docker run -p 8080:8080 kozlyakov/my-pastebin:1.1`

**Подробную документацию API можно найти по адресу `/swagger-ui.html` ([Посмотреть на heroku](https://my-pastebin.herokuapp.com/swagger-ui.html))**  

#### Основные возможности: 
 + Посмотр последних 10 публичных "паст"  
 + Cоздание "паст" с указанием типа (PUBLIC - доступно всем, UNLISTED - доступно только по ссылке, PRIVATE - доступно только автору).
 + Для создания пасты от своего имени необходимо передавать в каждом запросе данные о пользователе (HTTP Basic authentication).    
 + Для создания нового пользователя необходимо отправить post-запрос на `/api/register` json, содержащий `login` и `password`.  
 + Зарегистрированным пользователям доступен раздел `/api/my-pastes` и редактирование своих "паст".
 + При создании пасты можно указать `expireDate` в формате `yyyy-MM-ddThh:mm` (например: 2020-06-07**T**21:59), после которой паста будет недоступна.  
 Время необходимо указывать для часового пояса UTC+0
