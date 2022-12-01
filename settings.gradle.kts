plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "1.0.25"
}

rootProject.name = "dumpster-microservice"

gitHooks {
    commitMsg { conventionalCommits() }
    preCommit {
        tasks("ktlintCheck")
    }
    createHooks(overwriteExisting = true)
}
