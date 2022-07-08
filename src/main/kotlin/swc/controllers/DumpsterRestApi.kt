package swc.controllers

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
import swc.adapters.DumpsterDeserialization.toDumpster
import swc.entities.Dumpster
import swc.usecases.dumpster.CloseDumpsterUseCase
import swc.usecases.dumpster.CreateDumpsterUseCase
import swc.usecases.dumpster.DeleteDumpsterUseCase
import swc.usecases.dumpster.GetDumpsterByIdUseCase
import swc.usecases.dumpster.GetDumpstersUseCase
import swc.usecases.dumpster.OpenDumpsterUseCase
import swc.usecases.dumpster.UpdateDumpsterVolumeUseCase

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
        val dumpster = this.getAsJsonObject("dumpster")
        dumpster["id"] ?: dumpster.addProperty("id", AzureDTManager.calculateNextDumpsterId())
    }.let {
        CreateDumpsterUseCase(
            it.getAsJsonObject("dumpster").toDumpster(),
            it.getAsJsonPrimitive("collectionPointId").asString
        ).execute()
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
}
