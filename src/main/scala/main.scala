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

    snSession.sql(
        "CREATE STREAM TABLE IF NOT EXISTS SnappySiteAccessLogs (" +
        " ip string," +
        " datetime timestamp," +
        " header string," +
        " response int," +
        " size long) " +
        " USING directkafka_stream options (" +
        " STORAGELEVEL 'MEMORY_AND_DISK_SER_2', " +
        " rowConverter 'Converters.AccessLogToRowsConverter'," +
        " kafkaParams 'metadata.broker.list->localhost:9092;auto.offset.reset->smallest', " +
        " topics 'logs')"
    )

    snSession.start()
    snSession.awaitTermination()

    Thread.sleep(10000)
    val topRowsDF = snSession.sql("SELECT TOP 10 FROM SnappySiteAccessLogs")
    topRowsDF.show(Int.MaxValue)

  }

}
