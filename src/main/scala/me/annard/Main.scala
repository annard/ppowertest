package me.annard

import java.io.File

import me.annard.reporting.{SelectionLiabilityReport, TotalLiabilityReport}

object Main {
  def main(args: Array[String]): Unit = {
    if (args.size == 0) {
      println("Need a path to a CSV file!")
      System.exit(666)
    }
    val inputPath = args.head
    val csvUrl = new File(inputPath).toURI().toURL()
    val csvProc = CSVProcessor(csvUrl)
    val betCalculator = BetCalculator(csvProc)
    val report1 = new SelectionLiabilityReport

    print(betCalculator.selectionLiabilityReport(report1))
    print(" \n")

    val report2 = new TotalLiabilityReport
    print(betCalculator.totalLiabilityReport(report2))
  }
}
