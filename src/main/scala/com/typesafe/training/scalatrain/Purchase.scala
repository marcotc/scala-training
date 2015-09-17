package com.typesafe.training.scalatrain

import org.joda.time.LocalDateTime

class Purchase {
  def buy(order: Order, trains: Seq[Train]) = {
    trains.filter{_.getSchedule(order.date).isDefined}
  }
}

case class Order(name: String, email: String, from: Station, to: Station, date: LocalDateTime, purchaseType: PurchaseType)

sealed abstract class PurchaseType

case class Cheapest() extends PurchaseType
case class Fastest() extends PurchaseType