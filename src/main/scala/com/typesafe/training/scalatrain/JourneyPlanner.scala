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

  def connections(station: Station, departureTime: Time): Set[Hop] = {
    hopsFromStations.getOrElse(station, Set.empty).filter(_.departureTime >= departureTime)
  }

//  def pathsBetween(from: Station, to: Station, depatureTime: Time):  = {
//    connection(from, depatureTime)
//  }
//
//  def pathsBetweenHops(hop: Hop): Seq[Seq[Hop]] = {
//    connection(hop.to, hop.arrivalTime).map { h => pathsBetweenHops(h).map{ _.+:(hop)} }
//  }

  def paths(start: Station, endStation: Station, departureTime: Time): Set[Seq[Hop]] = {
    connections(start, departureTime).flatMap { hop: Hop =>
      hop match {
        case Hop(from, `endStation`, _) => Set(Seq(hop))
        case Hop(from, to, _) => paths(hop.to, endStation, hop.arrivalTime).map(Hop(hop.from,hop.to,hop.train) +: _)
      }
    }
  }
}

object JourneyPlanner {
  def sortPathsByTime(paths: Seq[Seq[Hop]]): Seq[Seq[Hop]] = {
    paths.sortBy(x => x.last.arrivalTime - x.head.departureTime)
  }
}