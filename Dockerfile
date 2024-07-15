FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY target/order-1.0.0.jar order-1.0.0.jar

EXPOSE 8082
CMD ["java","-jar","order-1.0.0.jar"]