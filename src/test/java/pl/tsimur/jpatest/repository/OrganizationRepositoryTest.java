package pl.tsimur.jpatest.repository;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import pl.timur.jpatest.model.Organization;
import pl.timur.jpatest.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrganizationRepositoryTest {
    @Autowired
    private OrganizationRepository repositoryOrg;

    @Test
    void createTest() {
        // 1 - C: Create
        Organization organization = repositoryOrg.save(TestData.org1);

        // 2 - R: Read
        assertTrue(
                repositoryOrg.findById(TestData.org1.getNip()) // Возвращает Optional<Organization> - поэтому нужно распаковать .get()
                        .isPresent());
        repositoryOrg.deleteById(TestData.org1.getNip());
    }

    @Test
    @Transactional
    void CRUDTest() {
        // 1 - C: Create
        Organization organization = repositoryOrg.save(TestData.org1);
        System.out.println("🤖 Сохранил организацию: " + organization.getName());
        Integer id = organization.getNip();
        assertNotNull(id);

        // 2 - R: Read
        System.out.println("😺 Проверяю что организация появилась в Базе Данных...");
        assertNotNull(
                repositoryOrg.findOrganizationByNip(id) // Возвращает Optional<Organization> - поэтому нужно распаковать .get()
        );
//
        System.out.println("😹 Обновляем организации поле Name...");
        repositoryOrg.updateOrganizationByNip(organization.getNip(), "Stars");
//            assertEquals("Stars", organization.getName());

        // 4 - D: Delete
        System.out.println("😹 Удаляю организацию из базе данных...");
        repositoryOrg.deleteByNip(id);

        // И еще раз перепроверю
        System.out.println("😼 Проверяю что организации не должно быть после удаления из базе данных...");
        assertNull(
                repositoryOrg.findOrganizationByNip(id)
        );
    }

    @Test
    void findOrganizationByNameTest() {
        var orgName = "Mercedes";
        List<Organization> organizationList = repositoryOrg.findAllOrganizationByName(orgName);
        assertEquals(orgName, organizationList.get(0).getName());
    }

    @Test
    void findOrganizationByAddressTest() {
        String address = "ul.Pobeda, Munich";
        List<Organization> organizationList = repositoryOrg.findAllOrganizationByAddress(address);
        assertEquals(address, organizationList.get(0).getAddress());
    }

    @Test
    @Disabled
    void updateOrganizationByOwnerIdTest() {
        Integer nip = TestData.org1.getNip();
        String newName = "Oracle";

        Organization organization = repositoryOrg.save(TestData.org1);

        String oldName = organization.getName();

        assertEquals(oldName, organization.getName());

        repositoryOrg.updateOrganizationByNip(nip, newName);
        Organization organizationWithUpdatedName = repositoryOrg.findById(nip)
                .orElseThrow(() -> new RuntimeException("Not found organization with nip: " + nip));

        assertEquals(newName, organizationWithUpdatedName.getName());
        repositoryOrg.deleteByNip(nip);
    }

    static class TestData {
        static final User user1 = User.builder().id(-1).build();

        static final Organization org1 = Organization.builder()
                .nip(5674523)
                .address("ul.Slusara, Poznan")
                .name("Pull&Bear")
                .owner(user1)
                .build();
    }
}
