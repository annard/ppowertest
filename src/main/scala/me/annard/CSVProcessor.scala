package me.annard

import java.io.File
import java.net.URL
import java.util.Currency

import com.github.tototoshi.csv.CSVReader

final class CSVProcessor(reader: CSVReader) extends EntityProcessor {

  def nextEntity: (Option[Bet]) = {
    val serialisedEntity = this.reader.readNext()
    serialisedEntity match {
      case None => None
      case Some(strList) => {
        val bet = parseListValues(strList)
        Some(bet)
      }
    }
  }

  def parseListValues(valueList: List[String]): Bet = {
    // TODO using the CSV header format to dynamically get the fields instead of hard-coding them by index
    // TODO harden parsing to return descriptive error when a field can't be converted
    val betId = valueList(0).trim()
    val betTimestamp = valueList(1).trim().toLong
    val selectionId = valueList(2).trim().toLong
    val selectionName = valueList(3).trim()
    val stake = valueList(4).trim().toDouble
    val price = valueList(5).trim().toDouble
    val currency = Currency.getInstance(valueList(6).trim())

    val selection = Selection(selectionId, selectionName)
    Bet(betId, betTimestamp, selection, stake, price, currency)
  }
}

object CSVProcessor {
  def apply(source: URL): CSVProcessor = {
    val file = new File(source.getFile())
    val reader = CSVReader.open(file)
    // FIXME: can fail if no headers present, the package doesn't deal gracefully with headers :-(
    reader.readNext()
    new CSVProcessor(reader)
  }
}
