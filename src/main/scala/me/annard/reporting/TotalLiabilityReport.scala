package me.annard.reporting

import me.annard.model.Liability

class TotalLiabilityReport extends ReportWriter {

  def writeHeaders(_valueList: Seq[Liability], builder: StringBuilder): StringBuilder = {
    headers.foreach( h => builder.append(f"$h%-20s") )
    builder.append("\n")
  }

  def writeValues(valueList: Seq[Liability], builder: StringBuilder): StringBuilder = {
    valueList.foreach( ss => builder.append(f"${ss.currencyCode}%-20s${ss.betCount}%-20d${ss.currencySymbol}${ss.totalStakes}%-20.2f${ss.currencySymbol}${ss.totalLiability}%-20.2f\n"))
    builder
  }

  def writeFooters(_valueList: Seq[Liability], builder: StringBuilder): StringBuilder = builder

  private def headers: Seq[String] = {
    Seq("Currency", "Num Bets", "Total Stakes", "Total Liability")
  }
}
