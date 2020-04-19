package me.annard

import java.util.Currency

import me.annard.model.{Bet, Selection, SelectionLiability}
import me.annard.reporting.ReportWriter

object BetCalculator {
  def apply(processor: EntityProcessor): BetCalculator = {
    val selectionToBetsMap: Map[Selection, Seq[Bet]] = updateSelections(processor)
    val selectionLiabilities: Seq[SelectionLiability] = generateSelectionLiability(selectionToBetsMap)
    new BetCalculator(selectionLiabilities)
  }

  @scala.annotation.tailrec
  def updateSelections(processor: EntityProcessor, selToBetsMap: Map[Selection, Seq[Bet]] = Map.empty[Selection, Seq[Bet]]): Map[Selection, Seq[Bet]] = {
    processor.nextEntity match {
      case None => selToBetsMap
      case Some(bet) => {
        val selection = bet.selection
        val betList = selToBetsMap.applyOrElse(selection, (_: Selection) => Seq.empty[Bet])

        updateSelections(processor, selToBetsMap + (selection -> {betList :+ bet}))
      }
    }
  }

  def generateSelectionLiability(selectionsMap: Map[Selection, Seq[Bet]] ): Seq[SelectionLiability] = {
    selectionsMap.flatMap { case (selection, selBetList) => {
      val currencyMap: Map[Currency, Seq[Bet]] = selBetList.groupBy[Currency](bet => bet.currency)
      currencyMap.flatMap { case(currency: Currency, curBetList: Seq[Bet]) => Seq(SelectionLiability(selection.name, currency, curBetList)) }
    }}.toSeq
  }

  //def generateTotalLiability(): Seq[TotalLiability]
}

case class BetCalculator(selectionLiabilities: Seq[SelectionLiability]) { //, totalLiability: Seq[TotalLiability]) {

  def selectionLiabilityReport(writer: ReportWriter): String = {
    val orderedLiabilities = selectionLiabilities.sortWith( (v1, v2) => {
      if (v1.currencyCode < v2.currencyCode)
        false
      else if (v1.currencyCode == v2.currencyCode)
        if (v1.totalLiability >= v2.totalLiability)
          true
        else
          false
      else
        true
    })
    val builder: StringBuilder = writer.writeHeaders(orderedLiabilities, new StringBuilder())
    writer.writeValues(orderedLiabilities, builder)
    writer.writeFooters(orderedLiabilities, builder)
    builder.mkString
  }

  def totalLiabilityReport(writer: ReportWriter): String = ""
}
