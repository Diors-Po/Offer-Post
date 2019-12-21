FROM java:8
EXPOSE 8080
ADD offer-post-0.0.1-SNAPSHOT.jar /root
WORKDIR /root
ENTRYPOINT ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "offer-post-0.0.1-SNAPSHOT.jar"]