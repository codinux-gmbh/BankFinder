package net.dankito.banking.bankfinder.rest

import net.dankito.banking.bankfinder.BankInfo
import net.dankito.banking.bankfinder.InMemoryBankFinder
import org.jboss.resteasy.annotations.jaxrs.PathParam
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType


@Path("/bankfinder")
class BankFinderResource {

    protected var bankFinder = InMemoryBankFinder()


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{query}")
    fun findBank(@PathParam query: String): List<BankInfo> {
        return bankFinder.findBankByNameBankCodeOrCity(query)
    }

}