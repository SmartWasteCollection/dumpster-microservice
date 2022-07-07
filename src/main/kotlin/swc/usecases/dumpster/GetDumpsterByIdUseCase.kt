package swc.usecases.dumpster

import swc.entities.Dumpster
import swc.usecases.UseCase

class GetDumpsterByIdUseCase(private val id: String) : UseCase<Dumpster> {
    override fun execute(): Dumpster =
        manager().getDumpsterById(id)
}
