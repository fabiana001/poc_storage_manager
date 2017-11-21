package it.teamdigitale

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import org.slf4j.{Logger, LoggerFactory}
import services.DockerContainer

/**
  * Since MiniKuduCluster requires a compiled 'kudu' binary. For this reason we use to test Kudu docker-java library.
  */
class KuduControllerSpec2  extends FlatSpec with Matchers with BeforeAndAfterAll {

  //var  miniCluster: MiniKuduCluster = _

  //var client: AsyncKuduClient = _

  //var syncClient: KuduClient = _

  val tableName = "test_table"

  var df: DataFrame = _

  val sparkSession = SparkSession.builder().master("local").getOrCreate()

  var kuduController: KuduController = _

  val alogger: Logger = LoggerFactory.getLogger(this.getClass)

  var containerId: String = _



  override def beforeAll(): Unit = {

    alogger.info("In Before All")

    containerId = DockerContainer.builder()
      //.withImage("docker.elastic.co/elasticsearch/elasticsearch:5.5.2")
      .withImage("appsvc/kudu")
      //for test use unconvetional ports
      .withPort(s"8181/tcp", "8181/tcp")
      //.withEnv("http.host", "0.0.0.0")
      .withEnv("network.host", "0.0.0.0")
      .withEnv("transport.host", "127.0.0.1")
      //.withEnv("index.mapper.dynamic", "true")
      .withName("TESTKUDU-PIPPOOOOO")
      .run()

//    miniCluster = Try (new MiniKuduCluster.MiniKuduClusterBuilder().numMasters(1).numTservers(1).build()) match {
//      case Success(c) => c
//      case Failure(ex) =>
//        alogger.error("NUOOOOOOOOOOOO")
//        new MiniKuduCluster.MiniKuduClusterBuilder().numMasters(1).numTservers(1).build()
//    }
//    alogger.info("Kudu MiniCluster created")
//
//    val masterAddresses: String = miniCluster.getMasterAddresses
//    val masterHostPort= miniCluster.getMasterHostPorts.asScala.head
//    alogger.info(s"Kudu MiniCluster created at $masterAddresses $masterHostPort")
//
//    //client = new AsyncKuduClient.AsyncKuduClientBuilder(masterAddresses).defaultAdminOperationTimeoutMs(50000).build
//    //syncClient = new KuduClient(client)



    Thread.sleep(40000L)

//    val kuduContext = new KuduContext(s"${masterHostPort.getHostText}:${masterHostPort.getPort}", sparkSession.sqlContext.sparkContext)
//    initKuduTable(kuduContext)
//
//    kuduController = new KuduController(sparkSession, masterAddresses)

    alogger.info("End Before All")
  }

  override def afterAll(): Unit = {
    //DockerContainer.builder().withId(containerId).clean()
    //miniCluster.shutdown()
  }

//  private def initKuduTable(kuduContext: KuduContext): Unit = {
//    import sparkSession.sqlContext.implicits._
//
//    df = Seq(
//      (8, "bat"),
//      (64, "mouse"),
//      (-27, "horse")
//    ).toDF("number", "word")
//
//    val build = new CreateTableOptions().setNumReplicas(1).addHashPartitions(List("word").asJava, 3)
//    //client.syncClient.createTable(tableName, df.schema, build)
//    //create table
//    kuduContext.createTable(tableName, df.schema, Seq("word"), build)
//    // Insert data
//    kuduContext.insertRows(df, tableName)
//  }



  "KuduController" should "Correctly read data from kudu" in {

      alogger.info("In Body")
// see here https://github.com/cloudera/kudu/blob/master/java/kudu-client/src/test/java/org/apache/kudu/client/BaseKuduTest.java
//      val newDf = kuduController.readData(tableName)
//      newDf.isSuccess must_== true
//      newDf.get.count must_== 4
//      newDf.get.columns.toSet must_== df.columns.toSet
//      newDf.get.collect().toSet must_== df.collect().toSet

      alogger.info(s"container $containerId")
      //ok
    }


}
