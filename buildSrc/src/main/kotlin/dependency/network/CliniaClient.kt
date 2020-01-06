package dependency.network

import dependency.Dependency


object CliniaClient : Dependency {

    override val group = "ca.clinia"
    override val artifact = "cliniasearch-client-kotlin"
    override val version = "1.0.0"
}