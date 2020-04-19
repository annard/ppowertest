package me.annard

import java.util.Currency

import me.annard.model.{Bet, Selection, SelectionLiability, TotalLiability}
import me.annard.reporting.ReportWriter

object BetCalculator {
  def apply(processor: EntityProcessor): BetCalculator = {
    val selectionToBetsMap: Map[Selection, Seq[Bet]] = updateSelections(processor)
    val selectionLiabilities: Seq[SelectionLiability] = generateSelectionLiabilities(selectionToBetsMap)
    val totalLiabilities: Seq[TotalLiability] = generateTotalLiabilities(selectionLiabilities)
    new BetCalculator(selectionLiabilities, totalLiabilities)
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

  def generateSelectionLiabilities(selectionsMap: Map[Selection, Seq[Bet]]): Seq[SelectionLiability] = {
    selectionsMap.flatMap { case (selection, selBetList) => {
      val currencyMap: Map[Currency, Seq[Bet]] = selBetList.groupBy[Currency](bet => bet.currency)
      currencyMap.flatMap { case (currency: Currency, curBetList: Seq[Bet]) => Seq(SelectionLiability(selection.name, currency, curBetList)) }
    }}.toSeq
  }

  def generateTotalLiabilities(selectionLiabilities: Seq[SelectionLiability]): Seq[TotalLiability] = {
    val currencyMap: Map[Currency, Seq[SelectionLiability]] = selectionLiabilities.groupBy[Currency](selLiab => selLiab.currency)
    currencyMap.flatMap { case (currency: Currency, selectionLiabs: Seq[SelectionLiability]) => {
      val totalLiab = selectionLiabs.foldLeft(new TotalLiability(currency, 0L, 0.0, 0.0))((tl, selLiab) => {
         new TotalLiability(currency, tl.betCount + selLiab.betCount, tl.totalStakes + selLiab.totalStakes, tl.totalLiability + selLiab.totalLiability)
      })
      Seq(totalLiab)
    }}.toSeq
  }
}

case class BetCalculator(selectionLiabilities: Seq[SelectionLiability], totalLiabilities: Seq[TotalLiability]) {

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

  def totalLiabilityReport(writer: ReportWriter): String = {
    val orderedLiabilities = totalLiabilities.sortBy(_.currencyCode)(Ordering[String].reverse)
    // TODO Refactor this and function above to simplify code using one implementation
    val builder: StringBuilder = writer.writeHeaders(orderedLiabilities, new StringBuilder())
    writer.writeValues(orderedLiabilities, builder)
    writer.writeFooters(orderedLiabilities, builder)
    builder.mkString
  }
}
