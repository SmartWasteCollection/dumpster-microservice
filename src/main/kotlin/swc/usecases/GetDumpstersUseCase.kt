package swc.usecases

import swc.entities.Dumpster

class GetDumpstersUseCase : UseCase<List<Dumpster>> {
    override fun execute(): List<Dumpster> =
        manager().getDumpsters()
}
