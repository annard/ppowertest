package me.annard.reporting

import me.annard.model.SelectionLiability

case class SelectionLiabilityReport() extends ReportWriter {

  def writeHeaders(_valueList: Seq[SelectionLiability], builder: StringBuilder): StringBuilder = {
    headers.foreach( h => builder.append(s"$h\t") )
    builder.append("\n")
  }

  def writeValues(valueList: Seq[SelectionLiability], builder: StringBuilder): StringBuilder = {
    valueList.foreach( ss => builder.append(s"${ss.name}\t${ss.currencyCode}\t${ss.size}\t${ss.totalStakes}\t${ss.totalLiability}\n"))
    builder
  }

  def writeFooters(_valueList: Seq[SelectionLiability], builder: StringBuilder): StringBuilder = builder

  private def headers: Seq[String] = {
    Seq("Selection Name", "Currency", "Num Bets", "Total Stakes", "Total Liability")
  }
}