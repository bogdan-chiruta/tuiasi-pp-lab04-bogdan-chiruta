package ro.tuiasi.pp.lab04.crawler

/**
 * Crawler web care descarcă conținut și îl parsează prin injecție de dependențe.
 *
 * Respectă principiul inversiei dependențelor (DIP): primește un [ContentParser]
 * prin constructor, fără a cunoaște implementarea concretă.
 *
 * @property parser Parserul folosit pentru interpretarea conținutului descărcat
 */
class Crawler(private val parser: ContentParser) {

    /**
     * Descarcă conținutul de la [url] și îl parsează cu parserul injectat.
     *
     * @param url URL-ul de la care se descarcă conținutul
     * @return Mapa cu datele parsate din răspunsul HTTP
     */
    fun fetch(url: String): Map<String, Any> {
        return try {
            val client = okhttp3.OkHttpClient()
            val request = okhttp3.Request.Builder().url(url).build()
            val continut = client.newCall(request).execute().body?.string() ?: ""
            parser.parse(continut)
        } catch (e: Exception) {
            emptyMap()
        }
    }
}
