FROM openjdk:21-jdk

WORKDIR /app

ARG JAR_FILE
ENV SPRING_PROFILES_ACTIVE=dev

COPY ${JAR_FILE} /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
