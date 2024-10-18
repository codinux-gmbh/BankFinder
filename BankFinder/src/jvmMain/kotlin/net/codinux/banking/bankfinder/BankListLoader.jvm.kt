package net.codinux.banking.bankfinder

actual class BankListLoader actual constructor() {

    companion object {
        const val BankListFileName = "BankList.json"
    }


    actual fun loadBankList(): String {
        val inputStream = BankListLoader::class.java.classLoader.getResourceAsStream(BankListFileName)!!

        return inputStream.bufferedReader().readText()
    }

}