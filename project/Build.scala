import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName         = "ExerExchange"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.github.twitter" % "bootstrap" % "2.1.0",
    "se.radley" %% "play-plugins-salat" % "1.0.9"
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    resolvers += "webjars" at "http://webjars.github.com/m2",
    resolvers += "OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
    routesImport += "se.radley.plugin.salat.Binders._",
    templatesImport += "org.bson.types.ObjectId"
  )

}
