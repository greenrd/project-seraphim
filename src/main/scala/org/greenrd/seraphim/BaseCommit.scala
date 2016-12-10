package org.greenrd.seraphim

case class BaseCommit (gitRepo: GitRepo) {

  def baseCommitForCurrentBranch(branch: String): String =
    gitRepo.gitCommand("merge-base", "HEAD", branch)

}
