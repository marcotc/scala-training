/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import com.github.nscala_time.time.Imports._

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

  val ice726CologneTime = Time(13, 2)

  val ice724 = Train(
    InterCityExpress(724),
    Vector(
      ice724MunichTime -> munich,
      ice724NurembergTime -> nuremberg,
      ice724FrankfurtTime -> frankfurt,
      ice724CologneTime -> cologne,
      ice724MunichTime.plusDays(1) -> munich,
      ice724NurembergTime.plusDays(1) -> nuremberg,
      ice724FrankfurtTime.plusDays(1) -> frankfurt,
      ice724CologneTime.plusDays(1) -> cologne,
      ice724MunichTime.plusDays(2) -> munich,
      ice724NurembergTime.plusDays(2) -> nuremberg,
      ice724FrankfurtTime.plusDays(2) -> frankfurt,
      ice724CologneTime.plusDays(2) -> cologne,
      ice724MunichTime.plusDays(3) -> munich,
      ice724NurembergTime.plusDays(3) -> nuremberg,
      ice724FrankfurtTime.plusDays(3) -> frankfurt,
      ice724CologneTime.plusDays(3) -> cologne,
      ice724MunichTime.plusDays(4) -> munich,
      ice724NurembergTime.plusDays(4) -> nuremberg,
      ice724FrankfurtTime.plusDays(4) -> frankfurt,
      ice724CologneTime.plusDays(4) -> cologne,
      ice724MunichTime.plusDays(5) -> munich,
      ice724NurembergTime.plusDays(5) -> nuremberg,
      ice724FrankfurtTime.plusDays(5) -> frankfurt,
      ice724CologneTime.plusDays(5) -> cologne,
      ice724MunichTime.plusDays(6) -> munich,
      ice724NurembergTime.plusDays(6) -> nuremberg,
      ice724FrankfurtTime.plusDays(6) -> frankfurt,
      ice724CologneTime.plusDays(6) -> cologne
    )
  )

  val ice726 = Train(
    InterCityExpress(726),
    Vector(
      ice726MunichTime -> munich,
      ice726NurembergTime -> nuremberg,
      ice726FrankfurtTime -> frankfurt,
      ice726CologneTime -> essen,
      ice726MunichTime.plusDays(1) -> munich,
      ice726NurembergTime.plusDays(1) -> nuremberg,
      ice726FrankfurtTime.plusDays(1) -> frankfurt,
      ice726CologneTime.plusDays(1) -> essen,
      ice726MunichTime.plusDays(2) -> munich,
      ice726NurembergTime.plusDays(2) -> nuremberg,
      ice726FrankfurtTime.plusDays(2) -> frankfurt,
      ice726CologneTime.plusDays(2) -> essen,
      ice726MunichTime.plusDays(3) -> munich,
      ice726NurembergTime.plusDays(3) -> nuremberg,
      ice726FrankfurtTime.plusDays(3) -> frankfurt,
      ice726CologneTime.plusDays(3) -> essen,
      ice726MunichTime.plusDays(4) -> munich,
      ice726NurembergTime.plusDays(4) -> nuremberg,
      ice726FrankfurtTime.plusDays(4) -> frankfurt,
      ice726CologneTime.plusDays(4) -> essen,
      ice726MunichTime.plusDays(5) -> munich,
      ice726NurembergTime.plusDays(5) -> nuremberg,
      ice726FrankfurtTime.plusDays(5) -> frankfurt,
      ice726CologneTime.plusDays(5) -> essen,
      ice726MunichTime.plusDays(6) -> munich,
      ice726NurembergTime.plusDays(6) -> nuremberg,
      ice726FrankfurtTime.plusDays(6) -> frankfurt,
      ice726CologneTime.plusDays(6) -> essen
    )
  )

  val planner = new JourneyPlanner(Set(ice724, ice726))
}
