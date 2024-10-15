# Используем образ OpenJDK 17
FROM openjdk:17

# Указание имени JAR-файла (измените путь, если необходимо)
ARG JAR_FILE=target/chap_18_google-cloud-0.0.1-SNAPSHOT.jar

# Копируем JAR-файл в контейнер
COPY ${JAR_FILE} /app/app.jar

# Установка рабочей директории (опционально, для лучшей структуры)
WORKDIR /app

# Установка точки входа для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

