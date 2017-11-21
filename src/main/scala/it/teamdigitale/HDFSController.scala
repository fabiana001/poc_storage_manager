package it.teamdigitale

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.slf4j.{Logger, LoggerFactory}

import scala.util.Try

class HDFSController(sparkSession: SparkSession) {

  val alogger: Logger = LoggerFactory.getLogger(this.getClass)

  def readData(path: String, format: String): Try[DataFrame] =  {
    Try{

    }
    ???
  }
}
