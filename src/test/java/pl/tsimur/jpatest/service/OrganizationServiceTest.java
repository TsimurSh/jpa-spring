package pl.tsimur.jpatest.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.timur.jpatest.model.Organization;
import pl.timur.jpatest.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrganizationServiceTest {
    private static final User USER = new User(-3);
    private static final Organization ORGANIZATION = Organization.builder()
            .nip(5677889)
            .address("ul.Cherwiara, Minsk")
            .owner(USER)
            .name("H&M")
            .build();
    @Autowired
    private OrganizationService organizationService;

    @Test
    @Transactional
    void createTest() {
//         1 - C: Create
        Integer nip;
        nip = organizationService.save(ORGANIZATION);
//         2 - R: Read
        assertNotNull(
                organizationService.find(nip));
//         3 - D: Delete
        organizationService.delete(nip);
        assertNull(
                organizationService.find(nip));
    }

    @Test
    void findByNipTest() {
        Organization organization = organizationService.find(1234567);
        assertNotNull(organization.getName());
    }

    @Test
    void findAllPensionersNamesTest() {
        assertNotNull(
                organizationService.findAllOrgNamesWithPensioners()
        );
    }

    @Test
    void findByAddressTest() {
        Organization organization = organizationService.find(1234567);
        String address = organization.getAddress();
        List<Organization> organizationList = organizationService.findAllByAddress(address);
        assertEquals(address, organizationList.get(0).getAddress());
    }

    @Test
    void findByNameTest() {
        Organization organization = organizationService.find(1234567);
        String name = organization.getName();
        List<Organization> organizationList = organizationService.findAllByName(name);
        assertEquals(name, organizationList.get(0).getName());
    }

    @Test
    void updateAddressTest() {
        Integer nip = 1234567;
        Organization organization = organizationService.find(nip);
        assertNotNull(organization);
        String oldAddress = organization.getAddress();
        String newAddress = "Berlin";
        organizationService.updateAddress(organization.getNip(), newAddress);
        Organization updatedOrganization = organizationService.find(nip);
        System.out.println(oldAddress);
        assertNotEquals(oldAddress, updatedOrganization.getAddress());
        organizationService.updateAddress(nip, oldAddress);
        Organization rollback = organizationService.find(nip);
        assertEquals(oldAddress, rollback.getAddress());
    }

}
