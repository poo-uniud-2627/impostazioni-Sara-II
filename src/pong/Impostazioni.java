// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
// Consegna 2: le impostazioni. Scadenza: domenica 1 novembre 2026.
package pong;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Le impostazioni di una partita di Pong: quanti punti servono per vincere, con quale scarto
 * minimo, a che velocità va la palla. È un record (lezione 4): una volta create, non cambiano.
 *
 * IL PEZZO MANCANTE: il costruttore che rifiuta i valori sbagliati e il metodo leggi.
 * Ogni metodo ha il suo contratto, nel commento sopra. I test ufficiali (cartella test)
 * controllano i contratti.
 */
public record Impostazioni(int puntiPerVincere, int scartoMinimo, int velocitaPalla) {

    /** Le impostazioni standard: 11 punti per vincere, scarto minimo 2, velocità della palla 5. */
    public static final Impostazioni STANDARD = new Impostazioni(11, 2, 5);

    /**
     * Il costruttore (lezione 7: un record che nasce giusto).
     * Promette: se i tre valori sono nei loro intervalli, le impostazioni con quei valori.
     * Lancia IllegalArgumentException se puntiPerVincere non è fra 1 e 99, se scartoMinimo
     * non è fra 1 e 10, o se velocitaPalla non è fra 1 e 20 (estremi compresi). Il messaggio
     * dice quale valore è sbagliato e perché.
     */
    
    public Impostazioni {
        
        if ((puntiPerVincere < 1) || (puntiPerVincere > 99)) {
            throw new IllegalArgumentException("Punteggio per vincere non valido, Valori ammessi tra 1 e 99 compresi.");
        }
          if ((scartoMinimo < 1) || (scartoMinimo > 10)) {
            throw new IllegalArgumentException("Scarto minimo non valido- Valori ammessi tra 1 e 10 compresi");
        }
          if ((velocitaPalla < 1) || (velocitaPalla > 20)) {
            throw new IllegalArgumentException("Velocità palla non valida. Valori ammessi tra 1 e 20 compresi");
        }
    }

    private static boolean numeri_interi(String parametro) {
        int numero_caratteri = parametro.length();
        for (int i= 0; i < numero_caratteri; i++) {
            if ((parametro.charAt(i) < '0') || (parametro.charAt(i) > '9')) {
                return false;
            } 

        }
        return true;

    }

    private static int controlla_parametro(int riga, String riga_corrente) {

        int posizioneUguale = riga_corrente.indexOf("=");
            String tempString = (riga_corrente.substring(posizioneUguale + 1).trim());

            if(tempString.isEmpty()) {
                throw new IllegalArgumentException("riga " + (riga+1) + ": Parametro non assegnato");
            }
           if (!numeri_interi(tempString)){
                throw new IllegalArgumentException("riga " + (riga+1) + ": Parametro non numerico");
            }
            return Integer.parseInt(tempString);
    }

    /**
     * Legge le impostazioni dalle righe di un file, una impostazione per riga.
     * Pretende: righe non è null e non contiene null.
     * Promette:
     *  - le righe vuote (o di soli spazi) e quelle che, tolti gli spazi iniziali, cominciano
     *    con # sono ignorate;
     *  - ogni altra riga è nella forma  chiave = valore , con spazi qualsiasi attorno alla chiave,
     *    al segno = e al valore; le chiavi sono puntiPerVincere, scartoMinimo, velocitaPalla;
     *  - un'impostazione che nelle righe non c'è prende il valore standard;
     *  - il risultato sono le impostazioni lette.
     * Lancia IllegalArgumentException, con un messaggio che comincia con riga N: » (N è il numero
     * della riga, contando da 1 tutte le righe, anche quelle vuote e i commenti), se in una riga:
     *  1. manca il segno = ;
     *  2. la chiave non è una delle tre;
     *  3. il valore non è un numero intero;
     *  4. la stessa chiave c'era già in una riga precedente;
     *  5. il valore è fuori dal suo intervallo (gli intervalli del costruttore).
     * Se ci sono errori in più righe, il messaggio è quello della riga più in alto.
     */
    public static Impostazioni leggi(List<String> righe) {
       int numero_righe = righe.size();
       boolean trovato_punti_per_vincere = false;
       boolean trovato_scarto_minimo = false;
       boolean trovato_velocita_palla = false;
       int lpuntiPerVincere = 11;
       int lscartoMinimo = 2;
       int lvelocitàPalla = 5;
       
       
       for (int i= 0; i < numero_righe; i++) {
       String riga_corrente = righe.get(i);
       if (riga_corrente == null) {

       continue;

       }

       riga_corrente = riga_corrente.trim();


       if (riga_corrente.isEmpty()) {
        continue;
       }
       
        if (riga_corrente.startsWith("#")) {
        continue;
       }

      if(!riga_corrente.contains("=")) {

       throw new IllegalArgumentException("riga " + (i+1) + ": non contiene l'uguale");
       }


       if ((riga_corrente.contains("puntiPerVincere"))) {
            if (trovato_punti_per_vincere) {
                throw new IllegalArgumentException("riga " + (i+1) + ": Punteggio per vincere già assegnato");
            }
            lpuntiPerVincere = controlla_parametro(i, riga_corrente);

            if ((lpuntiPerVincere < 1) || (lpuntiPerVincere > 99)) {
                throw new IllegalArgumentException("riga " + (i+1) + ": Punteggio per vincere non valido, Valori ammessi tra 1 e 99 compresi.");
            }

             trovato_punti_per_vincere = true;
    
       }

         else if ((riga_corrente.contains("scartoMinimo"))) {
            if (trovato_scarto_minimo) {
                throw new IllegalArgumentException("riga " + (i+1) + ": Scarto minimo già assegnato");
            }
            lscartoMinimo = controlla_parametro(i, riga_corrente);
            if ((lscartoMinimo < 1) || (lscartoMinimo > 10)) {
                throw new IllegalArgumentException("riga " + (i+1) + ": Scarto minimo non valido, Valori ammessi tra 1 e 10 compresi.");
            }
             trovato_scarto_minimo = true;
    
       }

        else if ((riga_corrente.contains("velocitaPalla"))) {
            if (trovato_velocita_palla) {
                throw new IllegalArgumentException("riga " + (i+1) + ": Velocità palla già assegnata");
            }
            lvelocitàPalla = controlla_parametro(i, riga_corrente);
            if ((lvelocitàPalla < 1) || (lvelocitàPalla > 20)) {
                throw new IllegalArgumentException("riga " + (i+1) + ": Velocità palla non valida, Valori ammessi tra 1 e 20 compresi.");
            }

             trovato_velocita_palla = true;
       }
       else {
        throw new IllegalArgumentException("riga " + (i+1) + ": Chiave errata");
            }
       }
       
       Impostazioni STANDARD = new Impostazioni(lpuntiPerVincere, lscartoMinimo, lvelocitàPalla);

        
        return STANDARD; 
    
    }

    /**
     * Legge le impostazioni da un file di testo.
     * Promette: come leggi, con le righe del file.
     * Lancia IOException se il file non si può leggere (per esempio non esiste): non la cattura,
     * la lascia a chi chiama. Lancia IllegalArgumentException come leggi.
     */
    public static Impostazioni daFile(Path file) throws IOException {
        return leggi(Files.readAllLines(file));
    }
}
