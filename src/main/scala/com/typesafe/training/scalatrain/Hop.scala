package com.typesafe.training.scalatrain

case class Hop(from: Station, to: Station, train: Train) {

  def departureTime: Time = train.timeAt(from).get

  def arrivalTime: Time = train.timeAt(to).get

  override def toString(): String = s"($from,$to)"
}
