/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import TestData._
import java.lang.{ IllegalArgumentException => IAE }
import org.scalatest.{ Matchers, WordSpec }

class TrainSpec extends WordSpec with Matchers {

  "Train ice724" should {
    "stop in Nurember" in {
      ice724.timeAt(nuremberg) shouldEqual Some(ice724NurembergTime)
    }
    "not stop in Essen" in {
      ice724.timeAt(essen) shouldEqual None
    }
  }

  "Train ice726" should {
    "stop in Munich" in {
      ice726.timeAt(munich) shouldEqual Some(ice726MunichTime)
    }
    "not stop in Cologne" in {
      ice726.timeAt(cologne) shouldEqual None
    }
  }

  "Creating a Train" should {
    "throw an IllegalArgumentException for a schedule with 0 or 1 elements" in {
      an[IAE] should be thrownBy Train(InterCityExpress(724), Vector())
      an[IAE] should be thrownBy Train(InterCityExpress(724), Vector((ice724MunichTime, munich, 10)))
    }
  }

  "stations" should {
    "be initialized correctly" in {
      ice724.stations.toSet shouldEqual Set(munich, nuremberg, frankfurt, cologne)
    }
  }

  "hops" should {
    "return" in {
      ice726.hops(ice724MunichTime.toLocalDate) shouldEqual Vector(Hop(munich, nuremberg, ice726, ice726MunichCost), Hop(nuremberg, frankfurt,ice726, ice726NurembergCost), Hop(frankfurt, essen, ice726, ice726FrankfurtCost))
    }
  }

  "getSchedule" should {
    "return schedule" in {
      ice726.getSchedule(ice726EssenTime) should not equal None
    }
  }
}
