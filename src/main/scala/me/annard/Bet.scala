package me.annard

import java.util.Currency

case class Bet(id: String, timestamp: Long, selection: Selection, stake: Double, price: Double, currency: Currency) {
  def liability(): Double = this.stake * this.price
}
