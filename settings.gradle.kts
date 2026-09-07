plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "2.1.24"
}

rootProject.name = "dumpster-microservice"

gitHooks {
    commitMsg { conventionalCommits() }
    preCommit {
        tasks("ktlintCheck")
    }
    createHooks(overwriteExisting = true)
}
