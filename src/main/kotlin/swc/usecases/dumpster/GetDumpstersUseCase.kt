package swc.usecases.dumpster

import swc.entities.Dumpster
import swc.usecases.UseCase

class GetDumpstersUseCase : UseCase<List<Dumpster>> {
    override fun execute(): List<Dumpster> =
        manager().getDumpsters()
}
