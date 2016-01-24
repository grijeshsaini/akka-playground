package com.grijesh.learn.akka

import akka.actor.{Props, ActorSystem, Actor}

/**
  * Created by grijesh on 24/1/16.
  */
class HelloActor2(actorName:String) extends Actor {
  override def receive = {
    case "Hey man !!!!" => println("Hey !!!" + actorName)
    case _       => println("Have you said something to "+actorName +" ?")
  }
}

object HelloActor2 extends App{
  val system = ActorSystem("HelloSystem")
  // default Actor constructor
  val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
  helloActor ! "Hey man !!!!" //tell
  helloActor ! "hey..."
}
