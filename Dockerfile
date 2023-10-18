FROM openjdk:17
ADD target/Ylab_University-1.0-SNAPSHOT.jar wallet.jar
ENTRYPOINT ["java", "-jar", "wallet.jar"]