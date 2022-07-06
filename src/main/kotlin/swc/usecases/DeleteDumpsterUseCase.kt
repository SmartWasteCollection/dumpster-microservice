package swc.usecases

import swc.controllers.DumpsterManager

class DeleteDumpsterUseCase(private val id: String) : UseCase<Unit> {
    override fun execute() =
        DumpsterManager.deleteDumpster(id)
}
