package pl.timur.jpatest.repository

import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional
import pl.timur.jpatest.model.Organization
import pl.timur.jpatest.model.User

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrganizationRepositoryTest(@Autowired private val repositoryOrg: OrganizationRepository) {

    companion object {
        private val user1 = User(id = -1)

        val org1 = Organization(
            nip = 5674523,
            address = "ul.Slusara, Poznan",
            name = "Pull&Bear"
        )
    }


    @BeforeAll
    fun init() {
        org1.owner = user1
    }

    @Test
    fun createTest() {
        // 1 - C: Create
        val organization = repositoryOrg.save(org1)

        // 2 - R: Read
        Assertions.assertTrue(
            repositoryOrg.findById(org1.nip) // Возвращает Optional<Organization> - поэтому нужно распаковать .get()
                .isPresent
        )
        repositoryOrg.deleteById(org1.nip)
    }

    @Test
    @Transactional
    fun CRUDTest() {
        // 1 - C: Create
        val organization = repositoryOrg.save(org1)
        println("🤖 Сохранил организацию: " + organization.name)
        val id = organization.nip
        Assertions.assertNotNull(id)

        // 2 - R: Read
        println("😺 Проверяю что организация появилась в Базе Данных...")
        Assertions.assertNotNull(
            repositoryOrg.findOrganizationByNip(id) // Возвращает Optional<Organization> - поэтому нужно распаковать .get()
        )
        //
        println("😹 Обновляем организации поле Name...")
        repositoryOrg.updateOrganizationByNip(organization.nip, "Stars")
        //            assertEquals("Stars", organization.getName());

        // 4 - D: Delete
        println("😹 Удаляю организацию из базе данных...")
        repositoryOrg.deleteByNip(id)

        // И еще раз перепроверю
        println("😼 Проверяю что организации не должно быть после удаления из базе данных...")
        Assertions.assertNull(
            repositoryOrg.findOrganizationByNip(id)
        )
    }

    @Test
    fun findOrganizationByNameTest() {
        val orgName = "Mercedes"
        val organizationList = repositoryOrg.findAllOrganizationByName(orgName)
        Assertions.assertEquals(orgName, organizationList[0].name)
    }

    @Test
    fun findOrganizationByAddressTest() {
        val address = "ul.Pobeda, Munich"
        val organizationList = repositoryOrg.findAllOrganizationByAddress(address)
        Assertions.assertEquals(address, organizationList[0].address)
    }

    @Test
    @Disabled
    fun updateOrganizationByOwnerIdTest() {
        val nip = org1.nip
        val newName = "Oracle"
        val organization = repositoryOrg.save(org1)
        val oldName = organization.name
        Assertions.assertEquals(oldName, organization.name)
        repositoryOrg.updateOrganizationByNip(nip, newName)
        val organizationWithUpdatedName = repositoryOrg.findById(nip)
            .orElseThrow { RuntimeException("Not found organization with nip: $nip") }
        Assertions.assertEquals(newName, organizationWithUpdatedName.name)
        repositoryOrg.deleteByNip(nip)
    }

}