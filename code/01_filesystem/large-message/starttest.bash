# large_message example
# build
mvn clean install

# create the broker 
$AMQ_HOME/bin/artemis create --user guest --password guest --role guest --allow-anonymous --java-options -Djava.net.preferIPv4Stack=true ./target/server0

# copy the security configuration files to the broker dir
cp src/main/resources/jndi.properties target/server0/etc/

# run the Java example
mvn exec:java -Dexec.mainClass="org.apache.activemq.artemis.jms.example.LargeMessageExample" -Dexec.cleanupDaemonThreads=false -Dexec.args="./target/server0/ true"

