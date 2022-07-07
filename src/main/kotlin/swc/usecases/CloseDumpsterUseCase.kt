package swc.usecases

class CloseDumpsterUseCase(private val id: String) : UseCase<Unit> {
    override fun execute() =
        manager().closeDumpster(id)
}
