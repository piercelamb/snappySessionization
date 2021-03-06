name := "snappySessionization"

version := "1.0"

scalaVersion := "2.11.12"

val workaround = {
  sys.props += "packaging.type" -> "jar"
  ()
}

libraryDependencies ++= Seq(
  "io.snappydata" % "snappydata-core_2.11" % "1.0.1",
  "io.snappydata" % "snappydata-cluster_2.11" % "1.0.1",
//  "org.apache.spark" %% "spark-streaming" % "2.1.1",
//  "org.apache.spark" %% "spark-streaming-kafka-0-8" % "2.1.1",
//  "org.apache.spark" %% "spark-sql" % "2.1.1",
//  "org.apache.spark" %% "spark-core" % "2.1.1"
  "org.apache.commons" % "commons-pool2" % "2.5.0",
  "org.apache.derby" % "derby" % "10.12.1.1" % Test
 // "org.apache.kafka" %% "kafka" % "0.8.2.2",
 // "org.apache.kafka" % "kafka-clients" % "0.8.2.2"
)