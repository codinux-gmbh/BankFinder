package net.codinux.banking.bankfinder


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

    override fun findBankByNameBicOrCityForNonEmptyQuery(query: String, maxItems: Int?): List<BankInfo> {
        val queryPartsLowerCase = query.lowercase().split(" ", "-")

        return getBankList().asSequence().filter { bankInfo ->
            checkIfAllQueryPartsMatchBankNameBicOrCity(queryPartsLowerCase, bankInfo)
        }
            .max(maxItems)
    }

    protected open fun checkIfAllQueryPartsMatchBankNameBicOrCity(queryPartsLowerCase: List<String>, bankInfo: BankInfo): Boolean {
        for (queryPartLowerCase in queryPartsLowerCase) {
            if (checkIfQueryMatchesBankNameBicOrCity(bankInfo, queryPartLowerCase) == false) {
                return false
            }
        }

        return true
    }

    protected open fun checkIfQueryMatchesBankNameBicOrCity(bankInfo: BankInfo, queryLowerCase: String): Boolean =
        bankInfo.name.contains(queryLowerCase, true)
                || bicMatches(bankInfo, queryLowerCase)
                || bankInfo.city.startsWith(queryLowerCase, true)
                || bankInfo.branchesInOtherCities.any { it.startsWith(queryLowerCase, true) }


    override fun searchBankByBic(bic: String): BankInfo? {
        return getBankList().firstOrNull { bicMatches(it, bic) }
    }

    protected open fun bicMatches(bankInfo: BankInfo, query: String): Boolean {
        val bic = bankInfo.bic
        if (bic == null) {
            return false
        }

        val normalizedQuery = if (query.length == 11 && query.endsWith("XXX", true)) query.substring(0, 8)
                            else query

        return bic.startsWith(normalizedQuery, true)
    }

    override fun findBankByBankCode(bankCode: String): BankInfo? {
        val result = getBankList().asSequence().filter { it.bankCode.startsWith(bankCode) }.max(2)

        return if (result.size > 1) { // non unique result, but should actually never happen for BICs
            null
        } else {
            result.firstOrNull()
        }
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

    private fun Sequence<BankInfo>.max(maxItems: Int? = null): List<BankInfo> =
        this.take(maxItems ?: Int.MAX_VALUE)
            .toList()

}