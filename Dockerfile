FROM openjdk:21

WORKDIR /app

COPY build/libs/notification-user-api.jar /app/notification-user-api.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "notification-user-api.jar"]

# docker build -t [username]/[image_name]:[version] .
# docker push [username]/[image_name]:[version]