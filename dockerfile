FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY --from=build /app/target/blogplatform-0.0.1-SNAPSHOT.jar /app/
RUN java -Djarmode=layertools -jar blogplatform-0.0.1-SNAPSHOT.jar extract

FROM maven:3.9-eclipse-temurin-21
WORKDIR /app
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies ./
COPY --from=builder /app/application ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]