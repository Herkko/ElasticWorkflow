import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "proggis"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
        "org.scalatest" % "scalatest_2.9.2" % "2.0.M3" % "test"
        
        //selenium tests
     //   "org.seleniumhq.selenium" % "selenium-java" % "2.23.1" % "test",
     //   "org.openqa.selenium.server" % "selenium-server-coreless" % "1.0-20081010.060147",
        
        //jetty server
     //   "org.eclipse.jetty" % "jetty-webapp" % "7.0.2.RC0" % "test",
     //   "org.eclipse.jetty" % "jetty-server" % "7.0.2.RC0" % "test"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )
    
    testOptions in Test := Nil

}
