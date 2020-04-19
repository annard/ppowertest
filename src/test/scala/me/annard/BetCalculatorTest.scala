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

  "BetCalculator" should "handle betting data" in {
    val csvProc = CSVProcessor(csvUrl)
    val selectionMap = BetCalculator.updateSelections(csvProc)
    assert(selectionMap.count((_) => true) == 4)
    val betList = selectionMap.find( { case(sel, list) => sel.name == "My Fair Lady" }).get._2
   assert(betList.size == 4)
  }
}

object NoneProcessor extends EntityProcessor {
  def nextEntity: Option[Bet] = None
}
