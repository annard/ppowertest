package me.annard

import org.scalatest.FlatSpec
import java.util.Currency

class BetTest extends FlatSpec {

  "Liability" should "be correctly calculated" in {
      val selection = Selection(42, "Beeblebrox")
      val myBet = Bet("BetMe-1", 4242424242L, selection, 2.0, 10.0, Currency.getInstance("EUR"))

      assert(myBet.liability() == 20.0)
  }
}
