FROM openjdk:21

WORKDIR /app

COPY build/libs/user-client-api.jar /app/user-client-api.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "user-client-api.jar"]

# docker build -t [username]/[image_name]:[version] .
# docker push [username]/[image_name]:[version]