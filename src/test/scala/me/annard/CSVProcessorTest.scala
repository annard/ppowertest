package me.annard

import java.io.File
import java.util.Currency

import org.scalatest.flatspec.AnyFlatSpec

class CSVProcessorTest extends AnyFlatSpec {

  "CSV" should "parse list with good values" in {
    val valueList = List("BetMe-1", "4242424242", "42", "Beeblebrox", "2.0", "10.0", "EUR")
    val processorClass = new CSVProcessor(null)
    val myBet = processorClass.parseListValues(valueList)

    val expectedBet = Bet("BetMe-1", 4242424242L, Selection(42, "Beeblebrox"), 2.0, 10.0, Currency.getInstance("EUR"))
    assert(myBet == expectedBet)
  }

  val csvPath = getClass().getResource("/test_data.csv").getPath()
  val csvUrl = new File(csvPath).toURI().toURL()

  "Processor" should "open a CSV file" in {
    val csvProc = CSVProcessor(csvUrl)
    val expectedBet = Bet("Bet-10", 1489490156000L, Selection(1, "My Fair Lady"), 0.5, 6.0, Currency.getInstance("GBP"))
    assert(csvProc.nextEntity.get == expectedBet)
  }

  "Processor" should "read all entities" in {
    val csvProc = CSVProcessor(csvUrl)

    assert(processEntityCount(csvProc) == 24)
  }

  private def processEntityCount(proc: CSVProcessor, count: Int = 0): Int = {
    proc.nextEntity match {
      case None => count
      case _ => processEntityCount(proc, count + 1)
    }
  }
}