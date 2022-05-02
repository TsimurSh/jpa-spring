package pl.timur.jpatest.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.timur.jpatest.model.Organization
import pl.timur.jpatest.repository.OrganizationRepository

@Service
class OrganizationServiceImpl(
    private val organizationRepository: OrganizationRepository
) : OrganizationService {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }


    override fun save(org: Organization): Int {
        val organization = organizationRepository.save(org)
        log.info("Saved organization with name: ${organization.name}")
        return organization.nip
    }

    override fun find(nip: Int): Organization? {
        val organization = organizationRepository.findOrganizationByNip(nip)
        log.info("Saved organization with nip:{}", nip)
        return organization
    }

    override fun findAllByName(name: String): List<Organization> {
        val orgWithName = organizationRepository.findAllOrganizationByName(name)
        log.info("Found ${orgWithName.size} organization with name: $name")
        return orgWithName
    }

    override fun findAllByAddress(address: String): List<Organization> {
        val orgWithAddresses = organizationRepository.findAllOrganizationByAddress(address)
        log.info("Found ${orgWithAddresses.size} organizations with $address addresses")
        return orgWithAddresses
    }

    override fun findAllOrgNamesWithPensioners(): List<String> {
        val orgNames = organizationRepository.findAllOrgNamesWithPensioners()
        log.info("👴🏼 Found ${orgNames.size} organizations where pensioners work")
        return orgNames
    }

    @Transactional
    override fun updateAddress(nip: Int, address: String) {
        organizationRepository.updateOrganizationByNip(nip, address)
        log.info("Updated organization $address")
    }

    @Transactional
    override fun delete(nip: Int) {
        organizationRepository.deleteByNip(nip)
        log.info("Deleted organization $nip")
    }
}