package pl.tsimur.jpatest.service;

import pl.tsimur.jpatest.model.Organization;

import java.util.List;

public interface OrganizationService {

    Integer save(Organization org);

    Organization find(Integer nip);

    List<Organization> findAllByName(String name);

    List<Organization> findAllByAddress(String address);

    List<String> findAllOrgNamesWithPensioners();

    void updateAddress(Integer nip, String address);

    void delete(Integer nip);
}
