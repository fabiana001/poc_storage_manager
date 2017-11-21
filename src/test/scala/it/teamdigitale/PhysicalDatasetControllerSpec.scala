package it.teamdigitale

import org.scalatest.{FlatSpec, Matchers}

class PhysicalDatasetControllerSpec extends FlatSpec with Matchers {

  "A phisical dataset controller" should "get a dataset from hdfs" in {
    pending
  }

  it should "get a dataset from kudu" in {
    pending
  }

  it should "get a dataset from opentsdb" in {
    pending
  }

  it should "select a column in a dataset" in {
    pending
  }

  it should "return an error if a non valid column is selected" in {
    pending
  }

  it should "return data for a valid where condition" in {
    pending
  }

  it should "return an error for a invalid where condition" in {
    pending
  }

  it should "aggregate correctly for column -> count" in {
    pending
  }

  it should "return an error for a invalid groupBy condition" in {
    pending
  }

}
