package ro.tuiasi.pp.lab04.crawler

/**
 * Parser pentru conținut JSON.
 *
 * Parsează un șir JSON simplu de forma `{"cheie": "valoare", ...}`
 * și returnează o mapă Kotlin.
 *
 * Implementare minimală fără biblioteci externe — parsează doar obiecte
 * JSON plate (fără obiecte sau array-uri imbricate).
 */
class JsonParser : ContentParser {

    /**
     * Parsează un șir JSON și returnează o mapă cheie→valoare.
     *
     * Exemplu de intrare:
     * ```json
     * {"titlu": "Articol", "autor": "Ion", "an": 2024}
     * ```
     *
     * @param content Șirul JSON de parsat
     * @return Mapa cu perechile cheie→valoare extrase
     */
    override fun parse(content: String): Map<String, Any> {
        val result = mutableMapOf<String, Any>()

        val trimmed = content.trim().trimStart('{').trimEnd('}')
        if (trimmed.isBlank()) return result

        val pairs = trimmed.split(",")
        for (pair in pairs) {
            val colonIndex = pair.indexOf(':')
            if (colonIndex == -1) continue

            val key = pair.substring(0, colonIndex).trim().trim('"')
            val value = pair.substring(colonIndex + 1).trim().trim('"')

            result[key] = value.toIntOrNull() ?: value
        }

        return result
    }
}
