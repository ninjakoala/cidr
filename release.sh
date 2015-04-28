#!/bin/sh
lein vcs assert-committed
lein change version leiningen.release/bump-version release
lein vcs commit
git tag `cat project.clj | grep defproject | cut -d" " -f 3 | tr -d "\""`
lein deploy clojars
lein change version leiningen.release/bump-version
lein vcs commit
lein vcs push
