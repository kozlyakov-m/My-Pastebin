FROM gradle:jdk8
COPY . ./
RUN gradle stage
CMD java -jar build/libs/pastebin-0.0.1-SNAPSHOT.jar