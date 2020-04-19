package me.annard.reporting

import me.annard.model.SelectionLiability

trait ReportWriter {
  def writeHeaders(valueList: Seq[SelectionLiability], builder: StringBuilder): StringBuilder
  def writeValues(valueList: Seq[SelectionLiability], builder: StringBuilder): StringBuilder
  def writeFooters(valueList: Seq[SelectionLiability], builder: StringBuilder): StringBuilder
}
