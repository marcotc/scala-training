package com.typesafe.training.scalatrain

case class Hop(from: Station, to: Station, train: Train, cost: Int = 0) {

  def departureTime: Time = train.timeAt(from).get

  def arrivalTime: Time = train.timeAt(to).get

  override def toString(): String = s"($from,$to,${train.info.number})"
}
