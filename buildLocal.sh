export MAVEN_OPTS="-Xmx1204m -XX:MaxPermSize=512m"
mvn clean package -Dgrails.env=local -P win

