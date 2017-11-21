package it.teamdigitale
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.{Logger, LoggerFactory}
import scala.util.{Failure, Success, Try}

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
  saltwidth: Option[Int] = None,
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
   * @return a valid condition
   */
  //FIXME validate the condition
  def where(df: Try[DataFrame], condition: String): Try[DataFrame] = {
    df.map(_.where(condition))
  }

  def select(df: Try[DataFrame], column: String): Try[DataFrame] =
    df.flatMap { d =>
      if (d.columns.toSet.contains(column)) Success(d.select(column))
      else Failure(new IllegalArgumentException(s"Column $column not found in ${d.columns}"))
    }

  /**
    *
    * @param df the dataframe
    * @param column the column to aggregate
    * @param aggregationFun a valid aggregation function in Set("count", "max", "mean", "min", "sum")
    * @return
    */
  def groupBy(df: Try[DataFrame], column: String, aggregationFun: String): Try[DataFrame] = {
    for {
      aggregation <- AggregationsValidator.validate(aggregationFun)
      d <- df
    } yield d.groupBy(column).agg(column -> aggregation)
  }

  /**
   *
   * @param df
   * @param limit
   * @return
   */
  def limit(df: Try[DataFrame], limit: Int = defaultLimit): Try[DataFrame] =
    df.map(_.limit(limit))

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
