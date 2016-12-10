package org.greenrd.seraphim

import java.io.{File, PrintWriter}

import org.apache.commons.io.FileUtils
import org.scalatest.{FunSpec, Matchers}

class BaseCommitTest extends FunSpec with Matchers{

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

  private def setupTwoCommitRepo(): String = {
    prepareGitRepo()
    gitRepo.gitCommand("init")
    createFile()
    addAndCommitToGit("new file added")
    val baseCommit = gitRepo.gitCommand("rev-parse", "HEAD")
    gitRepo.gitCommand("checkout", "-b", "branch")
    modifyFile()
    addAndCommitToGit("file changed")
    baseCommit
  }

  describe("BaseCommit") {

    it("Finds a base-commit for a branch") {
      val baseCommit = setupTwoCommitRepo()
      BaseCommit(gitRepo).baseCommitForCurrentBranch("master") should be (baseCommit)
    }
  }

  describe("Diff") {
    it("Finds the diff between two git revisions") {
      setupTwoCommitRepo()
      val a = Diff(BaseCommit(gitRepo).baseCommitForCurrentBranch("master"), "HEAD", gitRepo).run()
      val seq = a.split('\n')
      seq.head +: seq.drop(2) should be (
        Seq(
          "diff --git a/test.txt b/test.txt",
          "--- a/test.txt",
          "+++ b/test.txt",
          "@@ -0,0 +1 @@",
          "+hello",
          "\\ No newline at end of file"
        )
      )
    }

  }
}
