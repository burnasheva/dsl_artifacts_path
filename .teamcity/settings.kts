import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2019.2"

project {

    buildType(GetFile)
    buildType(GetFile2)
    buildType(PublishFile)
}

object GetFile : BuildType({
    name = "get file"

    dependencies {
        artifacts(PublishFile) {
            buildRule = lastFinished()
            cleanDestination = true
            artifactRules = "file.txt => subdirectory"
        }
    }
})

object GetFile2 : BuildType({
    name = "get file 2"

    dependencies {
        artifacts(AbsoluteId("VersionedSettings_ArtifactsPatch_PublishFile")) {
            id ="HARDCODED_ID"
            buildRule = lastFinished()
            cleanDestination = true
            artifactRules = "file.txt => subdirectory"
        }
    }
})

object PublishFile : BuildType({
    name = "publish file"

    artifactRules = "file.txt"

    steps {
        script {
            scriptContent = "touch file.txt"
        }
    }

    requirements {
        doesNotContain("teamcity.agent.jvm.os.name", "Windows")
    }
})
