package fi.riista.integration.metsastajarekisteri.common;

import static java.util.stream.Collectors.joining;

import fi.riista.config.Constants;
import fi.riista.integration.metsastajarekisteri.MetsastajaRekisteriImportService;
import fi.riista.integration.metsastajarekisteri.MetsastajaRekisteriPerson;
import fi.riista.util.Functions;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.springframework.batch.item.ItemWriter;

import javax.validation.ConstraintViolationException;
import java.util.List;

public class MetsastajaRekisteriPersonItemWriter implements ItemWriter<MetsastajaRekisteriPerson> {

    private final MetsastajaRekisteriImportService importService;
    private final DateTime importTimestamp;

    public MetsastajaRekisteriPersonItemWriter(MetsastajaRekisteriImportService importService,
                                               long importTimestamp) {
        this.importService = importService;
        this.importTimestamp = new Instant(importTimestamp).toDateTime(Constants.DEFAULT_TIMEZONE);
    }

    @Override
    public void write(final List<? extends MetsastajaRekisteriPerson> items) throws Exception {
        try {
            importService.updatePersons(items, importTimestamp);

        } catch (final ConstraintViolationException cve) {
            final String joinedContraintViolations = cve.getConstraintViolations().stream()
                    .map(Functions.CONSTRAINT_VIOLATION_TO_STRING)
                    .collect(joining("\n"));

            final String msg = String.format("JSR-303 constraint violations:%n%s", joinedContraintViolations);

            throw new ConstraintViolationException(msg, cve.getConstraintViolations());
        }
    }
}
