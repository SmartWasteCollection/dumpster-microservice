package swc.controllers

object AzureQueries {
    private const val IS_DUMPSTER_DT_CONDITION = "WHERE IS_OF_MODEL('${AzureConstants.DUMPSTER_DT_MODEL_ID}')"
    const val GET_ALL_DT = "SELECT * FROM digitaltwins"
    const val GET_COUNT_DT_QUERY = "SELECT COUNT() FROM digitaltwins"
    const val GET_ALL_DUMPSTERS_QUERY = "$GET_ALL_DT $IS_DUMPSTER_DT_CONDITION"
    const val GET_DUMPSTERS_COUNT = "$GET_COUNT_DT_QUERY $IS_DUMPSTER_DT_CONDITION"
}