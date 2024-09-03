FROM eclipse-temurin:21

WORKDIR /app

COPY ./docker/utils/wait_for_it.sh /usr/local/bin/wait_for_it.sh
RUN chmod +x /usr/local/bin/wait_for_it.sh

COPY target/*.jar rare-book-api.jar

EXPOSE 8080

CMD ["wait_for_it.sh", "mysql:3306", "--timeout=30", "--", "java", "-jar", "/app/rare-book-api.jar"]
