package org.greenrd.seraphim

import java.io.{File, PrintWriter}

import org.apache.commons.io.FileUtils
import org.scalatest.FunSpec

class BaseCommitTest extends FunSpec {

  private val gitRepoName = "temp-git"

  private def prepareGitRepo(): GitRepo = {
    val gitRepoFolder = new File("target", gitRepoName)
    if (gitRepoFolder.exists()) {
      FileUtils.deleteDirectory(gitRepoFolder)
    }
    new GitRepo(gitRepoFolder)
  }

  private val gitRepo = prepareGitRepo()

  private val fileName = "test.txt"
  private val file = new File(gitRepo.path, fileName)

  private def createFile(): Unit = file.createNewFile()

  private def modifyFile(): Unit = {
    val pw = new PrintWriter(file)
    pw.write("hello")
    pw.close()
  }

  private def addAndCommitToGit(gitMessage: String): Unit = {
    gitRepo.gitCommand("add", fileName)
    gitRepo.gitCommand("commit", "-m", gitMessage)
  }

  describe("BaseCommit") {

    it("Finds a base-commit for a branch") {
      prepareGitRepo()

      gitRepo.gitCommand("init")
      createFile()
      addAndCommitToGit("new file added")
      val baseCommit = gitRepo.gitCommand("rev-parse", "HEAD")

      gitRepo.gitCommand("checkout", "-b", "branch")
      modifyFile()
      addAndCommitToGit("file changed")

      assert(BaseCommit(gitRepo).baseCommitForCurrentBranch() === baseCommit)
    }
  }
}
