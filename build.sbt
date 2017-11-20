import Versions._
import sbt.{ExclusionRule, ModuleID}

name := "poc_storage_manager"

version := "0.1"

scalaVersion := "2.11.12"

val sparkExcludes =
  (moduleId: ModuleID) => moduleId.
    exclude("org.apache.hadoop", "hadoop-client").
    exclude("org.apache.hadoop", "hadoop-yarn-client").
    exclude("org.apache.hadoop", "hadoop-yarn-api").
    exclude("org.apache.hadoop", "hadoop-yarn-common").
    exclude("org.apache.hadoop", "hadoop-yarn-server-common").
    exclude("org.apache.hadoop", "hadoop-yarn-server-web-proxy").
    exclude("org.apache.zookeeper", "zookeeper").
    exclude("commons-collections", "commons-collections").
    exclude("commons-beanutils", "commons-beanutils").
    exclude("org.slf4j", "slf4j-log4j12")

val sparkLibraries = Seq(
  sparkExcludes("org.apache.spark" %% "spark-core" % sparkVersion % "compile"),
  sparkExcludes("org.apache.spark" %% "spark-sql" % sparkVersion % "compile"),
  sparkExcludes("org.apache.spark" %% "spark-yarn" % sparkVersion % "compile"),
  sparkExcludes("org.apache.spark" %% "spark-mllib" % sparkVersion % "compile"),
  sparkExcludes("org.apache.spark" %% "spark-streaming" % sparkVersion % "compile"),
  sparkExcludes("org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion % "compile"),
)

val hadoopExcludes =
  (moduleId: ModuleID) => moduleId.
    exclude("org.slf4j", "slf4j-api").
    exclude("org.slf4j", "slf4j-log4j12").
    exclude("org.mortbay.jetty", "jetty").
    exclude("org.mortbay.jetty", "jetty-util").
    exclude("org.mortbay.jetty", "jetty-sslengine").
    exclude("javax.servlet", "servlet-api").
    exclude("org.apache.logging.log4j", "log4j-slf4j-impl").
    exclude("org.slf4j", "slf4j-log4j12")

val hadoopHBaseExcludes =
  (moduleId: ModuleID) => moduleId.
    exclude("org.slf4j", "slf4j-log4j12").
    exclude("javax.servlet", "servlet-api").
    exclude("org.apache.logging.log4j", "log4j-slf4j-impl").
    exclude("org.slf4j", "slf4j-log4j12").
    excludeAll(ExclusionRule(organization = "javax.servlet"))

val hbaseExcludes =
  (moduleID: ModuleID) => moduleID.
    exclude("org.apache.thrift", "thrift").
    exclude("org.jruby", "jruby-complete").
    exclude("org.slf4j", "slf4j-log4j12").
    exclude("org.mortbay.jetty", "jsp-2.1").
    exclude("org.mortbay.jetty", "jsp-api-2.1").
    exclude("org.mortbay.jetty", "servlet-api-2.5").
    exclude("com.sun.jersey", "jersey-core").
    exclude("com.sun.jersey", "jersey-json").
    exclude("com.sun.jersey", "jersey-server").
    exclude("org.mortbay.jetty", "jetty").
    exclude("org.mortbay.jetty", "jetty-util").
    exclude("tomcat", "jasper-runtime").
    exclude("tomcat", "jasper-compiler").
    exclude("org.jboss.netty", "netty").
    exclude("io.netty", "netty").
    exclude("commons-logging", "commons-logging").
    exclude("org.apache.xmlgraphics", "batik-ext").
    exclude("commons-collections", "commons-collections").
    exclude("xom", "xom").
    exclude("commons-beanutils", "commons-beanutils").
    exclude("org.apache.logging.log4j", "log4j-slf4j-impl").
    exclude("org.slf4j", "slf4j-log4j12")

val logLibraries = Seq (
  "org.apache.logging.log4j" % "log4j-core" % log4j,
  "org.apache.logging.log4j" % "log4j-api" % log4j,
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4j
)

val hbaseLibraries = Seq (
  "org.apache.spark.opentsdb" %% "spark-opentsdb" % sparkOpenTSDBVersion % "compile" exclude("org.slf4j", "slf4j-log4j12"),
  hbaseExcludes("org.apache.hbase" % "hbase-client" % hbaseVersion % "compile"),
  hbaseExcludes("org.apache.hbase" % "hbase-protocol" % hbaseVersion % "compile"),
  hbaseExcludes("org.apache.hbase" % "hbase-hadoop-compat" % hbaseVersion % "compile"),
  hbaseExcludes("org.apache.hbase" % "hbase-server" % hbaseVersion % "compile"),
  hbaseExcludes("org.apache.hbase" % "hbase-common" % hbaseVersion % "compile"),
  hadoopExcludes("org.apache.hadoop" % "hadoop-yarn-server-web-proxy" % hadoopVersion % "compile"),
  hadoopExcludes("org.apache.hadoop" % "hadoop-client" % hadoopVersion % "compile"),
  hadoopHBaseExcludes("org.apache.hbase" % "hbase-server" % hbaseVersion % "test" classifier "tests"),
  hadoopHBaseExcludes("org.apache.hbase" % "hbase-common" % hbaseVersion % "test" classifier "tests"),
  hadoopHBaseExcludes("org.apache.hbase" % "hbase-testing-util" % hbaseVersion % "test" classifier "tests"
    exclude("org.apache.hadoop<", "hadoop-hdfs")
    exclude("org.apache.hadoop", "hadoop-minicluster")
    exclude("org.apache.hadoo", "hadoop-client")),
  hadoopHBaseExcludes("org.apache.hbase" % "hbase-hadoop-compat" % hbaseVersion % "test" classifier "tests"),
  hadoopHBaseExcludes("org.apache.hbase" % "hbase-hadoop-compat" % hbaseVersion % "test" classifier "tests" extra "type" -> "test-jar"),
  hadoopHBaseExcludes("org.apache.hbase" % "hbase-hadoop2-compat" % hbaseVersion % "test" classifier "tests" extra "type" -> "test-jar"),
  hadoopHBaseExcludes("org.apache.hadoop" % "hadoop-client" % hadoopVersion % "test" classifier "tests"),
  hadoopHBaseExcludes("org.apache.hadoop" % "hadoop-hdfs" % hadoopVersion % "test" classifier "tests"),
  hadoopHBaseExcludes("org.apache.hadoop" % "hadoop-hdfs" % hadoopVersion % "test" classifier "tests" extra "type" -> "test-jar"),
  hadoopHBaseExcludes("org.apache.hadoop" % "hadoop-hdfs" % hadoopVersion % "test" extra "type" -> "test-jar"),
  hadoopHBaseExcludes("org.apache.hadoop" % "hadoop-client" % hadoopVersion % "test" classifier "tests"),
  hadoopHBaseExcludes("org.apache.hadoop" % "hadoop-minicluster" % hadoopVersion % "test"),
  hadoopHBaseExcludes("org.apache.hadoop" % "hadoop-common" % hadoopVersion % "test" classifier "tests" extra "type" -> "test-jar"),
  hadoopHBaseExcludes("org.apache.hadoop" % "hadoop-mapreduce-client-jobclient" % hadoopVersion % "test" classifier "tests")
)

dependencyOverrides ++= Seq(
  "com.google.guava" % "guava" % "12.0.1" % "test",
  "com.google.guava" % "guava" % "12.0.1" % "compile"
)


libraryDependencies ++= Seq(
  //Test Dependencies
  "org.specs2" %% "specs2-core" % spec2Version % "test",
  "com.github.pathikrit" %% "better-files" % betterFilesVersion % Test)
  .map(x => x.exclude("org.scalactic", "scalactic")) ++ sparkLibraries ++ hbaseLibraries ++ logLibraries