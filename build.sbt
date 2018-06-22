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
  "org.apache.commons" % "commons-pool2" % "2.5.0",
  "org.apache.derby" % "derby" % "10.12.1.1" % Test
)