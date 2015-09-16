/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import scala.collection.immutable.Seq

case class Train(info: TrainInfo, schedule: Seq[(Time, Station)]) {
  require(schedule.size >= 2, "schedule must contain at least two elements")
  // TODO Verify that `schedule` is strictly increasing in time

  val stations: Seq[Station] =
    // Could also be expressed in short notation: schedule map (_._2)
    schedule.map(stop => stop._2)

  def timeAt(station: Station): Option[Time] =
    // Could also be expressed in notation: schedule find (_._2 == station) map (_._1)
    schedule.find(stop => stop._2 == station).map(found => found._1)

  def hops: Seq[Hop] = {
    val sorted = schedule.sortBy(_._1)
    sorted.zip(sorted.drop(1)).map{
      case ((_, station1), (_, station2)) => Hop(station1, station2, this)
    }
  }
}

case class Station(name: String) {
  override def toString(): String = name
}

sealed abstract class TrainInfo {
  def number: Int
}
case class InterCityExpress(number: Int, hasWifi: Boolean = false) extends TrainInfo
case class RegionalExpress(number: Int) extends TrainInfo
case class BavarianRegional(number: Int) extends TrainInfo
