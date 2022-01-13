FROM openjdk:11-jre-slim-bullseye
RUN adduser --system --group springdocker
USER springdocker:springdocker
ARG JAR_FILE=app/build/libs/ping-service.jar
COPY ${JAR_FILE} ping-service.jar
ENTRYPOINT ["java","-jar", \
#"-DPORT=8080", \
#"-DSPRING_PROFILES_ACTIVE=docker", \
#"-DTZ=America/Denver", \
"/ping-service.jar"]
# Environment variables to be prdvided in docker-compose
