FROM eclipse-temurin:21-jdk as builder

ADD . /code
WORKDIR /code

RUN chmod +x /code/gradlew
RUN cd code && ./gradlew build

FROM eclipse-temurin:21-jre
COPY --from=builder /code/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
