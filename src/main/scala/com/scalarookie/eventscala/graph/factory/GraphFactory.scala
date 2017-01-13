package com.scalarookie.eventscala.graph.factory

import akka.actor.{ActorRef, ActorSystem, Props}
import com.scalarookie.eventscala.caseclasses._
import com.scalarookie.eventscala.graph.nodes._
import com.scalarookie.eventscala.qos._

object GraphFactory {

  def apply(
    query: Query,
    callback: Event => Any,
    frequencyStrategyFactory: StrategyFactory,
    latencyStrategyFactory: StrategyFactory,
    publishers: Map[String, ActorRef])(
      implicit system: ActorSystem):
  ActorRef = query match {
    case stream: Stream =>
      system.actorOf(Props(new StreamNode(stream, frequencyStrategyFactory, latencyStrategyFactory, publishers, Some(callback))), "stream")
    case select: Select =>
      system.actorOf(Props(new SelectNode(select, frequencyStrategyFactory, latencyStrategyFactory, publishers, Some(callback))), "select")
    case filter: Filter =>
      system.actorOf(Props(new FilterNode(filter, frequencyStrategyFactory, latencyStrategyFactory, publishers, Some(callback))), "filter")
    case selfJoin: SelfJoin =>
      system.actorOf(Props(new SelfJoinNode(selfJoin, frequencyStrategyFactory, latencyStrategyFactory, publishers, Some(callback))), "selfjoin")
    case join: Join =>
      system.actorOf(Props(new JoinNode(join, frequencyStrategyFactory, latencyStrategyFactory, publishers, Some(callback))), "join")
  }

}