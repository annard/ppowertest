package me.annard.model

import java.util.Currency

case class SelectionLiability(name: String, currency: Currency, bets: Seq[Bet]) extends Liability{
  def betCount: Long = bets.size
  def totalStakes: Double = bets.foldLeft[Double] (0.0) { (total, bet) => total + bet.stake }
  def totalLiability: Double = bets.foldLeft[Double] (0.0) { (total, bet) => total + bet.liability }
}
