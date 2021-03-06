package fi.riista.feature.pub.occupation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fi.riista.feature.organization.address.AddressDTO;
import fi.riista.feature.organization.OrganisationType;

import java.util.List;

public class PublicOrganisationDTO {

    private long id;

    private String name;

    private OrganisationType organisationType;

    private String officialCode;

    @JsonInclude(Include.NON_NULL)
    private String rhyNumberString;

    @JsonInclude(Include.NON_NULL)
    private AddressDTO address;

    @JsonInclude(Include.NON_NULL)
    private String phoneNumber;

    @JsonInclude(Include.NON_NULL)
    private String email;

    @JsonInclude(Include.NON_NULL)
    private List<PublicOrganisationDTO> subOrganisations;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrganisationType getOrganisationType() {
        return organisationType;
    }

    public void setOrganisationType(OrganisationType organisationType) {
        this.organisationType = organisationType;
    }

    public String getOfficialCode() {
        return officialCode;
    }

    public void setOfficialCode(String officialCode) {
        this.officialCode = officialCode;
    }

    public String getRhyNumberString() {
        return rhyNumberString;
    }

    public void setRhyNumberString(String rhyNumber) {
        this.rhyNumberString = rhyNumber;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PublicOrganisationDTO> getSubOrganisations() {
        return subOrganisations;
    }

    public void setSubOrganisations(List<PublicOrganisationDTO> subOrganisations) {
        this.subOrganisations = subOrganisations;
    }
}
