FROM openjdk:19-jdk-alpine

# Set the working directory
WORKDIR /app

# Install required packages
RUN apk add --no-cache curl unzip

# Specify the Maven version
ENV MAVEN_VERSION=3.9.9

# Download and install Maven
RUN curl -fsSL "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/${MAVEN_VERSION}/apache-maven-${MAVEN_VERSION}-bin.zip" -o /tmp/maven.zip \
    && unzip /tmp/maven.zip -d /opt/ \
    && ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven \
    && rm /tmp/maven.zip

# Set environment variables for Maven
ENV MAVEN_HOME=/opt/maven
ENV PATH="${MAVEN_HOME}/bin:${PATH}"

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Expose the port
EXPOSE 8080

# Run the JAR file with the correct name
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "target/videoService-0.0.1-SNAPSHOT.jar"]
