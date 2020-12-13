// shadow sbt-scalajs' crossProject and CrossType from Scala.js 0.6.x

import org.jetbrains.sbtidea.Keys.createRunnerProject

addCommandAlias(
  "ci",
  Seq(
    "project root",
    "versionDump",
    "scalafmtCheckAll",
    "scalafix --check",
    "test:scalafix --check",
    "+clean",
    "+test:compile",
    "+test",
    "intellij/updateIntellij",
    "intellij/test"
  ).mkString(";", ";", "")
)

addCommandAlias(
  "fix",
  Seq(
    "root/scalafmtAll",
    "root/scalafmtSbt",
    "root/scalafix",
    "root/test:scalafix"
  ).mkString(";", ";", "")
)

addCommandAlias(
  "release",
  Seq(
    "project root",
    "+publishSigned",
    "sonatypeBundleRelease"
  ).mkString(";", ";", "")
)

// See https://github.com/JetBrains/sbt-idea-plugin/issues/76 for
// why this contrived sequence of actions exists ...
addCommandAlias(
  "packageIntellijPlugin",
  Seq(
    "project root",
    "intellij/packageArtifact",
    "intellij/doPatchPluginXml",
    "intellij/packageArtifactZip"
  ).mkString(";", ";", "")
)

ThisBuild / scalaVersion := WeaverPlugin.scala213

ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.4.4"

lazy val root = project
  .in(file("."))
  .enablePlugins(ScalafixPlugin)
  .aggregate(intellij)
  .settings(WeaverPlugin.doNotPublishArtifact)


// #################################################################################################
// Intellij
// #################################################################################################

ThisBuild / intellijBuild := "203"
ThisBuild / intellijPluginName := "weaver-intellij"

import org.jetbrains.sbtidea.Keys._
import org.jetbrains.sbtidea.SbtIdeaPlugin
import sbt.Keys._
import sbt._

// Prevents sbt-idea-plugin from automatically
// downloading intellij binaries on startup
ThisBuild / doProjectSetup := {}

lazy val intellij = (project in file("modules/intellij"))
  .enablePlugins(SbtIdeaPlugin, BuildInfoPlugin)
  .disablePlugins(WeaverPlugin)
  .settings(
    scalaVersion := "2.13.4",
    intellijPlugins := Seq(
      "com.intellij.java".toPlugin,
      "org.intellij.scala:2020.3.16".toPlugin
    ),
    libraryDependencies ++= Seq(
      "io.get-coursier" %% "coursier"        % "2.0.0-RC6-24",
      "com.novocode"     % "junit-interface" % "0.11" % Test
    ),
    patchPluginXml := pluginXmlOptions { xml =>
      xml.version = version.value
    },
    // packageArtifact in publishPlugin := packagePlugin.value,
    packageMethod := PackagingMethod.Standalone(),
    scalacOptions ++= (WeaverPlugin.commonCompilerOptions),
    buildInfoKeys := Seq[BuildInfoKey](name, version),
    buildInfoPackage := "weaver.build",
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    packageArtifactZip := {
      val dump   = (ThisBuild / baseDirectory).value / "intellijPlugin"
      val result = packageArtifactZip.value
      IO.write(dump, result.getAbsolutePath)
      result
    }
  )

// #################################################################################################
// Misc
// #################################################################################################

lazy val versionDump =
  taskKey[Unit]("Dumps the version in a file named version")

versionDump := {
  val file = (ThisBuild / baseDirectory).value / "version"
  IO.write(file, (Compile / version).value)
}
