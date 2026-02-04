FROM bellsoft/liberica-runtime-container:jre-25-cds-glibc

WORKDIR /app

COPY build/libs/*.jar /app/app.jar
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh \
    && mkdir -p /app/logs \
    && chown -R 10001:10001 /app/logs

USER 10001

EXPOSE 8080

ENTRYPOINT ["/app/entrypoint.sh"]
