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

  private def validateColumns(df: Try[DataFrame], columns: Set[String]): Try[DataFrame] = {
    df.flatMap { d =>
      if (columns.diff(d.columns.toSet).isEmpty) Success(d)
      else Failure(new IllegalArgumentException(s"Columns $columns not found in ${d.columns}"))
    }
  }

  /**
   *
   * @param df
   * @param column
   * @return a dataset with only the selected column
   */
  def select(df: Try[DataFrame], column: String): Try[DataFrame] = {
    validateColumns(df, Set(column))
      .map(_.select(column))
  }

  def select(df: Try[DataFrame], col: String, cols: String*): Try[DataFrame] = {
    val columns: Seq[String] = cols :+ col
    validateColumns(df, columns.toSet)
      .map(_.select(col, cols:_*))
  }

  type Column = String
  type Func = String
  type GroupExpr = (Column, Func)

  /**
    *
    * @param df a dataframe
    * @param column the column used for the aggregation
    * @param groupByOps a list of valid aggregations expression in the form (column,func) where func is in in Set("count", "max", "mean", "min", "sum")
    * @return
    */
  def groupBy(df: Try[DataFrame], column: String, groupByOps: GroupExpr*): Try[DataFrame] = {

    val validatedAggrs = groupByOps.map(kv => AggregationsValidator.validate(kv._2))

    val failureMsg = validatedAggrs.filter(_.isFailure)
      .map {
        case Failure(ex) => ex.getMessage
        case Success(_) => ""
      }.mkString("[", ",", "]")

    //if there are not validations errors
    if (validatedAggrs.forall(_.isSuccess)) {

      val columns: Seq[String] = groupByOps.map(_._1) :+ column
      validateColumns(df, columns.toSet)
        .map(_.groupBy(column).agg(groupByOps.toMap))

    } else Failure(new IllegalArgumentException(failureMsg))
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

