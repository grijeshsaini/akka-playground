package com.grijesh.learn.akka

import akka.actor.{Props, ActorSystem, Actor}

/**
  * Created by grijesh on 24/1/16.
  */
class HelloActor extends Actor{
  override def receive = {
    case "Hey man !!!!" => println("Hey !!!")
    case "Bye" => context stop self
    case _       => println("Have you said something ?")
  }
}

object HelloActor extends App{
  val system = ActorSystem("HelloSystem")
  // default Actor constructor
  val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
  helloActor ! "Hey man !!!!" //tell
  helloActor ! "hey..."
  helloActor ! "Bye"
  system.terminate()
}
