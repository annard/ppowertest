package me.annard.model

import java.util.Currency

trait Liability {
  def name: String
  def betCount: Long
  def currency: Currency
  def totalStakes: Double
  def totalLiability: Double

  def currencyCode: String = currency.getCurrencyCode
  def currencySymbol: String = currency.getSymbol
}
