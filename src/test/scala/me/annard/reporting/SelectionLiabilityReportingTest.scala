package me.annard.reporting

import java.util.Currency

import me.annard.model.{Bet, Selection, SelectionLiability}
import org.scalatest.flatspec.AnyFlatSpec

class SelectionLiabilityReportingTest extends AnyFlatSpec {

  "Report" should "display correct table" in {
    val selection = Selection(42, "Beeblebrox")
    val bet1 = Bet("BetMe-1", 4242424242L, selection, 2.0, 10.0, Currency.getInstance("EUR"))
    val bet2 = Bet("BetMe-2", 4242424668L, selection, 1.2, 2.0, Currency.getInstance("GBP"))
    val selLiabilities = Seq(new SelectionLiability(selection.name, bet1.currency, Seq(bet1)),
      new SelectionLiability(selection.name, bet2.currency, Seq(bet2)))
    val reporter = new SelectionLiabilityReport
    val builder: StringBuilder = reporter.writeHeaders(selLiabilities, new StringBuilder())
    reporter.writeValues(selLiabilities, builder)
    val reportStr = builder.mkString

    assert(reportStr.startsWith("Selection Name      Currency            Num Bets            Total Stakes        Total Liability"))
    assert(reportStr.trim().endsWith("£2.40"))
  }
}
