# pio-build

SBT plugin that complements the `pio build` command.

This is a very simple plugin that provides a new build settings to all engine
templates: `pioVersion`.

## Problem

Every engine template used to have a `build.sbt` file with PredictionIO version
hard-coded, e.g.

```scala
libraryDependencies ++= Seq(
  "io.prediction"    %% "core"        % "0.8.6" % "provided",
  "org.apache.spark" %% "spark-core"  % "1.2.0" % "provided",
  "org.apache.spark" %% "spark-mllib" % "1.2.0" % "provided")
```

When one builds an engine template (`pio build`) with a mismatched version of
PredictionIO, such as 0.9.0, the build process will succeed without error. When
doing `pio train`, however, strange errors could occur, such as the infamous
`java.lang.AbstractMethodError`, with no useful information for debugging.

## Solution

When `pio build` is run, it automatically generates a `pio.sbt` file at the
engine's project root directory before building the engine. The file sets
`pioVersion` to the correct value. Engine templates can use this value instead
of a hard coded one, e.g.

```scala
libraryDependencies ++= Seq(
  "io.prediction"    %% "core"        % pioVersion.value % "provided",
  "org.apache.spark" %% "spark-core"  % "1.2.0"          % "provided",
  "org.apache.spark" %% "spark-mllib" % "1.2.0"          % "provided")
```

## Usage

Add the following to your engine template's `project/pio-build.sbt`

```scala
addSbtPlugin("io.prediction" % "pio-build" % "0.9.0")
```

and modify your engine template's `build.sbt` to use `pioVersion` as shown
above.
