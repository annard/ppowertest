package me.annard

import java.util.Currency

import me.annard.model.{Bet, Selection, SelectionLiability, TotalLiability}


object BetCalculator {
  def apply(processor: EntityProcessor): Map[Selection, Seq[Bet]] = {
    val selectionToBetsMap: Map[Selection, Seq[Bet]] = updateSelections(processor)
    //val currencyToSelectionsMap: Map[Currency, Seq[Selection]] = {}
    selectionToBetsMap
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

  //def generateSelectionLiability(): Seq[SelectionLiability]

  //def generateTotalLiability(): Seq[TotalLiability]
}


case class BetCalculator(selectionLiability: Seq[SelectionLiability], totalLiability: Seq[TotalLiability]) {
  def selectionLiabilityReport(writer: ReportWriter): Boolean = {true}

  def totalLiabilityReport(writer: ReportWriter): Boolean = {true}

}
