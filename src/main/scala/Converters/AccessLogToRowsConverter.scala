package Converters

import org.apache.spark.sql.streaming.StreamToRowsConverter
import org.apache.spark.sql.Row

/**
  * Created by plamb on 6/22/18.
  */
class AccessLogToRowsConverter extends StreamToRowsConverter with Serializable {

  //what an access log looks like:
 //"""14:40:33.251 [application-akka.actor.default-dispatcher-6] INFO  access - 0:0:0:0:0:0:0:1 - [22/Jun/2018:14:40:33 -0700] - "GET /assets/images/favicon/favicon.ico" - 200 - 22382"""

  override def toRows(message: Any): Seq[Row] = {
    val log = message.asInstanceOf[String]
    val field = log.split(" - ")
    Seq(
      Row.fromSeq(
        Seq(
          Option(field(1)).getOrElse(""), //ip
          Option(field(2)).getOrElse(""), //datetime
          Option(field(3)).getOrElse(""), //header
          Option(field(4)).getOrElse(""), //response
          Option(field(5)).getOrElse("") //size

    )))
  }
}
