package net.dankito.banking.banklistcreator.prettifier

import net.dankito.banking.bankfinder.BankInfo


open class BankListPrettifier {

    open fun prettify(banks: List<BankInfo>, options: List<BankListPrettifierOption>): List<BankInfo> {
        var prettifiedList = banks

        if (options.contains(BankListPrettifierOption.RemoveInstitutionInternalBank)) {
            prettifiedList = removeInstitutionInternalBank(prettifiedList)
        }

        if (options.contains(BankListPrettifierOption.RemoveBanksWithSameBankCodeAndPostalCode)) {
            prettifiedList = removeBanksWithSameBankCodeAndPostalCode(prettifiedList)
        }

        if (options.contains(BankListPrettifierOption.RemoveBanksWithSameBankCodeAndCity)) {
            prettifiedList = removeBanksWithSameBankCodeAndCity(prettifiedList)
        }

        if (options.contains(BankListPrettifierOption.MergeAllBranchesOfBankIntoOne)) {
            prettifiedList = mergeAllBranchesOfBankIntoOne(prettifiedList)
        }

        if (options.contains(BankListPrettifierOption.MapBankNamesToWellKnownNames)) {
            prettifiedList = mapBankNamesToWellKnownNames(prettifiedList)
        }

        return prettifiedList
    }


    open fun mapBankNamesToWellKnownNames(banks: List<BankInfo>): List<BankInfo> {
        banks.forEach { bank ->
            when {
                bank.name.contains("Postbank") -> bank.name = "Postbank"
                bank.name.startsWith("Deutsche Kreditbank") -> bank.name = "DKB (Deutsche Kreditbank)"
                bank.name.startsWith("Deutsche Bank") || bank.name.startsWith("DB Privat- und Firmenkundenbank") -> bank.name = "Deutsche Bank"
                bank.name.lowercase().contains("comdirect") -> bank.name = "Comdirect"
                bank.name.startsWith("Commerzbank") -> bank.name = "Commerzbank"
                bank.bic.startsWith("CSDB") -> bank.name = "Consors"
            }
        }

        return banks
    }

    open fun removeInstitutionInternalBank(banks: List<BankInfo>): List<BankInfo> {
        return banks.filterNot {
            (it.name.contains("intern", true) && it.name.contains("international", true) == false)
                    || it.name.startsWith("UniCredit Bank - HVB Settlement")
        }
    }


    open fun removeBanksWithSameBankCodeAndPostalCode(banks: List<BankInfo>): List<BankInfo> {
        val groupedByBankCodeAndPostalCode = banks.groupBy { it.bankCode + "_" + it.postalCode }

        val banksToRemove = groupedByBankCodeAndPostalCode.values.flatMap { banksWithSameBankCodeAndPostalCode ->
            if (banksWithSameBankCodeAndPostalCode.size > 1) {
                val bankWithBestName = findBankWithShortestName(banksWithSameBankCodeAndPostalCode)
                val banksWithoutBankWithBestName = banksWithSameBankCodeAndPostalCode.toMutableList()

                banksWithoutBankWithBestName.remove(bankWithBestName)
                return@flatMap banksWithoutBankWithBestName
            }

            listOf<BankInfo>()
        }

        val prettifiedList = banks.toMutableList()
        prettifiedList.removeAll(banksToRemove)

        return prettifiedList
    }

    // TODO: there are many banks like "Volksbank Nordmünsterland -alt-" and "Volksbank Nordmünsterland (Gf P2)" where each time "-alt-" gets selected
    protected open fun findBankWithShortestName(banks: List<BankInfo>): BankInfo {
        var bankWithBestName = banks.first()

        for (i in 1 until banks.size) {
            val bank = banks[i]
            if (bank.name.length < bankWithBestName.name.length) {
                bankWithBestName = bank
            }
        }

        return bankWithBestName
    }


    open fun removeBanksWithSameBankCodeAndCity(banks: List<BankInfo>): List<BankInfo> {
        val groupedByBankCodeAndCity = banks.groupBy { it.bankCode + "_" + it.city }

        val banksToRemove = groupedByBankCodeAndCity.values.flatMap { banksWithSameBankCodeAndCity ->
            if (banksWithSameBankCodeAndCity.size > 1) {
                val banksToRemove = banksWithSameBankCodeAndCity.toMutableList()
                banksToRemove.remove(banksWithSameBankCodeAndCity.first())

                return@flatMap banksToRemove
            }

            listOf<BankInfo>()
        }

        val prettifiedList = banks.toMutableList()
        prettifiedList.removeAll(banksToRemove)

        return prettifiedList
    }

    open fun mergeAllBranchesOfBankIntoOne(banks: List<BankInfo>): List<BankInfo> {
        val groupedByBankCodeAndPinTanAddress = banks.groupBy { it.bankCode + "_" + it.pinTanAddress }

        val banksToRemove = groupedByBankCodeAndPinTanAddress.values.flatMap { banksWithSameBankCodeAndPinTanAddress ->
            if (banksWithSameBankCodeAndPinTanAddress.size > 1) {
                val banksToRemove = banksWithSameBankCodeAndPinTanAddress.toMutableList()
                val mainBranch = findMainBranch(banksWithSameBankCodeAndPinTanAddress)

                banksToRemove.remove(banksWithSameBankCodeAndPinTanAddress.first())

                mainBranch.branchesInOtherCities = banksToRemove.map { it.city }.toSet().toList()

                return@flatMap banksToRemove
            }

            listOf<BankInfo>()
        }

        val prettifiedList = banks.toMutableList()
        prettifiedList.removeAll(banksToRemove)

        return prettifiedList
    }

    private fun findMainBranch(banksWithSameBankCodeAndPinTanAddress: List<BankInfo>): BankInfo {
//        banksWithSameBankCodeAndPinTanAddress.forEach { bank ->
//            if (bank.name.contains(bank.city)) {
//                println("${bank.name}: Picked ${bank.city} from ${banksWithSameBankCodeAndPinTanAddress.map { it.city }}")
//                return bank
//            }
//        }

        // in most cases this turned out to be the best one
        return banksWithSameBankCodeAndPinTanAddress.first()
    }

}