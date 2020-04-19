package me.annard.model

import java.util.Currency

case class SelectionLiability(selection: Selection, currency: Currency, bets: Seq[Bet], totalStakes: Double, totalLiability: Double)
