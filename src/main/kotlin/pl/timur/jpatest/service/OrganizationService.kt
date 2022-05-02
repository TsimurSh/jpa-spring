package pl.timur.jpatest.service

import pl.timur.jpatest.model.Organization

interface OrganizationService {
    fun save(org: Organization): Int

    fun find(nip: Int): Organization?

    fun findAllByName(name: String): List<Organization>

    fun findAllByAddress(address: String): List<Organization>

    fun findAllOrgNamesWithPensioners(): List<String>

    fun updateAddress(nip: Int, address: String)

    fun delete(nip: Int)
}