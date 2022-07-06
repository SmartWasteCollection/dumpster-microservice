package swc.usecases

import swc.controllers.DumpsterManager
import swc.entities.Dumpster

class GetDumpstersUseCase : UseCase<List<Dumpster>> {
    override fun execute(): List<Dumpster> =
        DumpsterManager.getDumpsters()
}
