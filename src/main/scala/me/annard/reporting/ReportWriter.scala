package me.annard.reporting

import me.annard.model.Liability

trait ReportWriter {
  def writeHeaders(valueList: Seq[Liability], builder: StringBuilder): StringBuilder
  def writeValues(valueList: Seq[Liability], builder: StringBuilder): StringBuilder
  def writeFooters(valueList: Seq[Liability], builder: StringBuilder): StringBuilder
}
