package net.codinux.banking.bankfinder


interface IBankFinder {

    fun getBankList(maxItems: Int? = null): List<BankInfo>

    fun findBankByBankCode(query: String, maxItems: Int? = null): List<BankInfo>

    fun findBankByNameBicBankCodeOrCity(query: String?, maxItems: Int? = null): List<BankInfo>

    fun findBankByBankCode(bankCode: String): BankInfo?

    fun findBankByBic(bic: String): BankInfo?

    /**
     * Extracts the bank code (Bankleitzahl) from IBAN and tries to find unique bank that starts with this bank code.
     */
    fun findBankByIban(iban: String): BankInfo?

    /**
     * First tries to find bank by BIC with [findBankByBic] and, if no result has been found, by IBAN with [findBankByIban].
     */
    fun findBankByBicOrIban(bic: String?, iban: String): BankInfo?

    fun preloadBankList()

}