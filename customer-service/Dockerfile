FROM openjdk:8
MAINTAINER Vinayak Chaturvedi vinayak.chaturvedi96@gmail.com

# Install python and ansible
RUN ["apt-get", "update"]
RUN ["apt-get", "-y", "install", "openssh-server","sshpass"]

ENV SHARE_DIR /usr/local/share
WORKDIR .
# copy jar file and resource modules
COPY ./customer-service/target/customer-service-0.0.1-SNAPSHOT.jar ./
COPY ./estock-core/target/estock-core-0.0.1-SNAPSHOT.jar ./
COPY ./customer-service/src/main/resources/ ./src/main/resources/

#vinayak@vinayak:~/IdeaProjects/EStock$ docker build -f customer-service/Dockerfile -t vinayak96/estock_customerservice .