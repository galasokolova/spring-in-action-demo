# Первый этап: сборка
FROM maven:3.8-openjdk-17 AS builder

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем только POM-файлы для загрузки зависимостей
COPY ../../pom.xml /app/pom.xml
COPY ./pom.xml /app/kafka-listener/pom.xml

# Копируем исходный код проекта
COPY ../../ /app

# Загрузка зависимостей
RUN mvn dependency:go-offline -B -f kafka-listener/pom.xml

# Собираем проект
RUN mvn clean package -Pdocker -f kafka-listener/pom.xml

# Второй этап: создание образа
FROM openjdk:17

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем скомпилированный JAR-файл из первого этапа
COPY --from=builder /app/kafka-listener/target/kafka-listener-0.0.1-SNAPSHOT.jar /app/kafka-listener.jar

# Копируем скрипты
COPY ./kafka-listener/scripts/entrypoint.sh /scripts/entrypoint.sh
COPY ./kafka-listener/scripts/wait-for-it.sh /scripts/wait-for-it.sh

# Даем права на выполнение скриптов
RUN chmod +x /scripts/entrypoint.sh /scripts/wait-for-it.sh

# Устанавливаем точку входа
ENTRYPOINT ["/scripts/entrypoint.sh"]
