package ro.tuiasi.pp.lab04.crawler

import org.jsoup.Jsoup
import org.jsoup.parser.Parser

/**
 * Parser pentru conținut XML.
 *
 * Parsează un șir XML simplu cu elemente de nivel 1 sub rădăcină
 * și returnează o mapă cu valorile text ale elementelor.
 *
 * Exemplu de intrare:
 * ```xml
 * <date>
 *   <titlu>Articol</titlu>
 *   <autor>Ion</autor>
 * </date>
 * ```
 */
class XmlParser : ContentParser {

    /**
     * Parsează un șir XML și returnează o mapă element→text.
     *
     * @param content Șirul XML de parsat
     * @return Mapa cu perechile numeElement→textConținut
     */
    override fun parse(content: String): Map<String, Any> {
        val result = mutableMapOf<String, Any>()

        val doc = Jsoup.parse(content, "", Parser.xmlParser())
        val root = doc.children().firstOrNull() ?: return result

        for (child in root.children()) {
            result[child.tagName()] = child.text()
        }

        return result
    }
}
