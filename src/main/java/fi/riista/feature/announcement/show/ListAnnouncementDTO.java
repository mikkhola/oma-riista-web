package fi.riista.feature.announcement.show;

import fi.riista.feature.account.user.SystemUser;
import fi.riista.feature.announcement.Announcement;
import fi.riista.feature.announcement.AnnouncementSenderType;
import fi.riista.feature.announcement.AnnouncementSubscriber;
import fi.riista.feature.common.entity.BaseEntityDTO;
import fi.riista.feature.organization.occupation.OccupationType;
import fi.riista.feature.organization.Organisation;
import fi.riista.feature.organization.OrganisationType;
import fi.riista.util.DateUtil;
import fi.riista.util.DtoUtil;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ListAnnouncementDTO extends BaseEntityDTO<Long> {
    public static class OrganisationDTO {
        public static OrganisationDTO create(final Organisation organisation) {
            final OrganisationDTO dto = new OrganisationDTO();
            dto.setOrganisationType(organisation.getOrganisationType());
            dto.setOfficialCode(organisation.getOfficialCode());
            dto.setName(organisation.getNameLocalisation().asMap());
            return dto;
        }

        public static OrganisationDTO create(final AnnouncementSubscriber subscriber) {
            return create(subscriber.getOrganisation());
        }

        private OrganisationType organisationType;
        private String officialCode;
        private Map<String, String> name;

        public OrganisationType getOrganisationType() {
            return organisationType;
        }

        public void setOrganisationType(final OrganisationType organisationType) {
            this.organisationType = organisationType;
        }

        public String getOfficialCode() {
            return officialCode;
        }

        public void setOfficialCode(final String officialCode) {
            this.officialCode = officialCode;
        }

        public Map<String, String> getName() {
            return name;
        }

        public void setName(final Map<String, String> name) {
            this.name = name;
        }
    }

    public static ListAnnouncementDTO create(final Announcement announcement,
                                             final List<AnnouncementSubscriber> subscribers,
                                             final Organisation fromOrganisation,
                                             final SystemUser fromUser) {
        Objects.requireNonNull(announcement, "announcement must not be null");

        final ListAnnouncementDTO dto = new ListAnnouncementDTO();

        DtoUtil.copyBaseFields(announcement, dto);

        dto.setDate(DateUtil.toLocalDateNullSafe(announcement.getLifecycleFields().getCreationTime()));
        dto.setBody(announcement.getBody());
        dto.setSubject(announcement.getSubject());
        dto.setSenderType(announcement.getSenderType());

        if (fromOrganisation != null) {
            dto.setFromOrganisation(OrganisationDTO.create(fromOrganisation));
        }

        if (fromUser != null) {
            dto.setFrom(fromUser.getFullName());
        }

        if (subscribers != null) {
            dto.setOccupationTypes(subscribers.stream()
                    .map(AnnouncementSubscriber::getOccupationType)
                    .distinct()
                    .collect(toSet()));

            dto.setSubscriberOrganisations(subscribers.stream()
                    .map(AnnouncementSubscriber::getOrganisation)
                    .distinct()
                    .map(OrganisationDTO::create)
                    .collect(toSet()));
        }

        return dto;
    }

    private Long id;
    private Integer rev;
    private OrganisationDTO fromOrganisation;
    private AnnouncementSenderType senderType;
    private LocalDate date;
    private String from;
    private String subject;
    private String body;
    private Set<OccupationType> occupationTypes;
    private Set<OrganisationDTO> subscriberOrganisations;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Integer getRev() {
        return rev;
    }

    @Override
    public void setRev(final Integer rev) {
        this.rev = rev;
    }


    public OrganisationDTO getFromOrganisation() {
        return fromOrganisation;
    }

    public void setFromOrganisation(final OrganisationDTO fromOrganisation) {
        this.fromOrganisation = fromOrganisation;
    }

    public AnnouncementSenderType getSenderType() {
        return senderType;
    }

    public void setSenderType(final AnnouncementSenderType senderType) {
        this.senderType = senderType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public Set<OccupationType> getOccupationTypes() {
        return occupationTypes;
    }

    public void setOccupationTypes(final Set<OccupationType> occupationTypes) {
        this.occupationTypes = occupationTypes;
    }

    public Set<OrganisationDTO> getSubscriberOrganisations() {
        return subscriberOrganisations;
    }

    public void setSubscriberOrganisations(final Set<OrganisationDTO> subscriberOrganisations) {
        this.subscriberOrganisations = subscriberOrganisations;
    }
}
