package org.greenrd.seraphim

case class BaseCommit (gitRepo: GitRepo) {

  def baseCommitForCurrentBranch(): String =
    gitRepo.gitCommand("merge-base", "HEAD", "master")

}
