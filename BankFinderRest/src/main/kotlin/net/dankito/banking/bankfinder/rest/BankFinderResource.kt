package net.dankito.banking.bankfinder.rest

import io.quarkus.runtime.annotations.RegisterForReflection
import net.dankito.banking.bankfinder.BankInfo
import net.dankito.banking.bankfinder.InMemoryBankFinder
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType


@Path("/bankfinder")
@RegisterForReflection(targets = [ BankInfo::class ])
class BankFinderResource {

    protected var bankFinder = InMemoryBankFinder()


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun findBank(@QueryParam("query") query: String? = null, @QueryParam("maxItems") maxItems: Int? = null): List<BankInfo> =
        bankFinder.findBankByNameBankCodeOrCity(query, maxItems)

}