FROM openjdk:17

WORKDIR /app

COPY target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]

## Выбор базового образа с Java
 #FROM openjdk:17
 #
 ## Создание директории приложения
 #WORKDIR /app
 #
 ## Копирование собранного jar файла в контейнер
 #COPY target/*.jar app.jar
 #
 ## Запуск приложения
 #CMD ["java", "-jar", "app.jar"]