
package fi.riista.integration.luke_export.mooselikeharvests;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for gameGender.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="gameGender"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
 *     &lt;enumeration value="FEMALE"/&gt;
 *     &lt;enumeration value="MALE"/&gt;
 *     &lt;enumeration value="UNKNOWN"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "gameGender", namespace = "http://riista.fi/integration/luke/export/mooselikeharvests/2016/07")
@XmlEnum
public enum LEM_GameGender {

    FEMALE,
    MALE,
    UNKNOWN;

    public String value() {
        return name();
    }

    public static LEM_GameGender fromValue(String v) {
        return valueOf(v);
    }

}
