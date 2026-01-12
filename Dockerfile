# --- Estágio 1: Build ---
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app

# 1. Copia o script do wrapper e a pasta 'gradle' (essencial para o wrapper funcionar)
COPY gradlew .
COPY gradle gradle

# 2. Copia os arquivos de definição do projeto
COPY build.gradle.kts .
COPY settings.gradle.kts .

# 3. Dá permissão de execução ao gradlew
RUN chmod +x ./gradlew

# 4. Copia o código fonte
COPY src src

# 5. Roda o build (gera o .jar)
RUN ./gradlew bootJar --no-daemon -x test

# --- Estágio 2: Run ---
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copia o .jar gerado
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]