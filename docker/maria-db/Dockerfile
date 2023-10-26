#FROM eclipse-temurin:17.0.8_7-jdk-jammy
FROM mariadb:latest
#ENV USER=sysadm
#ARG HOME_DIR=/home/$USER
#ARG USER_ID=1000
#ARG GROUP_ID=1000

RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install -y sudo

#RUN groupadd -r $USER -g $GROUP_ID && useradd --no-log-init -r -g $USER $USER -m -u $USER_ID \
#      && chown -R $USER:$USER $HOME_DIR \
#      && chmod u+rwx $HOME_DIR \
#      && usermod -aG sudo sysadm && echo "sysadm ALL=(ALL) NOPASSWD: ALL" > /etc/sudoers.d/sysadm \
#      && chmod 0440 /etc/sudoers.d/sysadm \
#      && echo "${USER}:change_me" | chpasswd
#

RUN apt-get install -y inetutils-ping
RUN apt-get install -y curl
RUN apt-get install -y netcat
RUN apt-get install -y zip

RUN echo "UTC" > /etc/timezone \
      && rm -rf /opt/jdk/*src.zip /var/lib/apt/lists/*

COPY scripts/download.sh /download.sh
RUN /download.sh

#USER $USER:$USER
#WORKDIR $HOME_DIR