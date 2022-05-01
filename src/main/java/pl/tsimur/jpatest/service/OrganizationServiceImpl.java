package pl.tsimur.jpatest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tsimur.jpatest.model.Organization;
import pl.tsimur.jpatest.repository.OrganizationRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    public Integer save(Organization org) {
        Organization organization = organizationRepository.save(org);
        log.info("Saved organization with name: {}", organization.getName());
        return organization.getNip();
    }

    @Override
    public Organization find(Integer nip) {
        Organization organization = organizationRepository.findOrganizationByNip(nip);
        try {
            log.info("Found organization with name: {}", organization.getName());
        } catch (NullPointerException ex) {
            ex.getMessage();
        }
        return organization;
    }

    @Override
    public List<Organization> findAllByName(String name) {
        List<Organization> orgWithName = organizationRepository.findAllOrganizationByName(name);
        log.info("Found {} organization with name: {}", orgWithName.size(), name);
        return orgWithName;
    }

    @Override
    public List<Organization> findAllByAddress(String address) {
        List<Organization> orgWithAddresses = organizationRepository.findAllOrganizationByAddress(address);
        log.info("Found {} organizations with {} addresses", orgWithAddresses.size(), address);
        return orgWithAddresses;
    }

    /**
     * Имена компаний, где работают пенсионеры
     */
    @Override
    public List<String> findAllOrgNamesWithPensioners() {
        List<String> orgNames = organizationRepository.findAllOrgNamesWithPensioners();
        log.info("👴🏼 Found {} organizations where pensioners work", orgNames.size());
        return orgNames;
    }

    @Override
    @Transactional
    public void updateAddress(Integer nip, String address) {
        organizationRepository.updateOrganizationByNip(nip, address);
        log.info("Updated organization {} ", address);
    }

    @Override
    @Transactional
    public void delete(Integer nip) {
        organizationRepository.deleteByNip(nip);
        log.info("Deleted organization {} ", nip);
    }

}
