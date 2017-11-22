package it.teamdigitale

import org.apache.spark.sql.DataFrame

import scala.util.{Failure, Success, Try}

trait DatasetOperations {

  def defaultLimit: Int

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
}

object DatasetOperations extends DatasetOperations {
  override val defaultLimit: Int = 100
}

