package org.greenrd.seraphim

import java.io.File

import com.typesafe.scalalogging.LazyLogging

import scala.sys.process.{Process, ProcessBuilder}

class GitRepo (val path: File) extends LazyLogging {

  if (!path.exists()) {
    path.mkdir()
  }
  require(path.isDirectory)

  private def runProcess(processBuilder: ProcessBuilder): String =
    processBuilder.lineStream.mkString("\n")

 def gitCommand(command: String*): String =
   runProcess(Process("git" +: command, path))

}
