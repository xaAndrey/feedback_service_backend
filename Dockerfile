### STAGE 1: Builder ###
FROM openjdk:20-jdk as builder
WORKDIR ./app/build
COPY ./src ./src
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN ./mvnw clean install -Dmaven.test.skip=true

### STAGE 2: Runner ###
FROM openjdk:20-jdk
WORKDIR ./app
COPY --from=builder /app/build/target/*.jar ./app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]