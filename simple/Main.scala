package com.timmy.mysql.simple

import com.twitter.app.App
import com.twitter.finagle.exp.Mysql
import com.twitter.finagle.exp.mysql.{Client => MysqlClient}
import com.twitter.finagle.{Service, ServiceFactory}
import com.twitter.util.{Future, Stopwatch}
import java.util.concurrent.atomic.AtomicLong
import scala.util.Random

object MysqlStress extends App {
  val count = new AtomicLong
  val sql = "SELECT * FROM test where id = "

  def proc(client: MysqlClient) {
    client.query(sql + Random.nextInt(1000)) onSuccess { _ =>
      count.incrementAndGet()
      proc(client)
    }
  }

  def main() {
    println("Flags values:")
    println(s"User: ${User()}")
    println(s"Password: ${Password()}")
    println(s"DbName: ${DbName()}")
    println(s"MysqlAddress: ${MysqlAddress()}")

    val mysqlClient = Mysql.client
      .withCredentials(User(), Password())
      .withDatabase(DbName())
      .newRichClient(MysqlAddress())

    proc(mysqlClient)

    val elapsed = Stopwatch.start()

    while (true) {
      Thread.sleep(5000)

      val howlong = elapsed()
      val howmuch = count.get()
      assert(howmuch > 0)
      printf("%d QPS\n", howmuch / howlong.inSeconds)
    }
  }
}
