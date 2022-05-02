package pl.timur.jpatest.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pl.timur.jpatest.model.Organization

@Repository
interface OrganizationRepository : JpaRepository<Organization, Int> {
    fun findOrganizationByNip(nip: Int): Organization?
    fun findAllOrganizationByName(name: String): List<Organization>
    fun findAllOrganizationByAddress(address: String): List<Organization>

    @Query(
        value = "SELECT org.name " +
                " FROM organization org INNER JOIN user_account u ON  org.nip = u.employer_nip WHERE u.age > 50",
        nativeQuery = true
    )
    fun findAllOrgNamesWithPensioners(): List<String>

    @Modifying
    @Query(value = "UPDATE Organization SET address = :name WHERE nip = :nip")
    fun updateOrganizationByNip(nip: Int, name: String)

    @Modifying
    fun deleteByNip(nip: Int)
}