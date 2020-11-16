FROM openjdk:11
RUN apt update && apt install -y jq
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
HEALTHCHECK --start-period=15s --interval=1m --timeout=10s --retries=3 \
            CMD curl --silent --fail --request GET http://localhost:8081/actuator/health \
                | jq --exit-status '.status == "UP"' || exit 1
