package fi.riista.integration.mml.service;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import fi.riista.feature.gis.GISPoint;
import fi.riista.integration.mml.dto.MMLRekisteriyksikonTietoja;
import fi.riista.integration.mml.parser.MMLRekisteriyksikonTietojaParser;
import fi.riista.integration.mml.support.MMLWebFeatureServiceRequestTemplate;
import fi.riista.integration.mml.support.WFSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.util.List;

@Service
public class MMLRekisteriyksikonTietojaService {
    private static final Logger LOG = LoggerFactory.getLogger(MMLRekisteriyksikonTietojaService.class);

    public static final String[] KTJ_PROPERTY_NAMES = new String[]{
            "ktjkiiwfs:kiinteistotunnus",
            "ktjkiiwfs:kuntaTieto"
    };

    private final MMLWebFeatureServiceRequestTemplate requestTemplate;

    @Autowired
    public MMLRekisteriyksikonTietojaService(MMLWebFeatureServiceRequestTemplate requestTemplate) {
        this.requestTemplate = requestTemplate;
    }

    @Cacheable(value = "mmlRekisteriyksikonTietojaCode")
    public List<MMLRekisteriyksikonTietoja> findByCode(final String propertyIdentifier) {
        return getQuery(WFSUtil.propertyIsEqual("ktjkiiwfs:kiinteistotunnus", propertyIdentifier));
    }

    @Cacheable(value = "mmlRekisteriyksikonTietojaPosition")
    public List<MMLRekisteriyksikonTietoja> findByPosition(final GISPoint position) {
        return getQuery(WFSUtil.intersects(position,
                "ktjkiiwfs:rekisteriyksikonPalstanTietoja/ktjkiiwfs:RekisteriyksikonPalstanTietoja/ktjkiiwfs:sijainti"));
    }

    private List<MMLRekisteriyksikonTietoja> getQuery(final String filter) {
        final ImmutableMap<String, String> parameters = ImmutableMap.<String,String>builder()
                .put("SERVICE", "WFS")
                .put("REQUEST", "GetFeature")
                .put("VERSION", "1.1.0")
                .put("NAMESPACE", "xmlns(ktjkiiwfs=http://xml.nls.fi/ktjkiiwfs/2010/02)")
                .put("TYPENAME", "ktjkiiwfs:RekisteriyksikonTietoja")
                .put("PROPERTYNAME", Joiner.on(',').join(KTJ_PROPERTY_NAMES))
                .put("SRSNAME", WFSUtil.SRS_NAME)
                .put("FILTER", filter)
                .build();

        Document document = requestTemplate.makeXMLGetRequest(parameters);

        if (LOG.isDebugEnabled()) {
            LOG.debug("XML response: {}", WFSUtil.domToString(document, true, false));
        }

        return MMLRekisteriyksikonTietojaParser.parse(document);
    }
}
