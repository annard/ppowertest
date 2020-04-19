package me.annard.model

import java.util.Currency

import org.scalatest.flatspec.AnyFlatSpec

class SelectionLiabilityTest extends AnyFlatSpec {

  val selection = Selection(42, "Beeblebrox")
  val euro = Currency.getInstance("EUR")
  val bet1 = Bet("BetMe-1", 4242424242L, selection, 2.0, 10.0, euro)
  val bet2 = Bet("BetMe-2", 4242424668L, selection, 1.2, 2.0, euro)

  "Selection" should "have a correct liability with 0 bets" in {
    val selLiability = SelectionLiability(selection.name, euro, Seq.empty[Bet])
    assert(selLiability.betCount == 0)
    assert(selLiability.totalLiability == 0.0)
    assert(selLiability.totalStakes == 0.0)
  }

  "Selection" should "have a correct liability with 1 bet" in {
    val selLiability = SelectionLiability(selection.name, euro, Seq(bet1))
    assert(selLiability.betCount == 1)
    assert(selLiability.totalLiability == bet1.liability())
    assert(selLiability.totalStakes == bet1.stake)
  }

  "Selection" should "have a correct liability with multiple bets" in {
    val selLiability = SelectionLiability(selection.name, euro, Seq(bet1, bet2))
    assert(selLiability.betCount == 2)
    assert(selLiability.totalLiability == bet1.liability() + bet2.liability())
    assert(selLiability.totalStakes == bet1.stake + bet2.stake)
  }
}
