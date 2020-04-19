package me.annard.model

import java.util.Currency

case class SelectionLiability(name: String, currency: Currency, bets: Seq[Bet]) {
  def currencyCode: String = currency.getCurrencyCode
  def currencySymbol: String = currency.getSymbol
  def size: Long = bets.size
  def totalStakes: Double = bets.foldLeft[Double] (0.0) { (total, bet) => total + bet.stake }
  def totalLiability: Double = bets.foldLeft[Double] (0.0) { (total, bet) => total + bet.liability }
}
