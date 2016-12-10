package org.greenrd.seraphim

case class BaseCommit (gitRepo: GitRepo) {

  def baseCommitForCurrentBranch(baseBranch: String): String =
    baseCommitBetweenRevisions("HEAD", baseBranch)

  def baseCommitBetweenRevisions(revision1: String, revision2: String): String =
    gitRepo.gitCommand("merge-base", revision1, revision2)

}
