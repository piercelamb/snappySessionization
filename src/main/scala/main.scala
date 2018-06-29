import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.sql.types._
//import org.apache.spark.streaming.{Seconds, StreamingContext}

import org.apache.spark.sql.{SnappySession, SparkSession}
//import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Duration, SnappyStreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

/**
  * Created by plamb on 6/20/18.
  */
object snappySessionization {
 //Pure Spark Code that works
 // val conf = new SparkConf().setMaster("local[*]").setAppName("NetworkWordCount")
  //    val stream = new StreamingContext(conf, Seconds(1))
  //    val kafkaParams: Map[String, String] = Map(
  //      "metadata.broker.list" -> "localhost:9092"
  //    )
  //
  //    val rawLogs = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](stream, kafkaParams, Set("logs"))
  //
  //    rawLogs.print()

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder
      .appName("SnappyAccessLogs")
      .master("local[*]")
      .getOrCreate

    val snSession = new SnappyStreamingContext(spark.sparkContext, Duration(1))

    snSession.sql("drop table if exists SnappySiteAccessLogs")

    snSession.sql(
        "CREATE STREAM TABLE IF NOT EXISTS SnappySiteAccessLogs (" +
        " ip string," +
        " datetime timestamp," +
        " header string," +
        " response int," +
        " size long) " +
        " USING kafka_stream options (" +
        " rowConverter 'Converters.AccessLogToRowsConverter'," +
        " kafkaParams 'zookeeper.connect->localhost:2181;group.id->myGroupId;auto.offset.reset->smallest', " +
        " topics 'logs:1')"
    )

    snSession.start()


    Thread.sleep(10000)
    var topRowsDF = snSession.sql("SELECT * FROM SnappySiteAccessLogs")
    topRowsDF.show()
    Thread.sleep(10000)
    topRowsDF = snSession.sql("SELECT * FROM SnappySiteAccessLogs")
    topRowsDF.show()
    Thread.sleep(10000)
    topRowsDF = snSession.sql("SELECT * FROM SnappySiteAccessLogs")
    topRowsDF.show()
    Thread.sleep(10000)
    topRowsDF = snSession.sql("SELECT * FROM SnappySiteAccessLogs")
    topRowsDF.show()



  }

}
