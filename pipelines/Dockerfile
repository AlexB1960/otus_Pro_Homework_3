FROM maven:3.9.14-eclipse-temurin-21

USER root

ENV PROFILE="api"
#PROFILE="api" используется далее в pipeline для имени папки с тестами и в плейбуке для названия контейнера

#RUN mkdir -p /otus-allure
WORKDIR /api_tests
COPY . .

#ENTRYPOINT [ "./entrypoint.sh" ]
ENTRYPOINT ["sh", "-c", "mvn test -P $PROFILE" ]
#ENTRYPOINT ["sh", "-c", "mvn clean test"]