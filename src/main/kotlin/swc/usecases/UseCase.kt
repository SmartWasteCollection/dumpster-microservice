package swc.usecases

import swc.controllers.DumpsterManager
import swc.controllers.Manager

interface UseCase<T> {
    fun manager(): Manager = DumpsterManager
    fun execute(): T
}
