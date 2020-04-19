package me.annard.model

import java.util.Currency

case class TotalLiability(currency: Currency, betCount: Long, totalStakes: Double, totalLiability: Double) extends Liability {
  def name: String = this.currencyCode
}
