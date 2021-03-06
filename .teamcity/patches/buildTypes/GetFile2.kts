package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'GetFile2'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("GetFile2")) {
    dependencies {
        expect(RelativeId("PublishFile")) {
            artifacts {
                buildRule = lastFinished()
                cleanDestination = true
                artifactRules = "file.txt => subdirectory"
            }
        }
        update(RelativeId("PublishFile")) {
            artifacts {
                buildRule = lastSuccessful()
                cleanDestination = true
                artifactRules = "file.txt => subdirectory"
            }
        }

    }
}
