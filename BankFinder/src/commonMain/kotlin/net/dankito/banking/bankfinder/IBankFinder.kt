package net.dankito.banking.bankfinder


interface IBankFinder {

    fun getBankList(maxItems: Int? = null): List<BankInfo>

    fun findBankByBankCode(query: String, maxItems: Int? = null): List<BankInfo>

    fun findBankByNameBankCodeOrCity(query: String?, maxItems: Int? = null): List<BankInfo>

    fun findBankByBic(bic: String): BankInfo?

    fun preloadBankList()

}