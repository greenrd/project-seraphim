package org.greenrd.seraphim

/**
  * Created by davenpcm on 12/10/16.
  */
case class Diff(revision1: String, revision2: String, gitRepo: GitRepo){
  def run(): String = gitRepo.gitCommand("diff", revision1, revision2)
}
