package net.dankito.banking.bankfinder


open class InMemoryBankFinder() : BankFinderBase(), IBankFinder {

    constructor(bankList: List<BankInfo>) : this() {
        this.bankListField = bankList
    }


    protected var bankListField: List<BankInfo>? = null


    override fun findBankByBankCode(query: String, maxItems: Int?): List<BankInfo> {
        if (query.isEmpty()) {
            return getBankList(maxItems)
        }

        return getBankList().asSequence().filter { it.bankCode.startsWith(query) }
            .max(maxItems)
    }

    override fun findBankByNameBankCodeOrCityForNonEmptyQuery(query: String, maxItems: Int?): List<BankInfo> {
        val queryPartsLowerCase = query.lowercase().split(" ", "-")

        return getBankList().asSequence().filter { bankInfo ->
            checkIfAllQueryPartsMatchBankNameBankCodeOrCity(queryPartsLowerCase, bankInfo)
        }
            .max(maxItems)
    }

    protected open fun checkIfAllQueryPartsMatchBankNameBankCodeOrCity(queryPartsLowerCase: List<String>, bankInfo: BankInfo): Boolean {
        for (queryPartLowerCase in queryPartsLowerCase) {
            if (checkIfQueryMatchesBankNameBankCodeOrCity(bankInfo, queryPartLowerCase) == false) {
                return false
            }
        }

        return true
    }

    protected open fun checkIfQueryMatchesBankNameBankCodeOrCity(bankInfo: BankInfo, queryLowerCase: String): Boolean {
        return bankInfo.name.lowercase().contains(queryLowerCase)
                || bankInfo.bankCode.startsWith(queryLowerCase)
                || bankInfo.city.lowercase().startsWith(queryLowerCase)
                || bankInfo.branchesInOtherCities.any { it.lowercase().startsWith(queryLowerCase) }
    }


    override fun searchBankByBic(bic: String): BankInfo? {
        return getBankList().firstOrNull { it.bic == bic }
    }


    override fun preloadBankList() {
        findBankByBankCode("")
    }


    override fun getBankList(maxItems: Int?): List<BankInfo> {
        bankListField?.let {
            return it.asSequence().max(maxItems)
        }

        val bankList = loadBankList()

        this.bankListField = bankList

        return bankList.asSequence().max(maxItems)
    }

    fun Sequence<BankInfo>.max(maxItems: Int? = null): List<BankInfo> =
        this.take(maxItems ?: Int.MAX_VALUE)
            .toList()

}