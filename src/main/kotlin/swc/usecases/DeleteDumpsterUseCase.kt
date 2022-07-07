package swc.usecases

class DeleteDumpsterUseCase(private val id: String) : UseCase<Unit> {
    override fun execute() = manager().deleteDumpster(id)
}
