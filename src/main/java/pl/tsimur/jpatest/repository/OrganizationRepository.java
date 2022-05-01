package pl.tsimur.jpatest.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.tsimur.jpatest.model.Organization;

import java.util.List;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Integer> {

    Organization findOrganizationByNip(Integer nip);

    List<Organization> findAllOrganizationByName(String name);

    List<Organization> findAllOrganizationByAddress(String address);

    @Query(value = "SELECT org.name " +
            " FROM organization org INNER JOIN user_account u ON  org.nip = u.employer_nip WHERE u.age > 50", nativeQuery = true)
    List<String> findAllOrgNamesWithPensioners();

    @Modifying
    @Query(value = "UPDATE Organization SET address = :name WHERE nip = :nip")
    void updateOrganizationByNip(Integer nip, String name);

    @Modifying
    void deleteByNip(Integer nip);
}
