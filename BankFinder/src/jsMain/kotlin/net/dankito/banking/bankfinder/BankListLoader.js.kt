package net.dankito.banking.bankfinder

actual class BankListLoader actual constructor() {

    actual fun loadBankList(): String =
        // TODO: may use Compose Resources to also support JS (and Linux and Windows)
        throw NotImplementedError("Loading BankList with JavaScript is not implemented yet")

}