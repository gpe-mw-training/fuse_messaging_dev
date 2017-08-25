# clustered queue example
# build example
mvn install

# start and run the 1st broker
$AMQ_HOME/bin/artemis create --user guest --password guest --role guest --allow-anonymous --java-options -Djava.net.preferIPv4Stack=true ./target/server0
cp src/main/resources/activemq/server0/*.* target/server0/etc/
"./target/server0/bin/artemis-service" start

# start and run the 2nd broker
$AMQ_HOME/bin/artemis create --user guest --password guest --role guest --allow-anonymous --java-options -Djava.net.preferIPv4Stack=true ./target/server1
cp src/main/resources/activemq/server1/*.* target/server1/etc/
"./target/server1/bin/artemis-service" start

# run example
mvn exec:java -Dexec.mainClass="com.gpte.redhat.ClusteredQueueExample" -Dexec.cleanupDaemonThreads=false
