Для запуска необходима только Java (используются встроенные 
Apache Tomcat и субд H2)

Для получения последних 10 публичных паст отправить
GET запрос на http://localhost:8080/
Пример ответа в формате JSON:  
`[{"id":3,
"text":"something",
"hash":"7d0f1446-7625-40f3-b3fe-3a9898ad6706",
"expireDate":"2020-12-31T00:00:00.000+0000",
"private":false},`
`{"id":1,"text":"text",
"hash":"02fa4eee-3b62-4fe4-ad37-d58b6fbaf1b5",
"expireDate":"2020-12-31T00:00:00.000+0000",
"private":false}]`

Для для добавления пасты отправить
POST запрос на http://localhost:8080/. В теле запроса
необходимо в формате JSON передать параметры
- text — текст пасты
- isPrivate — true для приватной пасты и false для публичной
- expireDate — строка с датой в формате yyyy-MM-dd
После успешного добавления сервер вернет HTTP-статус 
"201 CREATED"и JSON:  
`{ "message": "Paste has been saved",
 "hash": "7d0f1446-7625-40f3-b3fe-3a9898ad6706"`

Для получения  пасты по сссылке выполнить GET запрос
по адресу http://localhost:8080/{hash}  
Структура ответа:
`{"id":2,"text":"text",
 "hash":"02fa4eee-3b62-4fe4-ad37-d58b6fbaf1b5",
 "expireDate":"2020-12-31T00:00:00.000+0000",
 "private":true}`
 
 Коды ошибок:  
 200 OK — запрос выполен успешно  
 201 CREATED — новая паста успешно создана  
 404 NOT FOUND — паста по данному адресу не найдена  
 400 BAD REQUEST — в теле post-запроса отсутствуют обязательные
 параметры (text, isPrivate, expireDate), либо указан неправильный 
 формат даты, либо значение isPrivate не является true или false