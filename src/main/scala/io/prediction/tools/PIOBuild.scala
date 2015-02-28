package io.prediction.tools

import sbt.Keys._
import sbt._

import java.io.File

object PIOBuildPlugin extends AutoPlugin {
  object autoImport {
    val pioVersion = SettingKey[String](
      "pio-version",
      "The version of PredictionIO used for building."
    )
    lazy val basePIOBuildSettings: Seq[Def.Setting[_]] = Seq(
      pioVersion := PIOBuild.defaultPIOVersion
    )
  }

  import io.prediction.tools.PIOBuildPlugin.autoImport._

  override lazy val buildSettings = basePIOBuildSettings
  override def trigger = allRequirements
}

object PIOBuild {
  val defaultPIOVersion = "0.9.0" // when this plugin first appeared
}
