package swc.controllers

import com.azure.digitaltwins.core.BasicRelationship
import swc.entities.CollectionPoint
import swc.entities.Dumpster

object AzureDTRelationshipFactory {
    fun from(collectionPoint: CollectionPoint, dumpster: Dumpster): BasicRelationship = BasicRelationship(
        "${collectionPoint.id}-${dumpster.id}",
        collectionPoint.id,
        dumpster.id,
        AzureConstants.RELATIONSHIP_NAME
    )
}
