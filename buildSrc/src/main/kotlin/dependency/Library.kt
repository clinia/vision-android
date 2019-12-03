package dependency


object Library: Dependency  {

    override val group = "ca.clinia"
    override val artifact = "vision"
    override val version = "1.0.0-beta05"

    val packageName = "$group:$artifact-android"

    val artifactCore = "$artifact-core"
    val artifactCoreCommon = "$artifactCore-common"
    val artifactCoreJvm = "$artifactCore-jvm"

    val artifactHelper = "$artifact-helper"
    val artifactHelperCommon = "$artifactHelper-common"
    val artifactHelperJvm = "$artifactHelper-jvm"
    val artifactHelperAndroid = "$artifact-android"
}