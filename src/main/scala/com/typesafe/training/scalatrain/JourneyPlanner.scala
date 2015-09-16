/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

class JourneyPlanner(trains: Set[Train]) {

  val stations: Set[Station] =
    // Could also be expressed in short notation: trains flatMap (_.stations)
    trains.flatMap(train => train.stations)

  def trainsAt(station: Station): Set[Train] =
    // Could also be expressed in short notation: trains filter (_.stations contains station)
    trains.filter(train => train.stations.contains(station))

  def stopsAt(station: Station): Set[(Time, Train)] =
    for {
      train <- trains
      time <- train.timeAt(station)
    } yield (time, train)

  def isShortTrip(from: Station, to: Station): Boolean =
    trains.exists(train =>
      train.stations.dropWhile(station => station != from) match {
        case `from` +: `to` +: _      => true
        case `from` +: _ +: `to` +: _ => true
        case _                        => false
      }
    )

  def hopsFromStations: Map[Station, Set[Hop]] = {
    trains.flatMap(_.hops).groupBy(_.from)
  }

  def connection(station: Station, departureTime: Time): Set[Hop] = {
    hopsFromStations.getOrElse(station, Nil).filter(_.departureTime >= departureTime).toSet
  }

  def pathsBetween(from: Station, to: Station, depatureTime: Time):  = {
    connection(from, depatureTime)
  }

  def pathsBetweenHops(hop: Hop): Seq[Seq[Hop]] = {
    connection(hop.to, hop.arrivalTime).map { h => pathsBetweenHops(h).map{ _.+:(hop)} }
  }
}
