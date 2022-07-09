package swc.usecases

import swc.controllers.Manager
import swc.controllers.azure.AzureDTManager

interface UseCase<T> {
    fun execute(): T
    fun manager(): Manager = AzureDTManager
}
