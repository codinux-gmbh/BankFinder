package net.codinux.banking.bankfinder


open class DetailedBankInfo(
    name: String,
    bankCode: String,
    bic: String,
    postalCode: String,
    city: String,
    pinTanAddress: String?,
    pinTanVersion: String?,
    open val checksumMethod: String,
    open val oldBankCode: String?,
    bankingGroup: BankingGroup? = null
) : BankInfo(name, bankCode, bic, postalCode, city, pinTanAddress, pinTanVersion, bankingGroup) {

    protected constructor() : this("", "", "", "", "", null, "", "", null, null) // for object deserializers


    open val isBankCodeDeleted: Boolean
        get() = oldBankCode != null // TODO: this is not in all cases true, there are banks with new bank code which haven't been deleted

}