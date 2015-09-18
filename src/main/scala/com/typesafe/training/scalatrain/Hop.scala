package com.typesafe.training.scalatrain

import com.github.nscala_time.time.Imports._

case class Hop(from: Station, to: Station, train: Train, cost: Int = 0, length: Int = 0) {

  def departureTime: LocalDateTime = train.timeAt(from).get

  def arrivalTime: LocalDateTime = train.timeAt(to).get

  override def toString(): String = s"($from,$to,$cost,$length,${train.info.number})"
}
