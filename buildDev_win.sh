export MAVEN_OPTS="-Xmx2048m -XX:MaxPermSize=1024m"
mvn clean package -Dgrails.env=dev -P win