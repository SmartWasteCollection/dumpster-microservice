package swc.controllers

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import swc.adapters.CollectionPointsDeserialization.toCollectionPoint
import swc.adapters.DumpsterDeserialization.parse
import swc.entities.CollectionPoint
import swc.entities.Dumpster
import swc.usecases.collectionpoint.CreateCollectionPointUseCase
import swc.usecases.collectionpoint.DeleteCollectionPointUseCase
import swc.usecases.collectionpoint.GetCollectionPointByIdUseCase
import swc.usecases.collectionpoint.GetCollectionPointsUseCase
import swc.usecases.collectionpoint.GetDumpstersInCollectionPointUseCase

@RestController
@CrossOrigin()
@RequestMapping("/collectionpoints")
class CollectionPointRestApi {

    @GetMapping
    fun getCollectionPoints(): List<CollectionPoint> = GetCollectionPointsUseCase().execute()

    @GetMapping("/{id}")
    fun getCollectionPointById(@PathVariable id: String): CollectionPoint = GetCollectionPointByIdUseCase(id).execute()

    @PostMapping("/")
    fun createCollectionPoint(@RequestBody body: String) = parse(body).apply {
        this["id"] ?: this.addProperty("id", AzureDTManager.calculateNextCollectionPointId())
    }.let {
        CreateCollectionPointUseCase(it.toCollectionPoint()).execute()
    }

    @DeleteMapping("/{id}")
    fun deleteCollectionPoint(@PathVariable id: String): CollectionPoint = DeleteCollectionPointUseCase(id).execute()

    @GetMapping("/{id}/dumpsters")
    fun getDumpstersInCollectionPoint(@PathVariable id: String): List<Dumpster> =
        GetDumpstersInCollectionPointUseCase(id).execute()
}
