package net.dankito.banking.bankfinder


abstract class BankFinderBase : IBankFinder {

    protected abstract fun findBankByNameBicOrCityForNonEmptyQuery(query: String, maxItems: Int? = null): List<BankInfo>

    abstract fun searchBankByBic(bic: String): BankInfo?


    protected val cachedBanksByBic = mutableMapOf<String, BankInfo?>() // TODO: use a thread-safe Map

    protected val cachedBanksByBankCode = mutableMapOf<String, BankInfo?>() // TODO: use a thread-safe Map


    override fun findBankByNameBicBankCodeOrCity(query: String?, maxItems: Int?): List<BankInfo> {
        if (query.isNullOrBlank()) {
            return getBankList(maxItems)
        }

        if (query.toIntOrNull() != null) { // if query is an integer, then it can only be an bank code, but not a bank name or city
            return findBankByBankCode(query, maxItems)
        }

        return findBankByNameBicOrCityForNonEmptyQuery(query, maxItems)
    }


    override fun findBankByBic(bic: String): BankInfo? {
        cachedBanksByBic[bic]?.let {
            return it
        }

        val bankForBic = searchBankByBic(bic)

        cachedBanksByBic[bic] = bankForBic

        return bankForBic
    }

    override fun findBankByBicOrIban(bic: String?, iban: String): BankInfo? {
        if (bic == null || iban.length < 9) {
            return null
        }

        return findBankByBic(bic) ?: findBankByIban(iban)
    }

    override fun findBankByIban(iban: String): BankInfo? {
        if (iban.length < 9) {
            return null
        }

        val bankCode = iban.substring(4) // first two letters are the country code, third and fourth char are the checksum, bank code starts at 5th char

        val bankByBankCode = findBankByBankCode(bankCode)

        cachedBanksByBankCode[bankCode] = bankByBankCode

        return bankByBankCode
    }


    protected open fun loadBankList(): List<BankInfo> {
        return BankListDeserializer().loadBankList()
    }

}