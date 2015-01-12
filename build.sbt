organization := "org.myproject"

name := "lens-intellij"

version := "0.2-SNAPSHOT"

scalaVersion := "2.11.4"

scalacOptions ++= Seq("-feature", "-language:postfixOps")

resolvers += "SonaType" at "https://oss.sonatype.org/content/groups/public"

libraryDependencies ++= Seq(
  "com.chuusai"                 %% "shapeless"       % "2.0.0",
  "org.scalatest"               %% "scalatest"       % "2.2.1"        % Test
)

mainClass := Some("jmx.util.Main")


