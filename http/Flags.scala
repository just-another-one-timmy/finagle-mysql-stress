package com.timmy.mysql.http

import com.twitter.app.GlobalFlag

object User extends GlobalFlag[String]("", "mysql user name")
object Password extends GlobalFlag[String]("", "mysql user password")
object DbName extends GlobalFlag[String]("", "mysql database name")
object MysqlAddress extends GlobalFlag[String]("127.0.0.1:3306", "Mysql server address")
object Concurrency extends GlobalFlag[Int](1, "Concurrency level")
