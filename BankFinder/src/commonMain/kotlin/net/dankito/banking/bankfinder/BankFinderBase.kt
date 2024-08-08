package net.dankito.banking.bankfinder


abstract class BankFinderBase : IBankFinder {

    protected abstract fun findBankByNameBankCodeOrCityForNonEmptyQuery(query: String,maxItems: Int? = null): List<BankInfo>

    abstract fun searchBankByBic(bic: String): BankInfo?


    protected val cachedBanksByBic = mutableMapOf<String, BankInfo?>()


    override fun findBankByNameBankCodeOrCity(query: String?, maxItems: Int?): List<BankInfo> {
        if (query.isNullOrBlank()) {
            return getBankList(maxItems)
        }

        if (query.toIntOrNull() != null) { // if query is an integer, then it can only be an bank code, but not a bank name or city
            return findBankByBankCode(query, maxItems)
        }

        return findBankByNameBankCodeOrCityForNonEmptyQuery(query, maxItems)
    }


    override fun findBankByBic(bic: String): BankInfo? {
        cachedBanksByBic[bic]?.let {
            return it
        }

        val bankForBic = searchBankByBic(bic)

        cachedBanksByBic[bic] = bankForBic

        return bankForBic
    }


    protected open fun loadBankList(): List<BankInfo> {
        return BankListDeserializer().loadBankList()
    }

}