1) Что хотим : получить 3 слоя: Клиент(Postman) взаимодействует через Http со слоем бэкэнда(Spring Boot) который в свою очередь обращается к слою бд(Postgresql в Docker) посредством CRUD методов.
2) Вся бизнес логика будет в сервисе(из контроллера делегируем туда, бэст практис), все контроллеры в контроллере.
3) Используем в методах параметризованный  ResponseEntity для подробной информации об ответе(код, хедеры). Spring сам (де)сериализует из RequestBody в Json и обратно в объект(за счет подкапотного использования фреймворка Jackson)
4) Поскольку в браузере можно тестировать только get запросы, будем тестировать в postman(удобная работа с телом, кодами ответа, сохранение и изменение запросов)
5) Postgres в качестве бд будем поднимать через Docker(он всегда в docker hub, качаем image через docker pull, запускаем через docker run. 
поскольку в контейнере Postgress представлено как приложение, то оно будет слушать определенный порт, поэтому добавляем -p 5432(порт хостовой машины):5432(порт контейнера).
В качестве клиента для подключения используем внутренний в Intellij idea ultimate.
![postgres check](https://github.com/user-attachments/assets/f5918800-b8ed-4150-bd33-3bfbe3f26ad9)

![idea db](https://github.com/user-attachments/assets/0deda25d-c43d-496d-9768-e5470c0f77eb)

6) Для работы с бд наследуемся от параметризованного JpaRepository(spring boot data jpa), чтобы получить огромное количество описанных методов.
7) Кастомные запросы к бд будем писать при помощи @Query(с синтаксисом JPQL,можно и через native), идет с Spring data jpa, размещается над методом.
8) Валидация и ошибки: используем для проверки корректности вводимых и сохраняемых клиентом данных + добавим понятные ответы и статус-коды для интуитивно понятного API.
9) Ошибки: При помощи spring AOP добавляем сквозную обработку ошибок, чтобы избавиться от обработки в каждом методе(@controllerAdvice + @ExceptionHandler, возвращать будем параметризированный ResponseEntity c понятным для дебага dto внутри).
![lo cancelled res](https://github.com/user-attachments/assets/ac31d59e-0b69-407e-ba86-0cbe46307e6b)

10) Валидация: через Spring Boot starter validation валидируем все со стороны клиента, за счет аннотации @Valid и конкретных constraint'oв.
 Отдельно валидация со стороны бизнес-логики и отдельно со стороны бд через констрэйнты Hibernate(bad practice, использовано для тестирования). Примеры успешных:
![MyCollages](https://github.com/user-attachments/assets/e714a857-53f6-4dca-85d7-fdd2f5bb3076)
Примеры всевозможных ошибок:
![MyCollages (2)](https://github.com/user-attachments/assets/f126e88f-6a14-474a-8108-695904bc7d5a)
![MyCollages (3)](https://github.com/user-attachments/assets/f3ddd46f-868f-4f5f-92ea-d6628588d829)
![MyCollages (4)](https://github.com/user-attachments/assets/34477fcf-1181-48ca-be63-1ab99125c211)

11) Чтобы все бронирования не выгружались при поиске конфликтов через findAll в память приложения, оптимизируем это через JPQL запрос(Пагинация через Pageable)
![query](https://github.com/user-attachments/assets/66f47d64-006e-4668-81da-6ba897320b10)
![2](https://github.com/user-attachments/assets/0ff91ba9-9c52-45c4-b36a-2dad82789e7e)
 
**TO DO : В в валидации в дто добавить message в каждый констрэйнт , а также везде констрэйт @Size(min,max,message)**
**Поскольку будет выкидываться MethodArgumentNotValidException, добавить обработчик в соответвствующем  ExeptionHandler. Добавить файл msg.properties и брать сообщения оттуда, используя {}**

**Добавить на ручки @ResponseStatus(HttpStatus.нужный статус). но через responseentity более гибкое управление**






