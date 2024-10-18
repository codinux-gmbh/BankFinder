package net.dankito.banking.bankfinder

import kotlinx.serialization.Serializable

//import com.fasterxml.jackson.annotation.JsonIgnore
//import com.fasterxml.jackson.annotation.JsonInclude
//
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Serializable
open class BankInfo(
    open var name: String,
    open val bankCode: String,
    open val bic: String? = null,
    open val postalCode: String,
    open val city: String,
    open val pinTanAddress: String? = null,
    open val pinTanVersion: String? = null,
    open var bankingGroup: BankingGroup? = null,
    open var branchesInOtherCities: List<String> = listOf() // to have only one entry per bank its branches' cities are now stored in branchesInOtherCities so that branches' cities are still searchable
) {

    protected constructor() : this("", "", "", "", "", null, "") // for object deserializers


//    @get:JsonIgnore
    open val supportsPinTan: Boolean
        get() = pinTanAddress.isNullOrEmpty() == false

//    @get:JsonIgnore
    open val supportsFinTs3_0: Boolean
        get() = pinTanVersion == "FinTS V3.0"


    override fun toString(): String {
        return "$bankCode $name $city"
    }

}