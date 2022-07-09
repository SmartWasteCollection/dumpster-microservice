package swc.controllers.api

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import swc.adapters.DumpsterDeserialization.parse
import swc.controllers.api.errors.DumpsterCreationFormatException
import swc.entities.CollectionPoint
import swc.entities.Dumpster
import swc.entities.WasteName
import swc.usecases.dumpster.CloseDumpsterUseCase
import swc.usecases.dumpster.CreateDumpsterUseCase
import swc.usecases.dumpster.DeleteDumpsterUseCase
import swc.usecases.dumpster.GetCollectionPointFromDumpsterIdUseCase
import swc.usecases.dumpster.GetDumpsterByIdUseCase
import swc.usecases.dumpster.GetDumpstersUseCase
import swc.usecases.dumpster.OpenDumpsterUseCase
import swc.usecases.dumpster.UpdateDumpsterVolumeUseCase
import swc.usecases.dumpster.UpdateDumpsterWorkingUseCase

@RestController
@CrossOrigin()
@RequestMapping("/dumpsters")
class DumpsterRestApi {

    @GetMapping
    fun getDumpsters(): List<Dumpster> = GetDumpstersUseCase().execute()

    @GetMapping("/{id}")
    fun getDumpsterById(@PathVariable id: String): Dumpster = GetDumpsterByIdUseCase(id).execute()

    @PostMapping("/")
    fun createDumpster(@RequestBody body: String) = parse(body).apply {
    }.let {
        val values = it.getAsJsonObject("dumpster")
        try {
            CreateDumpsterUseCase(
                Dumpster.from(
                    values["capacity"].asDouble,
                    WasteName.valueOf(values["wasteName"].asString)
                ),
                it.getAsJsonPrimitive("collectionPointId").asString
            ).execute()
        } catch (e: Exception) {
            throw DumpsterCreationFormatException("Creation object is not formatted correctly")
        }
    }

    @DeleteMapping("/{id}")
    fun deleteDumpster(@PathVariable id: String): Dumpster = DeleteDumpsterUseCase(id).execute()

    @PutMapping("/open/{id}")
    fun openDumpster(@PathVariable id: String): Dumpster = OpenDumpsterUseCase(id).execute()

    @PutMapping("/close/{id}")
    fun closeDumpster(@PathVariable id: String): Dumpster = CloseDumpsterUseCase(id).execute()

    @PutMapping("/volume/{id}")
    fun updateVolume(@PathVariable id: String, @RequestBody body: String): Dumpster = UpdateDumpsterVolumeUseCase(
        id,
        parse(body).getAsJsonPrimitive("volume").asDouble,
    ).execute()

    @GetMapping("/{id}/collectionpoint")
    fun getCollectionPoint(@PathVariable id: String): CollectionPoint =
        GetCollectionPointFromDumpsterIdUseCase(id).execute()

    @PutMapping("/{id}/working")
    fun updateDumpsterWorking(@PathVariable id: String, @RequestBody value: String): Dumpster = parse(value).let {
        UpdateDumpsterWorkingUseCase(id, it["working"].asBoolean).execute()
    }
}
