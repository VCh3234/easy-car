FROM openjdk:17
RUN mkdir /app
WORKDIR /app
COPY target/easy-car-1.0.jar /app
ENTRYPOINT java -jar /app/easy-car-1.0.jar