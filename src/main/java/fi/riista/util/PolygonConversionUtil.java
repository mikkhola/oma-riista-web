package fi.riista.util;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import org.geojson.GeoJsonObject;
import org.geojson.Geometry;
import org.geojson.GeometryCollection;
import org.geojson.LngLatAlt;
import org.geojson.MultiPolygon;
import org.geojson.Polygon;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

// This class contains boiler-plate to transform GeoJSON and Hibernate Polygon classes
public class PolygonConversionUtil {

    public static com.vividsolutions.jts.geom.Geometry geoJsonToJava(
            @Nonnull final GeoJsonObject geoJson, @Nonnull final GISUtils.SRID srid) {

        Objects.requireNonNull(geoJson, "geoJson is null");
        Objects.requireNonNull(srid, "srid is null");

        if (geoJson instanceof Geometry) {
            final Geometry<?> geometry = Geometry.class.cast(geoJson);

            if (geometry instanceof Polygon) {
                return geoJsonPolygonToJava(Polygon.class.cast(geometry), srid);
            }
            if (geometry instanceof MultiPolygon) {
                return geoJsonMultiPolygonToJava(MultiPolygon.class.cast(geometry), srid);
            }

        } else if (geoJson instanceof GeometryCollection) {
            return geoJsonGeometryCollectionToJava(GeometryCollection.class.cast(geoJson), srid);
        }

        throw new IllegalArgumentException("Geometry type is not supported: " + geoJson.getClass().getSimpleName());
    }

    public static GeoJsonObject javaToGeoJSON(@Nonnull final com.vividsolutions.jts.geom.Geometry geometry) {
        Objects.requireNonNull(geometry);

        if (geometry instanceof com.vividsolutions.jts.geom.MultiPolygon) {
            return javaMultiPolygonToGeoJson(com.vividsolutions.jts.geom.MultiPolygon.class.cast(geometry));
        } else if (geometry instanceof com.vividsolutions.jts.geom.Polygon) {
            return javaPolygonToGeoJson(com.vividsolutions.jts.geom.Polygon.class.cast(geometry));
        } else if (geometry instanceof com.vividsolutions.jts.geom.GeometryCollection) {
            return javaGeometryCollectionToGeoJson(com.vividsolutions.jts.geom.GeometryCollection.class.cast(geometry));
        } else if (geometry instanceof com.vividsolutions.jts.geom.LineString) {
            // TODO: Not required right now...
            return null;
        }

        throw new IllegalArgumentException("Geometry type is not supported: " + geometry.getGeometryType());
    }

    // GeoJSON: Polygon
    private static com.vividsolutions.jts.geom.Polygon geoJsonPolygonToJava(
            final Polygon jsonPolygon, final GISUtils.SRID srid) {

        final LinearRing linearRing = coordsToLinearRing(jsonPolygon.getExteriorRing(), srid);

        final LinearRing[] holes = jsonPolygon.getInteriorRings().stream()
                .map(lngLatAltList -> PolygonConversionUtil.coordsToLinearRing(lngLatAltList, srid))
                .toArray(LinearRing[]::new);

        return GISUtils.getGeometryFactory(srid).createPolygon(linearRing, holes);
    }

    private static LinearRing coordsToLinearRing(final List<LngLatAlt> exteriorRing, final GISUtils.SRID srid) {
        if (exteriorRing.isEmpty()) {
            throw new IllegalArgumentException("Empty ring");
        }

        final Stream<Coordinate> stream = exteriorRing.stream()
                .map(lngLatAlt -> new Coordinate(lngLatAlt.getLongitude(), lngLatAlt.getLatitude()));

        final LngLatAlt first = exteriorRing.get(0);
        final LngLatAlt last = exteriorRing.get(exteriorRing.size() - 1);

        final Coordinate[] coords;

        if (first.equals(last)) {
            coords = stream.toArray(Coordinate[]::new);
        } else {
            // Complete partial ring
            final Coordinate firstCoordinate = new Coordinate(first.getLongitude(), first.getLatitude());
            coords = Stream.concat(stream, Stream.of(firstCoordinate)).toArray(Coordinate[]::new);
        }

        return GISUtils.getGeometryFactory(srid).createLinearRing(coords);
    }

