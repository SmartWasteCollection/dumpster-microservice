package swc.controllers.azure

import com.azure.digitaltwins.core.BasicRelationship
import swc.entities.Dumpster

object AzureDTRelationshipFactory {
    fun from(collectionPointId: String, dumpster: Dumpster): BasicRelationship = BasicRelationship(
        "$collectionPointId-${dumpster.id}",
        collectionPointId,
        dumpster.id,
        AzureConstants.RELATIONSHIP_NAME
    )
}
