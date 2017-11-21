package it.teamdigitale
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.{Logger, LoggerFactory}

import scala.util.Try

/**
 *
 * @param defaultLimit
 * @param defaultChunkSize
 */
class PhysicalDatasetController(
  sparkSession: SparkSession,
  kuduMaster: String,
  keytab: Option[String] = None,
  principal: Option[String] = None,
  keytabLocalTempDir: Option[String] = None,
  saltwidth:Option[Int] = None,
  saltbucket: Option[Int] = None,
  defaultLimit: Int = 128,
  defaultChunkSize: Int = 0
) {

  val openTSDB = new OpenTSDBController(sparkSession, keytab, principal, keytabLocalTempDir, saltwidth, saltbucket)
  val kudu = new KuduController(sparkSession, kuduMaster)
  val hdfs = new HDFSController(sparkSession)

  /**
   *
   * @param df
   * @param condition
   * @return a projection on the input dataframe based on condition
   */
  def where(df: Try[DataFrame], condition: String): Try[DataFrame] = ???

  def select(df: Try[DataFrame], columns: String): Try[DataFrame] = ???

  //TODO check how spark implements groupBy
  def groupBy(df: Try[DataFrame], columns: String): Try[DataFrame] = ???

  /**
   *
   * @param df
   * @param limit
   * @return
   */
  def limit(df: Try[DataFrame], limit: Int): Try[DataFrame] = df.map(_.limit(limit))

  /**
    * Starting point, uri encodes the databases where reading the data
    * @param uri
    * @return
    */
  def get(uri: String): Try[DataFrame] = ???
}

object PhysicalDatasetController {

  val masterUrl = "local[*]"

  val sparkConfig = new SparkConf()

  sparkConfig.set("spark.driver.memory", "128M")

  val alogger: Logger = LoggerFactory.getLogger(this.getClass)

  //FIXME adding all configuration
  //def apply() = new PhysicalDatasetController()

}