    // GeoJSON: MultiPolygon
    private static com.vividsolutions.jts.geom.Geometry geoJsonMultiPolygonToJava(
            final MultiPolygon multiPolygon, final GISUtils.SRID srid) {

        final com.vividsolutions.jts.geom.Polygon[] polygons = multiPolygon.getCoordinates().stream()
                .map(coordinate -> PolygonConversionUtil.coordsToJsonPolygon(coordinate, srid))
                .toArray(com.vividsolutions.jts.geom.Polygon[]::new);

        return polygons.length == 0
                ? null
                : new com.vividsolutions.jts.geom.MultiPolygon(polygons, GISUtils.getGeometryFactory(srid));
    }

    private static com.vividsolutions.jts.geom.Polygon coordsToJsonPolygon(
            final List<List<LngLatAlt>> polygonCoords, final GISUtils.SRID srid) {

        final Iterator<List<LngLatAlt>> iterator = polygonCoords.iterator();
        final Polygon jsonPolygon = new Polygon(iterator.next());

        while (iterator.hasNext()) {
            jsonPolygon.addInteriorRing(iterator.next());
        }

        return geoJsonPolygonToJava(jsonPolygon, srid);
    }

    // GeoJSON: GeometryCollection
    private static com.vividsolutions.jts.geom.Geometry geoJsonGeometryCollectionToJava(
            final GeometryCollection geometryCollection, final GISUtils.SRID srid) {

        final com.vividsolutions.jts.geom.Geometry[] geometries = geometryCollection.getGeometries().stream()
                .map(geom -> PolygonConversionUtil.geoJsonToJava(geom, srid))
                .toArray(com.vividsolutions.jts.geom.Geometry[]::new);

        return geometries.length == 0
                ? null
                : new com.vividsolutions.jts.geom.GeometryCollection(geometries, GISUtils.getGeometryFactory(srid));
    }

    // Java: Ring
    private static List<LngLatAlt> javaRingToGeoJson(final LineString ring) {
        return Stream.of(ring.getCoordinates())
                .map(c -> new LngLatAlt(c.getOrdinate(Coordinate.X), c.getOrdinate(Coordinate.Y)))
                .collect(toList());
    }

    // Java: Polygon
    private static Polygon javaPolygonToGeoJson(final com.vividsolutions.jts.geom.Polygon geometry) {
        final Polygon polygon = new Polygon(javaRingToGeoJson(geometry.getExteriorRing()));

        for (int i = 0; i < geometry.getNumInteriorRing(); i++) {
            polygon.addInteriorRing(javaRingToGeoJson(geometry.getInteriorRingN(i)));
        }

        return polygon;
    }

    // Java: MultiPolygon
    private static MultiPolygon javaMultiPolygonToGeoJson(final com.vividsolutions.jts.geom.MultiPolygon geometry) {
        final MultiPolygon multiPolygon = new MultiPolygon();

        for (int i = 0; i < geometry.getNumGeometries(); i++) {
            final com.vividsolutions.jts.geom.Geometry subGeometry = geometry.getGeometryN(i);

            if (subGeometry instanceof com.vividsolutions.jts.geom.Polygon) {
                multiPolygon.add(javaPolygonToGeoJson(com.vividsolutions.jts.geom.Polygon.class.cast(subGeometry)));
            }
        }

        return multiPolygon;
    }

    // Java: GeometryCollection
    private static GeometryCollection javaGeometryCollectionToGeoJson(
            final @Nonnull com.vividsolutions.jts.geom.Geometry geometry) {

        final GeometryCollection geometryCollection = new GeometryCollection();

        for (int i = 0; i < geometry.getNumGeometries(); i++) {
            Optional.ofNullable(javaToGeoJSON(geometry.getGeometryN(i))).ifPresent(geometryCollection::add);
        }

        return geometryCollection;
    }

    private PolygonConversionUtil() {
        throw new AssertionError();
    }
}