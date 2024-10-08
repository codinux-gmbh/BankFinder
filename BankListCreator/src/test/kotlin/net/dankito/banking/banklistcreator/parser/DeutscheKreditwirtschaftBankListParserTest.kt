package net.dankito.banking.banklistcreator.parser

import net.dankito.banking.banklistcreator.TestConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test


@Ignore // not an automatic test, set your path to your bank list file in TestConfig.DeutscheKreditwirtschaftBankListXlsxFile
class DeutscheKreditwirtschaftBankListParserTest {

    private val underTest = DeutscheKreditwirtschaftBankListParser()


    @Test
    fun parse() {

        // when
        // TODO: set path to bank list file from Deutsche Kreditwirtschaft in TestConfig.DeutscheKreditwirtschaftBankListXlsxFile
        val result = underTest.parse(TestConfig.DeutscheKreditwirtschaftBankListXlsxFile)

        // then
        assertThat(result).hasSize(16287)

        result.forEach { bankInfo ->
            assertThat(bankInfo.name).isNotEmpty()
            assertThat(bankInfo.bankCode).isNotEmpty()
//            assertThat(bankInfo.bic).isNotEmpty() // TODO: is there a way to find BICs for all banks?
            assertThat(bankInfo.postalCode).isNotEmpty()
            assertThat(bankInfo.city).isNotEmpty()
            assertThat(bankInfo.checksumMethod).isNotEmpty()
        }
    }

}