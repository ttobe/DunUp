FROM amazoncorretto:17

LABEL authors="ahdrmfgur12"

ARG JAR_FILE=build/libs/dunup-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} docker-springboot.jar

ENTRYPOINT ["java", "-jar", "/docker-springboot.jar", ">", "app.log"]