import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "proggis"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
        "org.scalatest" % "scalatest_2.9.2" % "2.0.M3" % "test"
        

    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )
    
    testOptions in Test := Nil

}
