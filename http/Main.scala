package com.timmy.mysql.http

import com.twitter.app.App
import com.twitter.finagle.exp.Mysql
import com.twitter.finagle.exp.mysql.{Client => MysqlClient}
import com.twitter.finagle.httpx
import com.twitter.finagle.{Httpx, Service}
import com.twitter.util.{Await, Future, Promise}
import scala.util.Random

object MysqlStress extends App {
  def main() {
    println("Flags values:")
    println(s"User: ${User()}")
    println(s"Password: ${Password()}")

    println(s"DbName: ${DbName()}")
    println(s"MysqlAddress: ${MysqlAddress()}")
    println(s"Concurrency: ${Concurrency()}")

    val mysqlClient = Mysql.client
      .withCredentials(User(), Password())
      .withDatabase(DbName())
      .newRichClient(MysqlAddress())

    val service = new Service[httpx.Request, httpx.Response] {
      def apply(req: httpx.Request): Future[httpx.Response] = {
        val returnResponse = new Promise[httpx.Response]
        val sql = "SELECT * FROM test where id = ";
        mysqlClient.query(sql + Random.nextInt(1000)).map { _ =>
          returnResponse.setValue(httpx.Response(req.version, httpx.Status(200)))
        }
        returnResponse
      }
    }
    val server = Httpx.serve(":8200", service)
    Await.ready(server)
  }
}
