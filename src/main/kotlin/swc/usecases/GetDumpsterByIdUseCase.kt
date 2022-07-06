package swc.usecases

import swc.controllers.DumpsterManager
import swc.entities.Dumpster

class GetDumpsterByIdUseCase(private val id: String) : UseCase<Dumpster> {
    override fun execute(): Dumpster =
        DumpsterManager.getDumpsterById(id)
}
