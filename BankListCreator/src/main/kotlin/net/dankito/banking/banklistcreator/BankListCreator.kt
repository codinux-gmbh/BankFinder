package net.dankito.banking.banklistcreator

import net.dankito.banking.bankfinder.BankInfo
import net.dankito.banking.bankfinder.DetailedBankInfo
import net.dankito.banking.banklistcreator.parser.DeutscheKreditwirtschaftBankListParser
import net.dankito.banking.banklistcreator.prettifier.BankListPrettifier
import net.dankito.banking.banklistcreator.prettifier.BankListPrettifierOption
import net.dankito.utils.serialization.JacksonJsonSerializer
import org.slf4j.LoggerFactory
import java.io.File


open class BankListCreator @JvmOverloads constructor(
    protected open val parser: DeutscheKreditwirtschaftBankListParser = DeutscheKreditwirtschaftBankListParser(),
    protected open val prettifier: BankListPrettifier = BankListPrettifier()
) {

    companion object {
        private val log = LoggerFactory.getLogger(BankListCreator::class.java)
    }


    open fun createBankListFromDeutscheKreditwirtschaftXlsxFile(deutscheKreditwirtschaftXlsxFile: File,
                                                                bankListOutputFile: File) {

        val banks = parser.parse(deutscheKreditwirtschaftXlsxFile)

        saveBankListAsJson(banks, bankListOutputFile)
    }

    open fun createDetailedAndPrettifiedBankListFromDeutscheKreditwirtschaftXlsxFile(
        deutscheKreditwirtschaftXlsxFile: File, detailedBankListOutputFile: File,
        prettifiedBankListOutputFile: File, prettifyOptions: List<BankListPrettifierOption>,
        finTsServerAddressFinderOutputFile: File? = null) {

        val allBanks = parser.parse(deutscheKreditwirtschaftXlsxFile)

        saveBankListAsJson(allBanks, detailedBankListOutputFile)
        log.info("Wrote ${allBanks.size} detailed bank infos to $detailedBankListOutputFile")

        val mappedBanks = allBanks.map { BankInfo(it.name, it.bankCode, it.bic, it.postalCode, it.city, it.pinTanAddress, it.pinTanVersion) }
        val prettifiedBanks = prettifier.prettify(mappedBanks, prettifyOptions)
        saveBankListAsJson(prettifiedBanks, prettifiedBankListOutputFile)
        log.info("Wrote ${prettifiedBanks.size} prettified bank infos to $prettifiedBankListOutputFile")

        finTsServerAddressFinderOutputFile?.let {
            createFinTsServerAddressFinder(allBanks, it)
        }
    }

    open fun saveBankListAsJson(banks: List<BankInfo>, bankListOutputFile: File) {
        JacksonJsonSerializer().serializeObject(banks, bankListOutputFile)
    }


    protected open fun createFinTsServerAddressFinder(allBanks: List<DetailedBankInfo>, finTsServerAddressFinderOutputFile: File) {
        val finTsServerAddressByBankCode = allBanks.filterNot { it.pinTanAddress.isNullOrBlank() }.associate { it.bankCode to it.pinTanAddress!! }

        finTsServerAddressFinderOutputFile.parentFile.mkdirs()

        val writer = finTsServerAddressFinderOutputFile.bufferedWriter()

        writer.appendLine("package net.dankito.banking.fints.util")
        writer.newLine()

        writer.appendLine("open class FinTsServerAddressFinder {")
        writer.newLine()

        writer.appendLine("\topen fun findFinTsServerAddress(bankCode: String): String? {")
        writer.appendLine("\t\treturn finTsServerAddressByBankCode[bankCode]")
        writer.appendLine("\t}")
        writer.newLine()
        writer.newLine()

        writer.appendLine("\tprotected open val finTsServerAddressByBankCode: Map<String, String> = mapOf(")

        finTsServerAddressByBankCode.forEach { bankCode, finTsServerAddress ->
            writer.appendLine("\t\t\"$bankCode\" to \"$finTsServerAddress\",")
        }

        writer.appendLine("\t)")
        writer.newLine()

        writer.appendLine("}")

        writer.close()

        log.info("Wrote FinTsServerAddressFinder class to $finTsServerAddressFinderOutputFile")
    }

}