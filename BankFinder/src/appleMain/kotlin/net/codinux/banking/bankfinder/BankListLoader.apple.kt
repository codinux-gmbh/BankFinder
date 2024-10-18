package net.codinux.banking.bankfinder

import platform.Foundation.*

actual class BankListLoader actual constructor() {

    actual fun loadBankList(): String {
        val bundle = NSBundle.mainBundle

        val bankListJsonPath = bundle.pathForResource("BankList", "json")!!

        return NSString.stringWithContentsOfFile(bankListJsonPath) as String
    }

}