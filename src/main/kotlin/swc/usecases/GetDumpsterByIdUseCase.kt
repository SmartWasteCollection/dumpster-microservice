package swc.usecases

import swc.entities.Dumpster

class GetDumpsterByIdUseCase(private val id: String) : UseCase<Dumpster> {
    override fun execute(): Dumpster =
        manager().getDumpsterById(id)
}
