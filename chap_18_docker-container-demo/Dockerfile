FROM openjdk:17

# Указание имени JAR-файла
ARG JAR_FILE=target/chap_18_docker-container-demo-0.0.1-SNAPSHOT.jar

# Копируем JAR-файл в контейнер
COPY ${JAR_FILE} /app/app.jar

# Установка точки входа для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
