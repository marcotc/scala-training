/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import TestData._
import java.lang.{ IllegalArgumentException => IAE }
import org.scalatest.{ Matchers, WordSpec }

class JourneyPlannerSpec extends WordSpec with Matchers {

  "stations" should {
    "be initialized correctly" in {
      planner.stations shouldEqual Set(munich, nuremberg, frankfurt, cologne, essen)
    }
  }

  "Calling trainsAt" should {
    "return the correct trains" in {
      planner.trainsAt(munich) shouldEqual Set(ice724, ice726)
      planner.trainsAt(cologne) shouldEqual Set(ice724)
    }
  }

  "Calling stopsAt" should {
    "return the correct stops" in {
      planner.stopsAt(munich) shouldEqual Set(ice724MunichTime -> ice724, ice726MunichTime -> ice726)
    }
  }

  "Calling isShortTrip" should {
    "return false for more than one station in between" in {
      planner.isShortTrip(munich, cologne) shouldBe false
      planner.isShortTrip(munich, essen) shouldBe false
    }
    "return true for zero or one stations in between" in {
      planner.isShortTrip(munich, nuremberg) shouldBe true
      planner.isShortTrip(munich, frankfurt) shouldBe true
      planner.isShortTrip(nuremberg, frankfurt) shouldBe true
      planner.isShortTrip(nuremberg, essen) shouldBe true
    }
  }

  "Calling hopsFromStations" should {
    "return" in {
      planner.hopsFromStations(ice724MunichTime.toLocalDate) shouldBe
        Map(munich -> Set(Hop(munich,nuremberg,ice726, ice726MunichCost), Hop(munich,nuremberg,ice724, ice724MunichCost)),
          frankfurt -> Set(Hop(frankfurt,cologne,ice724, ice724FrankfurtCost), Hop(frankfurt,essen,ice726, ice726FrankfurtCost)),
          nuremberg -> Set(Hop(nuremberg,frankfurt,ice726, ice726NurembergCost), Hop(nuremberg,frankfurt,ice724, ice724NurembergCost)))
    }
  }

  "Calling paths" should {
    "return paths to Cologne" in {
      planner.paths(munich, cologne, ice726MunichTime) shouldBe
        Set(List(Hop(munich, nuremberg, ice726, ice726MunichCost), Hop(nuremberg, frankfurt, ice726, ice726NurembergCost), Hop(frankfurt, cologne, ice724, ice724FrankfurtCost)),
          List(Hop(munich, nuremberg, ice726, ice726MunichCost), Hop(nuremberg, frankfurt, ice724, ice724NurembergCost), Hop(frankfurt, cologne, ice724, ice724FrankfurtCost)),
          List(Hop(munich, nuremberg, ice724, ice724MunichCost), Hop(nuremberg, frankfurt, ice724, ice724NurembergCost), Hop(frankfurt, cologne, ice724, ice724FrankfurtCost)))
    }
  }

  "Calling paths" should {
    "return paths to essen" in {
      planner.paths(munich, essen, ice726MunichTime) shouldBe
        Set(List(Hop(munich, nuremberg, ice726, ice726MunichCost), Hop(nuremberg, frankfurt, ice726, ice726NurembergCost), Hop(frankfurt, essen, ice726, ice726FrankfurtCost)))
    }
  }

  "Calling sortPathsByTime" should {
    "return sorted paths" in {
      JourneyPlanner.sortPathsByTime(Seq(
        Seq(Hop(munich, nuremberg, ice726), Hop(nuremberg, frankfurt, ice726), Hop(frankfurt, cologne, ice724)),
        Seq(Hop(munich, nuremberg, ice726), Hop(nuremberg, frankfurt, ice724), Hop(frankfurt, cologne, ice724)),
        Seq(Hop(munich, nuremberg, ice724), Hop(nuremberg, frankfurt, ice724), Hop(frankfurt, cologne, ice724)))
      ) shouldBe Seq(
        Seq(Hop(munich, nuremberg, ice724), Hop(nuremberg, frankfurt, ice724), Hop(frankfurt, cologne, ice724)),
        Seq(Hop(munich, nuremberg, ice726), Hop(nuremberg, frankfurt, ice726), Hop(frankfurt, cologne, ice724)),
        Seq(Hop(munich, nuremberg, ice726), Hop(nuremberg, frankfurt, ice724), Hop(frankfurt, cologne, ice724))
      )
    }
  }

  "Calling sortPathsByCost" should {
    "return sorted by cost" in {
      JourneyPlanner.sortPathsByCost(Seq(
        Seq(Hop(munich, nuremberg, ice726, 1), Hop(nuremberg, frankfurt, ice726, 2), Hop(frankfurt, cologne, ice724, 3)),
        Seq(Hop(munich, nuremberg, ice726, 0), Hop(nuremberg, frankfurt, ice724, 0), Hop(frankfurt, cologne, ice724, 1)),
        Seq(Hop(munich, nuremberg, ice724, 9), Hop(nuremberg, frankfurt, ice724, 9), Hop(frankfurt, cologne, ice724, 9)))
      ) shouldBe Seq(
        Seq(Hop(munich, nuremberg, ice726, 0), Hop(nuremberg, frankfurt, ice724, 0), Hop(frankfurt, cologne, ice724, 1)),
        Seq(Hop(munich, nuremberg, ice726, 1), Hop(nuremberg, frankfurt, ice726, 2), Hop(frankfurt, cologne, ice724, 3)),
        Seq(Hop(munich, nuremberg, ice724, 9), Hop(nuremberg, frankfurt, ice724, 9), Hop(frankfurt, cologne, ice724, 9))
      )
    }
  }

  "Calling nextMaintenance" should {
    "return the correct nextMaintenance" in {
      //planner.nextMaintenance(ice724) shouldEqual Set(ice724, ice726)
    }
  }
}
