package io.prediction.tools

import org.json4s._
import org.json4s.jackson.JsonMethods._
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
      pioVersion := PIOBuild(baseDirectory.value)
    )
  }

  import io.prediction.tools.PIOBuildPlugin.autoImport._

  override lazy val buildSettings = basePIOBuildSettings
  override def trigger = allRequirements
}

object PIOBuild {
  val defaultPIOVersion = "0.8.6" // when this plugin first appeared

  def apply(baseDirectory: File): String = {
    lazy implicit val formats = DefaultFormats
    val engineJsonFile = new File(baseDirectory, "engine.json")
    val engineJson = parse(IO.read(engineJsonFile))
    (engineJson \ "pioVersion").extractOpt[String].getOrElse(defaultPIOVersion)
  }
}
