import org.apache.spark.sql.types._
import org.apache.spark.sql.{SnappySession, SparkSession}
import org.apache.spark.streaming.{Duration, SnappyStreamingContext}

/**
  * Created by plamb on 6/20/18.
  */
object snappySessionization {

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder
      .appName("SnappyAccessLogs")
      .master("local[*]")
      .getOrCreate

    val snSession = new SnappyStreamingContext(spark.sparkContext, Duration(1))

    //what an access log looks like:
    //12:24:33.498 [application-akka.actor.default-dispatcher-3] INFO  access - 0:0:0:0:0:0:0:1 - -
    //[22/Jun/2018:12:24:33 -0700] "GET /" 200 21286

    snSession.sql(
              "CREATE STREAM TABLE SnappySiteAccessLogs (" +
              " ip string," +
              " datetime timestamp," +
              " header string," +
              " response int," +
              " size long) " +
              "USING kafka_stream options (" +
              "STORAGELEVEL 'MEMORY_AND_DISK_SER_2', " +
              "rowConverter 'Converters.AccessLogToRowsConverter' ," +
              "kafkaParams 'zookeeper.connect->localhost:2181;auto.offset.reset->smallest;group.id->myGroupId', " +
              "topics 'logs')"
    )

    snSession.start()
    snSession.awaitTermination()

    Thread.sleep(10000)
    val topRowsDF = snSession.sql("SELECT TOP 10 FROM SnappySiteAccessLogs")
    topRowsDF.show(Int.MaxValue)

  }

}
