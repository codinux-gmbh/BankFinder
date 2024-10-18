package net.codinux.banking.banklistcreator.prettifier

import net.codinux.banking.bankfinder.BankingGroup

class BankingGroupMapper {

    fun getBankingGroup(name: String, bic: String): BankingGroup? {
        val lowercase = name.lowercase()

        return when {
            name.contains("Sparda") -> BankingGroup.Sparda
            name.contains("PSD") -> BankingGroup.PSD
            name.contains("GLS") -> BankingGroup.GLS
            // see https://de.wikipedia.org/wiki/Liste_der_Genossenschaftsbanken_in_Deutschland
            name.contains("BBBank") || name.contains("Evangelische Bank") || name.contains("LIGA Bank")
                    || name.contains("Pax") || name.contains("Bank für Kirche und Diakonie") || name.contains("Bank im Bistum Essen")
                    || name.contains("Bank für Schiffahrt") || name.contains("Bank für Kirche")
                -> BankingGroup.SonstigeGenossenschaftsbank
            lowercase.contains("deutsche kreditbank") -> BankingGroup.DKB
            // may check against https://de.wikipedia.org/wiki/Liste_der_Sparkassen_in_Deutschland
            lowercase.contains("sparkasse") -> BankingGroup.Sparkasse
            lowercase.contains("comdirect") -> BankingGroup.Comdirect
            lowercase.contains("commerzbank") -> BankingGroup.Commerzbank
            lowercase.contains("targo") -> BankingGroup.Targobank
            lowercase.contains("santander") -> BankingGroup.Santander
            name.contains("KfW") -> BankingGroup.KfW
            name.contains("N26") -> BankingGroup.N26
            else -> getBankingGroupByBic(bic)
        }
    }

    private fun getBankingGroupByBic(bic: String): BankingGroup? {
        if (bic.length < 4) {
            return null
        }

        if (bic.startsWith("CMCIDEDD")) {
            return BankingGroup.Targobank
        }

        val bankCodeOfBic = bic.substring(0, 4)

        return when (bankCodeOfBic) {
            "GENO", "VBMH", "VOHA", "VBRS", "DBPB", "VBGT", "FFVB", "WIBA", "VRBU", "MVBM", "VOBA", "ULMV", "VBRT", "VBRA", "VBPF", "VOLO" -> BankingGroup.VolksUndRaiffeisenbanken
            "BFSW", // Bank fuer Sozialwirtschaft
                "BEVO", // Berliner Volksbank
                "DAAE", // apoBank
                "MHYP", // Münchener Hypothekenbank
                "DZBM", // DZB Bank
                "EDEK" // Edekabank
                -> BankingGroup.SonstigeGenossenschaftsbank
            "BYLA", "SOLA", "NOLA", "WELA", "HELA", "MALA", "BRLA", "NASS", "TRIS", "OSDD", "ESSL", "GOPS", "SBCR", "BRUS" -> BankingGroup.Sparkasse // filter out DBK, (Bayr.) Landesbank, ...
            "OLBO" -> BankingGroup.OldenburgischeLandesbank
            "DEUT" -> BankingGroup.DeutscheBank
            "PBNK" -> BankingGroup.Postbank
            "COBA", "DRES" -> BankingGroup.Commerzbank // COBA could also be comdirect, but we cannot differentiate this at this level, this has to do getBankingGroup()
            "HYVE" -> BankingGroup.Unicredit
            "INGB" -> BankingGroup.ING
            "SCFB" -> BankingGroup.Santander
            "NORS" -> BankingGroup.Norisbank
            "DEGU" -> BankingGroup.Degussa
            "OBKL" -> BankingGroup.Oberbank
            "MARK" -> BankingGroup.Bundesbank
            "KFWI", "DTAB" -> BankingGroup.KfW
            "NTSB" -> BankingGroup.N26
            "CSDB" -> BankingGroup.Consors
            else -> null
        }
    }

}