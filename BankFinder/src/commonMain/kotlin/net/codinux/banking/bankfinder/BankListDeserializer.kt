package net.codinux.banking.bankfinder

import kotlinx.serialization.json.Json
import net.codinux.log.logger


class BankListDeserializer {

    private val log by logger()


    fun loadBankList(): List<BankInfo> =
        try {
            val bankListJson = BankListLoader().loadBankList()

            val json = Json {
                ignoreUnknownKeys = true
            }

            json.decodeFromString(bankListJson)
        } catch (e: Throwable) {
            log.error(e) { "Could not load bank list" }

            emptyList()
        }

}