package me.annard.reporting

import java.util.Currency

import me.annard.model.{Bet, Selection, TotalLiability}
import org.scalatest.flatspec.AnyFlatSpec

class TotalLiabilityReportTest extends AnyFlatSpec {

  "Report" should "display correct table" in {
    val selection = Selection(42, "Beeblebrox")
    val bet1 = Bet("BetMe-1", 4242424242L, selection, 2.0, 10.0, Currency.getInstance("EUR"))
    val bet2 = Bet("BetMe-2", 4242424668L, selection, 1.2, 2.0, Currency.getInstance("GBP"))
    val totalLiabilities = Seq(new TotalLiability(bet1.currency, 1, bet1.stake, bet1.liability()),
      new TotalLiability(bet2.currency, 1, bet2.stake, bet2.liability()))
    val reporter = new TotalLiabilityReport
    val builder: StringBuilder = reporter.writeHeaders(totalLiabilities, new StringBuilder())
    reporter.writeValues(totalLiabilities, builder)
    val reportStr = builder.mkString

    assert(reportStr.startsWith("Currency            Num Bets            Total Stakes        Total Liability"))
    assert(reportStr.trim().endsWith("£2.40"))
  }

}
