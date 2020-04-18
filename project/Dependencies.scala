import sbt._

object Dependencies {

  lazy val Version = new {
    val cvsReader              = "1.3.6"
    val java                   = "1.11"
    val scala                  = "2.13.1"
    val scalaTest              = "3.1.1"
  }

  val cvsReader                 = "com.github.tototoshi"          %% "scala-csv"               % Version.cvsReader
  val scalaTest                 = "org.scalatest"                 %% "scalatest"               % Version.scalaTest

  val serverDeps =
    Seq(
      cvsReader,
      scalaTest             % Test
    )
}
