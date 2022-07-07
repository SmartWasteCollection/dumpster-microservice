package swc.usecases.dumpster

import swc.usecases.UseCase

class DeleteDumpsterUseCase(private val id: String) : UseCase<Unit> {
    override fun execute() = manager().deleteDumpster(id)
}
