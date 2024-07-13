FROM openjdk:21

WORKDIR /app

COPY infrastructure/build/libs/usr-client-api.jar /app/usr-client-api.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "usr-client-api.jar"]

# docker build -t [username]/[image_name]:[version] .
# docker push [username]/[image_name]:[version]