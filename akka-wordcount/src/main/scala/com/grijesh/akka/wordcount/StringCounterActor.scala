package com.grijesh.akka.wordcount

import akka.actor.Actor

/**
  * Created by grijesh on 17/3/16.
  */
class StringCounterActor extends Actor {
  def receive = {
    case ProcessStringMsg(string) => {
      val wordsInLine = string.split(" ").length
      sender ! StringProcessedMsg(wordsInLine)
    }
    case _ => println("Error: message not recognized")
  }
}
