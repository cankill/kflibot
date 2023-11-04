# Build Step
FROM ubuntu:jammy

ARG DB_ROOT_PASSWORD
ARG DB_HOST
ARG DB_NAME

ENV DB_ROOT_PASSWORD=$DB_ROOT_PASSWORD
ENV DB_HOST=$DB_HOST
ENV DB_NAME=$DB_NAME

RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install -y cron
RUN apt-get install -y mysql-client
RUN apt-get install -y curl

RUN echo "UTC" > /etc/timezone \
      && rm -rf /opt/jdk/*src.zip /var/lib/apt/lists/*

COPY scripts/download.sh /download.sh
RUN chmod +x /download.sh

RUN echo "DB_NAME=$DB_NAME" > /etc/environment
RUN echo "DB_HOST=$DB_HOST" >> /etc/environment
RUN echo "DB_ROOT_PASSWORD=$DB_ROOT_PASSWORD" >> /etc/environment

RUN touch /var/log/cron.log

COPY scripts/download_job /etc/cron.d/download_job
RUN chmod 0644 /etc/cron.d/download_job
#RUN sed -i s/\$password/$DB_ROOT_PASSWORD/ /etc/cron.d/download_job
#RUN sed -i s/\$host/$DB_HOST/ /etc/cron.d/download_job
#RUN sed -i s/\$db_name/$DB_NAME/ /etc/cron.d/download_job
RUN crontab /etc/cron.d/download_job

CMD cron && tail -f /var/log/cron.log
