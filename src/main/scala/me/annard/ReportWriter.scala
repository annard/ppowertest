package me.annard

trait ReportWriter {
  def writeHeaders(headerList: Seq[String]): Boolean
  def writeValueList(valueList: Seq[AnyVal]): Boolean
}
