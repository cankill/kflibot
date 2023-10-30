FROM mariadb:latest

RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install -y curl

RUN echo "UTC" > /etc/timezone \
      && rm -rf /opt/jdk/*src.zip /var/lib/apt/lists/*

COPY scripts/download.sh /download.sh
RUN /download.sh
