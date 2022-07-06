package swc.usecases

interface UseCase<T> {
    fun execute(): T
}
