package swc.usecases

import swc.controllers.AzureDTManager
import swc.controllers.Manager

interface UseCase<T> {
    fun execute(): T
    fun manager(): Manager = AzureDTManager
}
