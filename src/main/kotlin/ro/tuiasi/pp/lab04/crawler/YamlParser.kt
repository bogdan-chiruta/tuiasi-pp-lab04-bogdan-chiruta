package ro.tuiasi.pp.lab04.crawler

/**
 * Parser pentru conținut YAML simplu.
 *
 * Parsează un șir YAML cu perechi simple `cheie: valoare` (fără structuri imbricate)
 * și returnează o mapă Kotlin.
 *
 * Exemplu de intrare:
 * ```yaml
 * titlu: Articol important
 * autor: Ion Ionescu
 * an: 2024
 * ```
 */
class YamlParser : ContentParser {

    /**
     * Parsează un șir YAML simplu și returnează o mapă cheie→valoare.
     *
     * @param content Șirul YAML de parsat (format `cheie: valoare` pe fiecare linie)
     * @return Mapa cu perechile cheie→valoare extrase
     */
    override fun parse(content: String): Map<String, Any> {
        val result = mutableMapOf<String, Any>()

        content.lines()
            .filter { it.isNotBlank() && !it.trimStart().startsWith('#') }
            .forEach { line ->
                val parts = line.split(":", limit = 2)
                if (parts.size < 2) return@forEach

                val key = parts[0].trim().trim('"')
                val value = parts[1].trim().trim('"')

                result[key] = value.toIntOrNull() ?: value
            }

        return result
    }
}
