package swc.usecases

import swc.entities.Dumpster
import swc.entities.WasteName

class GetDumpstersUseCase : UseCase<List<Dumpster>> {
    override fun execute(): List<Dumpster> = listOf(
        Dumpster.from(1234.0, WasteName.ORGANIC),
        Dumpster.from(1234.0, WasteName.GLASS),
    )
}
