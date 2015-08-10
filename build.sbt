lazy val simple = (project in file("simple")).
  settings(
    name := "finagle-mysql-stress-simple",
    version := "1.0",
    scalaVersion := "2.11.7",
    libraryDependencies ++= Seq(
      "com.twitter" % "util-app_2.11" % "6.26.0",
      "com.twitter" % "finagle-core_2.11" % "6.26.0",
      "com.twitter" % "finagle-mysql_2.11" % "6.26.0"
    )
  )

lazy val http = (project in file("http")).
  settings(
    name := "finagle-mysql-stress-http",
    version := "1.0",
    scalaVersion := "2.11.7",
    libraryDependencies ++= Seq(
      "com.twitter" % "util-app_2.11" % "6.26.0",
      "com.twitter" % "finagle-core_2.11" % "6.26.0",
      "com.twitter" % "finagle-mysql_2.11" % "6.26.0",
      "com.twitter" % "finagle-httpx_2.11" % "6.26.0"
    )
  )
