plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "1.1.2"
}

rootProject.name = "dumpster-microservice"

gitHooks {
    commitMsg { conventionalCommits() }
    preCommit {
        tasks("ktlintCheck")
    }
    createHooks(overwriteExisting = true)
}
