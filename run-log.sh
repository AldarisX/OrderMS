bash ./mvnw clean
nohup setsid bash ./mvnw spring-boot:run > /dev/null > /dev/null 2>&1 &