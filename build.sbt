name := "ppowertest"

import Dependencies._

// *****************************************************************************
// Projects
// *****************************************************************************

lazy val ask =
  project
    .in(file("."))
    .settings(
      settings
    )
    .settings(
      libraryDependencies ++= serverDeps
    )

// *****************************************************************************
// Settings
// *****************************************************************************

lazy val settings =
  commonSettings

lazy val commonSettings =
  Seq(
    scalaVersion := Version.scala,
    organization := "me.annard",
    organizationName := "Annard Brouwer",
    startYear := Some(2020),
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-target:jvm-" + Version.java,
      "-encoding", "UTF-8",
      "-Wunused:imports",
    ),
    envVars in Test := Map("SCALA_ENV" -> "test"),
    Compile / run / fork := true,
    Compile / unmanagedSourceDirectories := Seq((Compile / scalaSource).value),
    Test / unmanagedSourceDirectories := Seq((Test / scalaSource).value),
  )
