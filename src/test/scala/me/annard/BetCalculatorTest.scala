package me.annard

import java.io.File
import java.util.Currency

import me.annard.model.{Bet, Selection}
import org.scalatest.flatspec.AnyFlatSpec

class BetCalculatorTest extends AnyFlatSpec {

  "BetCalculator" should "handle no betting data" in {
    val emptySelectionMap = Map.empty[Selection, Seq[Bet]]
    assert(BetCalculator.updateSelections(NoneProcessor) == emptySelectionMap)
  }

  val csvPath = getClass().getResource("/test_data.csv").getPath()
  val csvUrl = new File(csvPath).toURI().toURL()

  "Calculator" should "handle betting data" in {
    val csvProc = CSVProcessor(csvUrl)
    val selectionMap = BetCalculator.updateSelections(csvProc)
    assert(selectionMap.count((_) => true) == 4)
    val betList = selectionMap.find( { case(sel, list) => sel.name == "My Fair Lady" }).get._2
   assert(betList.size == 4)
  }

  "Calculator" should "generate liabilities" in {
    val selection = Selection(42, "Beeblebrox")
    val bet1 = Bet("BetMe-1", 4242424242L, selection, 2.0, 10.0, Currency.getInstance("EUR"))
    val bet2 = Bet("BetMe-2", 4242424668L, selection, 1.2, 2.0, Currency.getInstance("GBP"))
    val selToBetsMap = Map(selection -> Seq(bet1, bet2))
    val selectionLiabilities = BetCalculator.generateSelectionLiabilities(selToBetsMap)

    assert(selectionLiabilities.size == 2)

    val sortedLiabilities = selectionLiabilities.sortWith( (l1, l2) => l1.currencyCode > l2.currencyCode )

    assert(sortedLiabilities.head.currencyCode == "GBP")
    assert(sortedLiabilities.last.currencyCode == "EUR")
  }

  // TODO Missing test for ordering of entries

  // TODO Missing test for total liabilities generation (I'm tired)
}

object NoneProcessor extends EntityProcessor {
  def nextEntity: Option[Bet] = None
}
