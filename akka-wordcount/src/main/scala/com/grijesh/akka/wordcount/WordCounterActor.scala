package com.grijesh.akka.wordcount

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.concurrent.Await
import scala.io.Source._
import scala.language.postfixOps

/**
  * Created by grijesh on 17/3/16.
  */
class WordCounterActor(filename: String) extends Actor {

  private var running = false
  private var totalLines = 0
  private var linesProcessed = 0
  private var result = 0
  private var fileSender: Option[ActorRef] = None

  def receive = {
    case StartProcessFileMsg() =>
      if (running) {
        println("Warning: duplicate start message received")
      } else {
        running = true
        fileSender = Some(sender)
        fromFile(filename).getLines.foreach {
          line =>
            //println(line)
            context.actorOf(Props[StringCounterActor]) ! ProcessStringMsg(line)
            totalLines += 1
        }
      }
    case StringProcessedMsg(words) =>
      result += words
      linesProcessed += 1
      if (linesProcessed == totalLines) {
        fileSender.get ! result
      }
    case _ => println("message not recognized!")
  }
}

object RunExample extends App {

  import akka.pattern.ask
  import akka.util.Timeout

  import scala.concurrent.duration._

  val system = ActorSystem("System")
  val actor = system.actorOf(Props(new WordCounterActor(args(0))))
  implicit val timeout = Timeout(25 seconds)
  val future = actor ? StartProcessFileMsg()
  val result = Await.result(future, timeout.duration).asInstanceOf[Int]
  println(result)
  system terminate

}