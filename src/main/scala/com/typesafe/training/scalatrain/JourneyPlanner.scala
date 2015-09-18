/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import com.github.nscala_time.time.Imports._

class JourneyPlanner(trains: Set[Train]) {

  val stations: Set[Station] =
    // Could also be expressed in short notation: trains flatMap (_.stations)
    trains.flatMap(train => train.stations)

  def trainsAt(station: Station): Set[Train] =
    // Could also be expressed in short notation: trains filter (_.stations contains station)
    trains.filter(train => train.stations.contains(station))

  def stopsAt(station: Station): Set[(LocalDateTime, Train)] =
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

  def nextMaintenance(train: Train): LocalDate = {
    val maxLength = 100000
    val maxTime = 1 year

    var date = train.lastMaintenance
    var distance = 0
    while (distance < maxLength && date.isBefore(train.lastMaintenance.plus(maxTime))) {
      distance += train.hops(date).foldLeft(0){_ + _.length}
      date = date.plusDays(1)
    }

    date
  }

  def hopsFromStations(date: LocalDate): Map[Station, Set[Hop]] = {
    trains.flatMap(_.hops(date)).groupBy(_.from)
  }

  def connections(station: Station, departureTime: LocalDateTime): Set[Hop] = {
    hopsFromStations(departureTime.toLocalDate()).getOrElse(station, Set.empty).filter(_.departureTime >= departureTime)
  }

  def paths(start: Station, endStation: Station, departureTime: LocalDateTime = LocalDateTime.now()): Set[Seq[Hop]] = {
    connections(start, departureTime).flatMap { hop: Hop =>
      hop match {
        case Hop(from, `endStation`, _, _, _) => Set(Seq(hop))
        case Hop(from, to, _, _, _) => paths(hop.to, endStation, hop.arrivalTime).map(Hop(hop.from,hop.to,hop.train, hop.cost, hop.length) +: _)
      }
    }
  }
}

object JourneyPlanner {
  def sortPathsByTime(paths: Seq[Seq[Hop]]): Seq[Seq[Hop]] =
    paths.sortBy(x => Period.fieldDifference(x.head.departureTime, x.last.arrivalTime).getMillis)


  def sortPathsByCost(paths: Seq[Seq[Hop]]): Seq[Seq[Hop]] =
    paths.sortBy(x => x.foldLeft(0){_ + _.cost})

}