package net.dankito.banking.bankfinder

actual class BankListLoader actual constructor() {

    actual fun loadBankList(): String {
        val bundle = NSBundle.mainBundle

        val bankListJsonPath = bundle.pathForResource("BankList", "json")!!

        return NSData.dataWithContentsOfFile(bankListJsonPath, NSDataReadingMappedIfSafe, null)!!
    }

}