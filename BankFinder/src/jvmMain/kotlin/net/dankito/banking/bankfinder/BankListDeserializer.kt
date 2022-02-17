package net.dankito.banking.bankfinder

import net.dankito.utils.serialization.JacksonJsonSerializer
import org.slf4j.LoggerFactory


actual class BankListDeserializer {

    companion object {
        const val BankListFileName = "BankList.json"

        private val log = LoggerFactory.getLogger(BankListDeserializer::class.java)
    }


    actual fun loadBankList(): List<BankInfo> {
        try {
            val bankListString = readBankListFile()

            JacksonJsonSerializer().deserializeList(bankListString, BankInfo::class.java)?.let {
                return it
            }
        } catch (e: Exception) {
            log.error("Could not load bank list", e)
        }

        return listOf()
    }

    fun readBankListFile(): String {
        val inputStream = BankFinderBase::class.java.classLoader.getResourceAsStream(BankListFileName)

        return inputStream.bufferedReader().readText()
    }

}