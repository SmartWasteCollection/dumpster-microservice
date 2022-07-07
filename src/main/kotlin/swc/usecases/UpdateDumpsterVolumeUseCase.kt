package swc.usecases

class UpdateDumpsterVolumeUseCase(
    private val id: String,
    private val newVolume: Double
) : UseCase<Unit> {
    override fun execute() =
        manager().updateVolume(id, newVolume)
}
