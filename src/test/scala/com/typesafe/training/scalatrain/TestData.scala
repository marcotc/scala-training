/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import com.github.nscala_time.time.Imports._
import org.joda.time.LocalDateTime

import scala.collection.immutable.Seq

object TestData {
  def Time(hours: Int, minutes:Int): LocalDateTime = {
    new LocalDateTime(LocalDateTime.now().getYear, LocalDateTime.now().getMonthOfYear, LocalDateTime.now().getDayOfMonth, hours, minutes)
  }

  val munich = Station("Munich")

  val nuremberg = Station("Nuremberg")

  val frankfurt = Station("Frankfurt")

  val cologne = Station("Cologne")

  val essen = Station("Essen")

  val ice724MunichTime = Time(8, 50)

  val ice724NurembergTime = Time(10,0)

  val ice724FrankfurtTime = Time(12, 10)

  val ice724CologneTime = Time(13, 39)

  val ice726MunichTime = Time(7, 50)

  val ice726NurembergTime = Time(9,0)

  val ice726FrankfurtTime = Time(11, 10)

  val ice726EssenTime = Time(13, 2)

  val distances = Map(
    Set(munich, nuremberg) -> 100,
    Set(nuremberg, frankfurt) -> 200,
    Set(frankfurt, cologne) -> 400,
    Set(frankfurt, essen) -> 800
  )

  def sched(schedule: Seq[(LocalDateTime, Station, Int)], dayOfWeek: Seq[Int]): Seq[(LocalDateTime, Station, Int)] = {
    dayOfWeek.flatMap(d => schedule.map(t => t.copy(_1 = t._1.plusDays(d))))
  }

  val ice724MunichCost = 10
  val ice724NurembergCost = 20
  val ice724FrankfurtCost = 40

  val ice724 = Train(
    InterCityExpress(724),
    sched(
      Seq(
        (ice724MunichTime, munich, ice724MunichCost),
        (ice724NurembergTime, nuremberg, ice724NurembergCost),
        (ice724FrankfurtTime, frankfurt, ice724FrankfurtCost),
        (ice724CologneTime, cologne, 0)),
      Seq(0, 1, 2, 3, 4, 5, 6)
    )
  )

  val ice726MunichCost = 30
  val ice726NurembergCost = 10
  val ice726FrankfurtCost = 35

  val ice726 = Train(
    InterCityExpress(726),
    sched(
      Seq(
        (ice726MunichTime, munich, ice726MunichCost),
        (ice726NurembergTime, nuremberg, ice726NurembergCost),
        (ice726FrankfurtTime, frankfurt, ice726FrankfurtCost),
        (ice726EssenTime, essen, 0)),
      Seq(0, 1, 2, 3, 4, 5, 6)
    )
  )

  val planner = new JourneyPlanner(Set(ice724, ice726))
}
