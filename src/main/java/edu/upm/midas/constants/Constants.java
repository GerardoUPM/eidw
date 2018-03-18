package edu.upm.midas.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 27/3/17.
 * @project ExtractionInformationWikipedia
 * @version ${<VERSION>}
 * @author Gerardo Lagunes G.
 * @className Constants
 * @see
 */
@Component
public class Constants {

    @Value("${my.service.client.disease_album.name}")
    public String DISNET_DIALIST_CLIENT_NAME;
    @Value("${my.service.client.disease_album.url}")
    public String SERVICE_DIALIST_URL;
    @Value("${my.service.client.disease_album.path.last}")
    public String SERVICE_DIALIST_PATH_LAST;
    @Value("${my.service.client.disease_album.path.get}")
    public String SERVICE_DIALIST_PATH_GET;

    @Value("${my.service.dialist.name}")
    public String SERVICE_DIALIST_NAME;
    @Value("${my.service.dialist.code}")
    public String SERVICE_DIALIST_CODE;
    @Value("${my.service.metamap.name}")
    public String SERVICE_METAMAP_NAME;
    @Value("${my.service.metamap.code}")
    public String SERVICE_METAMAP_CODE;
    @Value("${my.service.tvp.name}")
    public String SERVICE_TVP_NAME;
    @Value("${my.service.tvp.code}")
    public String SERVICE_TVP_CODE;


    public static final String HTTP_HEADER = "http://";
    public static final String VERSION_PROJECT = "1.0";

    public static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJncmFyZG9sYWdhckBob3RtYWlsLmNvbSIsImF1ZCI6IndlYiIsIm5hbWUiOiJHZXJhcmRvIExhZ3VuZXMiLCJ1c2VyIjp0cnVlLCJpYXQiOjE1MDk2MTQyNjh9.uVhDgfLrAgdnj02Hsbgfj9tkVlfni89i0hKVYW31eHApCHpheikK9ae1MhbzRhiyUcFGMKwtiyVgff5NCMY3PA";
    public static final String EXTRACTION_HISTORY_FOLDER = "tmp/mm/";

    public static final String IMAGE_PATTERN =
            "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

    public static final String URLS[] ={"en.wikipedia.org/wiki/Sulzberger\u2013Garbe_syndrome",
            "en.wikipedia.org/wiki/Johnson\u2013McMillin_syndrome",
            "en.wikipedia.org/wiki/Rosselli\u2013Gulienetti_syndrome",
            "en.wikipedia.org/wiki/Rosenthal\u2013Kloepfer_syndrome",
            "en.wikipedia.org/wiki/Flynn\u2013Aird_syndrome",
            "en.wikipedia.org/wiki/Bj\u00F6rnstad_syndrome",
            "en.wikipedia.org/wiki/Marshall\u2013White_syndrome",
            "en.wikipedia.org/wiki/Guillain\u2013Barr\u00E9_syndrome",
            "en.wikipedia.org/wiki/Waterhouse\u2013Friderichsen_syndrome",
            "en.wikipedia.org/wiki/Congenital_adrenal_hyperplasia_due_to_11\u03B2-hydroxylase_deficiency",
            "en.wikipedia.org/wiki/Antley\u2013Bixler_syndrome",
            "en.wikipedia.org/wiki/Dejerine\u2013Roussy_syndrome",
            "en.wikipedia.org/wiki/Inborn_errors_of_purine\u2013pyrimidine_metabolism",
            "en.wikipedia.org/wiki/Kashin\u2013Beck_disease",
            "en.wikipedia.org/wiki/Marden\u2013Walker_syndrome",
            "en.wikipedia.org/wiki/Fourth_cholera_pandemic_(1863\u201375)",
            "en.wikipedia.org/wiki/Third_cholera_pandemic_(1852\u201360)",
            "en.wikipedia.org/wiki/Qu\u1EA3ng_Ng\u00E3i_skin_disease_outbreak",
            "en.wikipedia.org/wiki/Legg\u2013Calv\u00E9\u2013Perthes_disease",
            "en.wikipedia.org/wiki/Conradi\u2013H\u00FCnermann_syndrome",
            "en.wikipedia.org/wiki/Wolcott\u2013Rallison_syndrome",
            "en.wikipedia.org/wiki/Alstr\u00F6m_syndrome",
            "en.wikipedia.org/wiki/Dandy\u2013Walker_syndrome",
            "en.wikipedia.org/wiki/M\u00FCllerian_agenesis",
            "en.wikipedia.org/wiki/Short_rib_\u2013_polydactyly_syndrome",
            "en.wikipedia.org/wiki/B\u00E1lint's_syndrome",
            "en.wikipedia.org/wiki/Parry\u2013Romberg_syndrome",
            "en.wikipedia.org/wiki/Brown\u2013Vialetto\u2013Van_Laere_syndrome",
            "en.wikipedia.org/wiki/Urban\u2013Rogers\u2013Meyer_syndrome",
            "en.wikipedia.org/wiki/Fitz-Hugh\u2013Curtis_syndrome",
            "en.wikipedia.org/wiki/Miller\u2013Dieker_syndrome",
            "en.wikipedia.org/wiki/Pallister\u2013Killian_syndrome",
            "en.wikipedia.org/wiki/Lipsch\u00FCtz_ulcer",
            "en.wikipedia.org/wiki/Bing\u2013Neel_syndrome",
            "en.wikipedia.org/wiki/Sj\u00F6gren's_syndrome",
            "en.wikipedia.org/wiki/Henoch\u2013Sch\u00F6nlein_purpura",
            "en.wikipedia.org/wiki/Eosinophilia\u2013myalgia_syndrome",
            "en.wikipedia.org/wiki/Gianotti\u2013Crosti_syndrome",
            "en.wikipedia.org/wiki/Arnold\u2013Chiari_malformation",
            "en.wikipedia.org/wiki/Diamond\u2013Blackfan_anemia",
            "en.wikipedia.org/wiki/Potocki\u2013Lupski_syndrome",
            "en.wikipedia.org/wiki/Obsessive\u2013compulsive_personality_disorder",
            "en.wikipedia.org/wiki/Tolosa\u2013Hunt_syndrome",
            "en.wikipedia.org/wiki/Wernicke\u2013Korsakoff_syndrome",
            "en.wikipedia.org/wiki/Plummer\u2013Vinson_syndrome",
            "en.wikipedia.org/wiki/Niemann\u2013Pick_disease,_type_C",
            "en.wikipedia.org/wiki/Millard\u2013Gubler_syndrome",
            "en.wikipedia.org/wiki/Letterer\u2013Siwe_disease",
            "en.wikipedia.org/wiki/Kleine\u2013Levin_syndrome",
            "en.wikipedia.org/wiki/Freeman\u2013Sheldon_syndrome",
            "en.wikipedia.org/wiki/Weill\u2013Marchesani_syndrome",
            "en.wikipedia.org/wiki/Pitt\u2013Hopkins_syndrome",
            "en.wikipedia.org/wiki/Potocki\u2013Shaffer_syndrome",
            "en.wikipedia.org/wiki/Kufor\u2013Rakeb_syndrome",
            "en.wikipedia.org/wiki/Tonic\u2013clonic_seizure",
            "en.wikipedia.org/wiki/Beckwith\u2013Wiedemann_syndrome",
            "en.wikipedia.org/wiki/Aarskog\u2013Scott_syndrome",
            "en.wikipedia.org/wiki/Bernard\u2013Soulier_syndrome",
            "en.wikipedia.org/wiki/Brown-S\u00E9quard_syndrome",
            "en.wikipedia.org/wiki/Birt\u2013Hogg\u2013Dub\u00E9_syndrome",
            "en.wikipedia.org/wiki/Bardet\u2013Biedl_syndrome",
            "en.wikipedia.org/wiki/Hand\u2013Sch\u00FCller\u2013Christian_disease",
            "en.wikipedia.org/wiki/Bannayan\u2013Riley\u2013Ruvalcaba_syndrome",
            "en.wikipedia.org/wiki/Zunich\u2013Kaye_syndrome",
            "en.wikipedia.org/wiki/C\u0153ur_en_sabot",
            "en.wikipedia.org/wiki/Zimmermann\u2013Laband_syndrome",
            "en.wikipedia.org/wiki/Allan\u2013Herndon\u2013Dudley_syndrome",
            "en.wikipedia.org/wiki/Arthrogryposis\u2013renal_dysfunction\u2013cholestasis_syndrome",
            "en.wikipedia.org/wiki/Bart\u2013Pumphrey_syndrome",
            "en.wikipedia.org/wiki/Johnson\u2013Munson_syndrome",
            "en.wikipedia.org/wiki/Lymphedema\u2013distichiasis_syndrome",
            "en.wikipedia.org/wiki/Charcot\u2013Marie\u2013Tooth_disease",
            "en.wikipedia.org/wiki/Ch\u00E9diak\u2013Higashi_syndrome",
            "en.wikipedia.org/wiki/Creutzfeldt\u2013Jakob_disease",
            "en.wikipedia.org/wiki/Crigler\u2013Najjar_syndrome",
            "en.wikipedia.org/wiki/Dubin\u2013Johnson_syndrome",
            "en.wikipedia.org/wiki/Ellis\u2013van_Creveld_syndrome",
            "en.wikipedia.org/wiki/Ehlers\u2013Danlos_syndrome",
            "en.wikipedia.org/wiki/Emery\u2013Dreifuss_muscular_dystrophy",
            "en.wikipedia.org/wiki/Caf\u00E9_au_lait_spot",
            "en.wikipedia.org/wiki/Dejerine\u2013Sottas_disease",
            "en.wikipedia.org/wiki/Buschke\u2013Ollendorff_syndrome",
            "en.wikipedia.org/wiki/Cartilage\u2013hair_hypoplasia",
            "en.wikipedia.org/wiki/DeSanctis\u2013Cacchione_syndrome",
            "en.wikipedia.org/wiki/Ectrodactyly\u2013ectodermal_dysplasia\u2013cleft_syndrome",
            "en.wikipedia.org/wiki/Lesch\u2013Nyhan_syndrome",
            "en.wikipedia.org/wiki/Hermansky\u2013Pudlak_syndrome",
            "en.wikipedia.org/wiki/Holt\u2013Oram_syndrome",
            "en.wikipedia.org/wiki/Hailey\u2013Hailey_disease",
            "en.wikipedia.org/wiki/Machado\u2013Joseph_disease",
            "en.wikipedia.org/wiki/Lhermitte\u2013Duclos_disease",
            "en.wikipedia.org/wiki/Keratitis\u2013ichthyosis\u2013deafness_syndrome",
            "en.wikipedia.org/wiki/Haim\u2013Munk_syndrome",
            "en.wikipedia.org/wiki/Hay\u2013Wells_syndrome",
            "en.wikipedia.org/wiki/Limb\u2013mammary_syndrome",
            "en.wikipedia.org/wiki/Loeys\u2013Dietz_syndrome",
            "en.wikipedia.org/wiki/McCune\u2013Albright_syndrome",
            "en.wikipedia.org/wiki/M\u00E9ni\u00E8re's_disease",
            "en.wikipedia.org/wiki/Niemann\u2013Pick_disease",
            "en.wikipedia.org/wiki/Obsessive\u2013compulsive_disorder",
            "en.wikipedia.org/wiki/Peutz\u2013Jeghers_syndrome",
            "en.wikipedia.org/wiki/Prader\u2013Willi_syndrome",
            "en.wikipedia.org/wiki/Maroteaux\u2013Lamy_syndrome",
            "en.wikipedia.org/wiki/Pelizaeus\u2013Merzbacher_disease",
            "en.wikipedia.org/wiki/Muir\u2013Torre_syndrome",
            "en.wikipedia.org/wiki/Nail\u2013patella_syndrome",
            "en.wikipedia.org/wiki/Muckle\u2013Wells_syndrome",
            "en.wikipedia.org/wiki/Papillon\u2013Lef\u00E8vre_syndrome",
            "en.wikipedia.org/wiki/Mohr\u2013Tranebj\u00E6rg_syndrome",
            "en.wikipedia.org/wiki/Neonatal_ichthyosis\u2013sclerosing_cholangitis_syndrome",
            "en.wikipedia.org/wiki/Rubinstein\u2013Taybi_syndrome",
            "en.wikipedia.org/wiki/Tay\u2013Sachs_disease",
            "en.wikipedia.org/wiki/Wolff\u2013Parkinson\u2013White_syndrome",
            "en.wikipedia.org/wiki/Rothmund\u2013Thomson_syndrome",
            "en.wikipedia.org/wiki/Sj\u00F6gren\u2013Larsson_syndrome",
            "en.wikipedia.org/wiki/Urbach\u2013Wiethe_disease",
            "en.wikipedia.org/wiki/Wiskott\u2013Aldrich_syndrome",
            "en.wikipedia.org/wiki/Von_Hippel\u2013Lindau_disease",
            "en.wikipedia.org/wiki/Simpson\u2013Golabi\u2013Behmel_syndrome",
            "en.wikipedia.org/wiki/Smith\u2013Magenis_syndrome",
            "en.wikipedia.org/wiki/Rabson\u2013Mendenhall_syndrome",
            "en.wikipedia.org/wiki/Townes\u2013Brocks_syndrome",
            "en.wikipedia.org/wiki/Rapp\u2013Hodgkin_syndrome",
            "en.wikipedia.org/wiki/Smith\u2013Fineman\u2013Myers_syndrome",
            "en.wikipedia.org/wiki/Tricho\u2013rhino\u2013phalangeal_syndrome",
            "en.wikipedia.org/wiki/Ulnar\u2013mammary_syndrome",
            "en.wikipedia.org/wiki/Walker\u2013Warburg_syndrome",
            "en.wikipedia.org/wiki/P\u00E9brine",
            "en.wikipedia.org/wiki/Upshaw\u2013Schulman_syndrome",
            "en.wikipedia.org/wiki/Kearns\u2013Sayre_syndrome",
            "en.wikipedia.org/wiki/K\u00F6hler_disease",
            "en.wikipedia.org/wiki/Yim\u2013Ebbin_syndrome",
            "en.wikipedia.org/wiki/Persistent_M\u00FCllerian_duct_syndrome",
            "en.wikipedia.org/wiki/Howel\u2013Evans_syndrome",
            "en.wikipedia.org/wiki/Cranio\u2013lenticulo\u2013sutural_dysplasia",
            "en.wikipedia.org/wiki/Lujan\u2013Fryns_syndrome",
            "en.wikipedia.org/wiki/Bazex\u2013Dupr\u00E9\u2013Christol_syndrome",
            "en.wikipedia.org/wiki/Coffin\u2013Siris_syndrome",
            "en.wikipedia.org/wiki/Albinism\u2013deafness_syndrome",
            "en.wikipedia.org/wiki/Microphthalmia\u2013dermal_aplasia\u2013sclerocornea_syndrome",
            "en.wikipedia.org/wiki/Acro\u2013dermato\u2013ungual\u2013lacrimal\u2013tooth_syndrome",
            "en.wikipedia.org/wiki/Craniosynostosis\u2013anal_anomalies\u2013porokeratosis_syndrome",
            "en.wikipedia.org/wiki/Gougerot\u2013Blum_syndrome",
            "en.wikipedia.org/wiki/Raymond_C\u00E9stan_syndrome",
            "en.wikipedia.org/wiki/Waldenstr\u00F6m_hyperglobulinemic_purpura",
            "en.wikipedia.org/wiki/Cruveilhier\u2013Baumgarten_disease",
            "en.wikipedia.org/wiki/Budd\u2013Chiari_syndrome",
            "en.wikipedia.org/wiki/L\u00F6ffler's_syndrome",
            "en.wikipedia.org/wiki/Osgood\u2013Schlatter_disease",
            "en.wikipedia.org/wiki/Fox\u2013Fordyce_disease",
            "en.wikipedia.org/wiki/Lennox\u2013Gastaut_syndrome",
            "en.wikipedia.org/wiki/Camurati\u2013Engelmann_disease",
            "en.wikipedia.org/wiki/Langer\u2013Giedion_syndrome",
            "en.wikipedia.org/wiki/Kayser\u2013Fleischer_ring",
            "en.wikipedia.org/wiki/Sturge\u2013Weber_syndrome",
            "en.wikipedia.org/wiki/Zollinger\u2013Ellison_syndrome",
            "en.wikipedia.org/wiki/Irregular_sleep\u2013wake_rhythm",
            "en.wikipedia.org/wiki/Crimean\u2013Congo_hemorrhagic_fever",
            "en.wikipedia.org/wiki/Smith\u2013Lemli\u2013Opitz_syndrome",
            "en.wikipedia.org/wiki/Fazio\u2013Londe_disease",
            "en.wikipedia.org/wiki/Kl\u00FCver\u2013Bucy_syndrome",
            "en.wikipedia.org/wiki/Saethre\u2013Chotzen_syndrome",
            "en.wikipedia.org/wiki/Sch\u00F6pf\u2013Schulz\u2013Passarge_syndrome",
            "en.wikipedia.org/wiki/Weissenbacher\u2013Zweym\u00FCller_syndrome",
            "en.wikipedia.org/wiki/Fitzsimmons\u2013Guilbert_syndrome",
            "en.wikipedia.org/wiki/Adams\u2013Oliver_syndrome",
            "en.wikipedia.org/wiki/Stevens\u2013Johnson_syndrome",
            "en.wikipedia.org/wiki/Lambert\u2013Eaton_myasthenic_syndrome",
            "en.wikipedia.org/wiki/Kapur\u2013Toriello_syndrome",
            "en.wikipedia.org/wiki/Goldberg\u2013Shprintzen_syndrome",
            "en.wikipedia.org/wiki/Keppen\u2013Lubinsky_syndrome",
            "en.wikipedia.org/wiki/Foix\u2013Alajouanine_syndrome",
            "en.wikipedia.org/wiki/Jaffe\u2013Campanacci_syndrome",
            "en.wikipedia.org/wiki/Killian\u2013Jamieson_diverticulum",
            "en.wikipedia.org/wiki/Klippel\u2013Tr\u00E9naunay_syndrome",
            "en.wikipedia.org/wiki/Abderhalden\u2013Kaufmann\u2013Lignac_syndrome",
            "en.wikipedia.org/wiki/Wende\u2013Bauckus_syndrome",
            "en.wikipedia.org/wiki/Doege\u2013Potter_syndrome",
            "en.wikipedia.org/wiki/Mallory\u2013Weiss_syndrome",
            "en.wikipedia.org/wiki/S\u00E9zary_disease",
            "en.wikipedia.org/wiki/Salter\u2013Harris_fracture",
            "en.wikipedia.org/wiki/Swyer\u2013James_syndrome",
            "en.wikipedia.org/wiki/Sex_cord\u2013gonadal_stromal_tumour",
            "en.wikipedia.org/wiki/Sakati\u2013Nyhan\u2013Tisdale_syndrome",
            "en.wikipedia.org/wiki/Abdallat\u2013Davis\u2013Farrage_syndrome",
            "en.wikipedia.org/wiki/Mixed_M\u00FCllerian_tumor",
            "en.wikipedia.org/wiki/Shwachman\u2013Diamond_syndrome",
            "en.wikipedia.org/wiki/Pallister\u2013Hall_syndrome",
            "en.wikipedia.org/wiki/Acid\u2013base_imbalance",
            "en.wikipedia.org/wiki/Wilson\u2013Mikity_syndrome",
            "en.wikipedia.org/wiki/May\u2013Thurner_syndrome",
            "en.wikipedia.org/wiki/Favre\u2013Racouchot_syndrome",
            "en.wikipedia.org/wiki/Oliver\u2013McFarlane_syndrome",
            "en.wikipedia.org/wiki/Wallis\u2013Zieff\u2013Goldblatt_syndrome",
            "en.wikipedia.org/wiki/Schinzel\u2013Giedion_syndrome",
            "en.wikipedia.org/wiki/Donnai\u2013Barrow_syndrome",
            "en.wikipedia.org/wiki/Odonto\u2013tricho\u2013ungual\u2013digital\u2013palmar_syndrome",
            "en.wikipedia.org/wiki/Senior\u2013L\u00F8ken_syndrome",
            "en.wikipedia.org/wiki/Silver\u2013Russell_syndrome",
            "en.wikipedia.org/wiki/Churg\u2013Strauss_syndrome",
            "en.wikipedia.org/wiki/Johanson\u2013Blizzard_syndrome",
            "en.wikipedia.org/wiki/Rokitansky\u2013Aschoff_sinuses",
            "en.wikipedia.org/wiki/Young\u2013Simpson_syndrome",
            "en.wikipedia.org/wiki/Yush\u014D_disease",
            "en.wikipedia.org/wiki/Bogart\u2013Bacall_syndrome",
            "en.wikipedia.org/wiki/Niemann\u2013Pick_disease,_SMPD1-associated",
            "en.wikipedia.org/wiki/Wolf\u2013Hirschhorn_syndrome",
            "en.wikipedia.org/wiki/Gerstmann\u2013Str\u00E4ussler\u2013Scheinker_syndrome",
            "en.wikipedia.org/wiki/Laurence\u2013Moon_syndrome",
            "en.wikipedia.org/wiki/M\u00E9n\u00E9trier's_disease",
            "en.wikipedia.org/wiki/Epstein\u2013Barr_virus_infection",
            "en.wikipedia.org/wiki/Marinesco\u2013Sj\u00F6gren_syndrome",
            "en.wikipedia.org/wiki/Hajdu\u2013Cheney_syndrome",
            "en.wikipedia.org/wiki/Barraquer\u2013Simons_syndrome",
            "en.wikipedia.org/wiki/Yunis\u2013Varon_syndrome",
            "en.wikipedia.org/wiki/Jarisch\u2013Herxheimer_reaction",
            "en.wikipedia.org/wiki/Jackson\u2013Weiss_syndrome",
            "en.wikipedia.org/wiki/Sack\u2013Barabas_syndrome",
            "en.wikipedia.org/wiki/L\u00F6fgren_syndrome",
            "en.wikipedia.org/wiki/Bhaskar\u2013Jagannathan_syndrome",
            "en.wikipedia.org/wiki/Erdheim\u2013Chester_disease",
            "en.wikipedia.org/wiki/Roussy\u2013L\u00E9vy_syndrome",
            "en.wikipedia.org/wiki/Kohlsch\u00FCtter-T\u00F6nz_syndrome",
            "en.wikipedia.org/wiki/Aicardi\u2013Gouti\u00E8res_syndrome",
            "en.wikipedia.org/wiki/Hagemoser\u2013Weinstein\u2013Bresnick_syndrome",
            "en.wikipedia.org/wiki/Wiedemann\u2013Rautenstrauch_syndrome",
            "en.wikipedia.org/wiki/Leukotriene_receptor_antagonist-associated_Churg\u2013Strauss_syndrome",
            "en.wikipedia.org/wiki/Babinski\u2013Nageotte_syndrome",
            "en.wikipedia.org/wiki/Rosai\u2013Dorfman_disease",
            "en.wikipedia.org/wiki/Lightwood\u2013Albright_syndrome",
            "en.wikipedia.org/wiki/Melnick\u2013Needles_syndrome",
            "en.wikipedia.org/wiki/Marshall\u2013Smith_syndrome",
            "en.wikipedia.org/wiki/Lenz\u2013Majewski_syndrome",
            "en.wikipedia.org/wiki/Deficiency_of_the_interleukin-1\u2013receptor_antagonist",
            "en.wikipedia.org/wiki/Reis\u2013Bucklers_corneal_dystrophy",
            "en.wikipedia.org/wiki/McKusick\u2013Kaufman_syndrome",
            "en.wikipedia.org/wiki/Scalp\u2013ear\u2013nipple_syndrome",
            "en.wikipedia.org/wiki/Wilson\u2013Turner_syndrome",
            "en.wikipedia.org/wiki/Hypotrichosis\u2013lymphedema\u2013telangiectasia_syndrome",
            "en.wikipedia.org/wiki/Floating\u2013Harbor_syndrome",
            "en.wikipedia.org/wiki/Foster\u2013Kennedy_syndrome",
            "en.wikipedia.org/wiki/Villaret\u2019s_syndrome",
            "en.wikipedia.org/wiki/Non-mycosis_fungoides_CD30\u2212_cutaneous_large_T-cell_lymphoma",
            "en.wikipedia.org/wiki/Riga\u2013Fede_disease"};

    /** Validaciones */
    public static final String SEMANTIC_TYPES[] = { "sosy", "diap", "dsyn", "fndg", "lbpr", "lbtr" };
    public static final List<String> SEMANTIC_TYPES_LIST = new ArrayList<String>(){{
        add("sosy");
        add("diap");
        add("dsyn");
        add("fndg");
        add("lbpr");
        add("lbtr");

        add("inpr");
        add("menp");
        add("mobd");
        add("patf");
    }};


    /**
     * Fuentes de extracción
     * */
    public static final String SOURCE_WIKIPEDIA = "wikipedia";
    public static final String SOURCE_WIKIPEDIA_CODE = "SO01";

    /** Constantes para insertar en la base de datos */

    public static final String CONTENT_TYPE_PARA = "PARA";
    public static final String CONTENT_TYPE_LIST = "LIST";

    /**
     * Constantes para la extracción
     */

    public static final String WIKI_INFOBOX_SECTIONS[] = { "Classification and external resources", "Identifiers", "Databases", "External databases" };

    public static final String HTML_P = "p";
    public static final String HTML_H1 = "h1";
    public static final String HTML_H2 = "h2";
    public static final String HTML_H3 = "h3";
    public static final String HTML_H4 = "h4";
    public static final String HTML_DIV = "div";
    public static final String HTML_TR = "tr";
    public static final String HTML_TH = "th";
    public static final String HTML_TD = "td";
    public static final String HTML_LI = "li";
    public static final String HTML_UL = "ul";
    public static final String HTML_OL = "ol";
    public static final String HTML_IMG = "img";
    public static final String HTML_ALT = "alt";
    public static final String HTML_A = "a";
    public static final String HTML_B = "b";
    public static final String HTML_HREF = "href";
    public static final String HTML_COLSPAN = "colspan";
    public static final String HEAD = "head";
    public static final String FOOT = "foot";
    public static final String LIST = "list";
    public static final String TEXT = "text";
    public static final String HTML_TABLE = "table";

    /** CONSULTAS BIBLIOTECA JSOUP */
    public static final String QUERY_A_HREF = "a[href]";
    public static final String QUERY_A_CLASS = "a[class=";
    public static final String QUERY_ABS_HREF = "abs:href";
    public static final String QUERY_TABLE_CLASS = "table[class=";
    public static final String QUERY_DIV_CLASS = "div[class=";
    public static final String QUERY_TD_CLASS = "td[class=";//text-align:center
    public static final String QUERY_TH_STYLE_TEXT_ALIGN_CENTER = "th[style=text-align:center]";
    public static final String QUERY_TD_STYLE_TEXT_ALIGN_CENTER = "td[style=text-align:center]";


    /** Ruta del archivo XML source */
    public static final String XML_SOURCE_FILE = "src/main/resources/parameters/sources.xml";
    public static final String TXT_FILE = "src/main/resources/parameters/texts.txt";
    public static final String TXT_CONCEPT_FILE = "src/main/resources/parameters/concept.txt";


    /** Constantes para leer XML */

    public static final String XML_ROOT_TAG = "source";
    public static final String XML_TAG_SECTIONS = "sections";
    public static final String XML_TAG_HIGHLIGTS = "highlights";
    public static final String XML_TAG_LINKS = "links";
    public static final String XML_TAG_NAME = "name";
    public static final String XML_TAG_LINK = "link";
    public static final String XML_ATT_CONSULT = "consult";
    public static final String XML_ATT_CONSULT_YES = "y";
    public static final String XML_ATT_CONSULT_NO = "n";
    public static final String XML_ATT_ID = "id";
    public static final String XML_ATT_TYPE = "type";
    public static final String XML_ATT_CLASS = "class";
    public static final String XML_ATT_NAME = "name";
    public static final String XML_ATT_DESC = "desc";


    /**
     * XML_HIGHLIGHTS HL Constantes para consultar
     */
    public static final String XML_HL_DISEASENAME = "diseasename";
    public static final String XML_HL_INFOBOX = "infobox";
    public static final String XML_HL_EXTERNAL_TEXT = "externalresoruce";
    public static final String XML_HL_PLAIN_LIST = "plainlist";
    public static final String XML_HL_HORIZONTAL_LIST = "horizontallist";
    public static final String XML_HL_TEXT_ALIGN_CENTER = "textaligncenter";


    /**
     * Constantes para la validación de conceptos (metamap y proceso de validación de terminos)
     */

    public static final String CONSULT_SOURCE_ALL = "all";
    public static final String CONSULT_VERSION_LAST = "last";

    /**
     * Fecha por default
     */
    @SuppressWarnings("deprecation")
    public static final Date DEFAULT_DATE = new Date(70, 0, 1);

    /**
     * Cadena en blancos
     */
    public static final String BLANKS = "";

    /**
     * Punto
     */
    public static final String POINT = ".";

    /**
     * Coma
     */
    public static final String COMMA = ",";

    /**
     * Punto y coma
     */
    public static final String COMMA_DOT = ";";

    /**
     * Cero
     */
    public static final String ZERO = "0";

    /**
     * Slash
     */
    public static final String SLASH = "/";

    /**
     * Linea del piso
     */
    public static final String UNDER_SCORE = "_";

    /**
     * Guion
     */
    public static final String DASH = "-";

    /**
     * Uno
     */
    public static final String ONE = "1";

    /**
     * Tiempo estandar para dormir y esperar
     */
    public static final long SLEEP_TIME = 15000;

    /**
     * Menos
     */
    public static final String MINUS = "-";
    /**
     * Menos Cero
     */
    public static final String MINUS_ZERO = "-0";

    /**
     * Parametro de Url 1
     */
    public static final String URL_PARAM01 = "&1";

    /**
     * Parametro de Url 2
     */
    public static final String URL_PARAM02 = "&2";

    /**
     * Dos puntos
     */
    public static final String TWO_POINTS = ":";

    /**
     * Dos puntos seguido de cero
     */
    public static final String TWO_POINTS_ZERO = ":0";

    /**
     * Tres
     */
    public static final String THREE = "3";

    /**
     * Dos
     */
    public static final String TWO = "2";

    /**
     * Separador decimal para split
     */
    public static final String DECIM_SEP = "\\.";

    /**
     * Punto decimal
     */
    public static final String DEC_POINT = ".";

    /**
     * Espacio en blancos
     */
    public static final String BLANK_SPACE = " ";

    /**
     * Cinco
     */
    public static final String FIVE = "5";

    /**
     * Cuatro
     */
    public static final String FOUR = "4";

    /**
     * Ceros
     */
    public static final String ZEROS = "00";

    /**
     * Parametro 01
     */
    public static final String PARAM01 = "01";

    /**
     * Parametro 02
     */
    public static final String PARAM02 = "02";

    /**
     * Parametro 03
     */
    public static final String PARAM03 = "03";

    /**
     * Separador interno de parametros
     */
    public static final String INNER_PARAM_SEP = "_";

    /**
     * Flecha
     */
    public static final String ARROW = "==>";

    /**
     * Parentesis izquierdo
     */
    public static final String LEFT_PARENTHESIS = "[";

    /**
     * Parentesis derecho
     */
    public static final String RIGHT_PARENTHESIS = "]";

    /**
     * Porcentaje
     */
    public static final String PERCENTAGE = "%";

    /**
     * Punto
     */
    public static final String DOT = ".";

    /**
     * Add
     */
    public static final String ADD = "ADD";

    /**
     * Change
     */
    public static final String CHANGE = "CHANGE";

    /**
     * Delete
     */
    public static final String DELETE = "DELETE";

    /**
     * Ampersand
     */
    public static final String AMPERSAND = "&";

    /**
     * Init Change
     */
    public static final String INIT_CHANGE = "initChange";

    /**
     * Stdfunreq
     */
    public static final String STDFUNREQ = "stdfunreq";

    /**
     * Init Add
     */
    public static final String INIT_ADD = "initAdd";

    /**
     * Bean de autenticacion
     */
    public static final String AUTENTICATION_BEAN = "autenticationBean";

    /**
     * Nombre del subfile
     */
    public static final String NAME_SUBFILE = "subfile";

    /**
     * Parametro SE01 (Maximo intentoss inicio sesion)
     */
    public static final String SE01 = "SE01";

    /**
     * Parametro SE02 (Registros a mostrar en subfile)
     */
    public static final String SE02 = "SE02";

    /**
     * Parametro SE03 (Paginas en subfile)
     */
    public static final String SE03 = "SE03";

    /**
     * Parametro SE04 (Minimo caracteres contrase?a)
     */
    public static final String SE04 = "SE04";

    /**
     * Parametro SE05 (Dias para cambiar contrase?a)
     */
    public static final String SE05 = "SE05";

    /**
     * Parametro SE06 (Tiempo de bloqueo de usuario minutos)
     */
    public static final String SE06 = "SE06";

    /**
     * Parametro SE07 (Maximo de registros en consulta)
     */
    public static final String SE07 = "SE07";

    /**
     * Parametro SE08 (Direcci?n Endpoint Virtuoso)
     */
    public static final String SE08 = "SE08";

    /**
     * Parametro SE09 (Maximo de registros en autocompletar)
     */
    public static final String SE09 = "SE09";

    /**
     * Parametro SE10 (Url del servicio de visualizacion)
     */
    public static final String SE10 = "SE10";

    /**
     * Parametro SE11 (Indicador de visualizaci?n integrada)
     */
    public static final String SE11 = "SE11";

    /**
     * Nombre de Bean
     */
    public static final Object BEAN_NAME = "beanName";

    /**
     * Timer de tareas automaticas
     */
    public static final String TIMER = "TIMER_";

    /**
     * Seleccione
     */
    public static final String SELECCIONE = "seleccione";

    /**
     * Parametro
     */
    public static final String PARAMETER = "parameter";

    /**
     * Parametro 01 bean
     */
    public static final String BEAN_PARAM01 = "param1";

    /**
     * Parametro 02 bean
     */
    public static final String BEAN_PARAM02 = "param2";

    /**
     * SMTP
     */
    public static final String SMTP = "smtp";

    /**
     * Dias en la semana
     */
    public static final int DAYS_IN_WEEK = 7;

    /**
     * Hora en milisegundos
     */
    public static final long HOUR_MILIS = 3600000 * 23;

    /**
     * Nueva linea
     */
    public static final String NEW_LINE = "\n";

    /**
     * Atributo de Relaci?n
     */
    public static final String REL = "?rel_";

    /**
     * Objeto de query
     */
    public static final String OBJECT = "object";

    /**
     * Menor
     */
    public static final String MINOR = "<";

    /**
     * Mayor
     */
    public static final String MAJOR = ">";

    /**
     * Separador de mail (@)
     */
    public static final String MAIL_SEP = "@";

    /**
     * ID del paginador de listas
     */
    public static final String LIST_PAGINATOR = "listPaginator";

    /**
     * Subfile
     */
    public static final String SUBFILE = "subfile";

    /**
     * Ruta para imagen de hoja
     */
    public static final String PATH_LEAF = "/PhiBaseWeb/xmlhttp/css/rime/css-images/tree_document.gif";

    /**
     * Ruta para imagen de folder open
     */
    public static final String PATH_FOLDER_OPEN = "/PhiBaseWeb/xmlhttp/css/rime/css-images/tree_folder_closed.gif";

    /**
     * Ruta para imagen de folder close
     */
    public static final String PATH_FOLDER_CLOSE = "/PhiBaseWeb/xmlhttp/css/rime/css-images/tree_folder_open.gif";

    /**
     * Nodo Raiz
     */
    public static final String ROOT_NODE = "RootNode";

    /**
     * Ruta icono no encontrado
     */
    public static final String ICON_NOTFOUND = "/PhiBaseWeb/images/btn/vineta1.gif";

    /**
     * Ruta icono encontrado
     */
    public static final String ICON_FOUND = "/PhiBaseWeb/images/icons/normal/16/check_mark_16.png";

    /**
     * Query Result
     */
    public static final Object QUERY_RESULT = "queryResult";

    /**
     * Query Value
     */
    public static final Object QUERY_VALUE = "queryValue";

    /**
     * Gene
     */
    public static final String K00 = "00";

    /**
     * Host
     */
    public static final String K01 = "01";

    /**
     * Pathogen
     */
    public static final String K02 = "02";

    /**
     * Protocol Description
     */
    public static final String K03 = "03";

    /**
     * Interaction Context Mutant
     */
    public static final String K04 = "04";

    /**
     * Disease name
     */
    public static final String K05 = "05";

    /**
     * Gene Locus Id
     */
    public static final String K06 = "06";

    /**
     * Gene Function
     */
    public static final String K07 = "07";

    /**
     * Gene Name
     */
    public static final String K08 = "08";

    /**
     * Allele
     */
    public static final String K09 = "09";

    /**
     * Invitro Growth
     */
    public static final String K10 = "10";

    /**
     * Lethal Knockout
     */
    public static final String K11 = "11";

    /**
     * Gene Accession
     */
    public static final String K12 = "12";

    /**
     * Citation
     */
    public static final String K13 = "13";

    /**
     * InterOperator
     */
    public static final String INTER_OP = "InterOp:";

    /**
     * Link
     */
    public static final String LINK = "link";

    /**
     * Igual
     */
    public static final String EQUAL = "=";

    /**
     * Dollar
     */
    public static final String DOLLAR = "$";

    /**
     * Separador de parametros
     */
    public static final String PARAM_SEP = "<>";

    /**
     * Separador de parametros
     */
    public static final String PARAM_SEP_2 = "?";


    public final static String CHAR_SEPARATOR = "!";

    public final static String VALIDATED_FOLDER = "cnv_data/validation/validated/";
    public final static String NO_VALIDATED_FOLDER = "cnv_data/validation/not_validated/";
    public final static String VALIDATION_FINDINGS_FILE = "vte_data/results/allFindings.fd";
    public final static String DISEASES_FINDINGS_FOLDER = "cnv_data/diseases_findings/";
    public final static String DISEASES_URLS_FILE = "cnv_data/diseases.lst";
    public final static String DISEASES_TEXTS_FOLDER = "cnv_data/diseasesData/";
    public final static String VALIDATION_FINDINGS_TEMP_FOLDER = "vte_data/temp_findings/";


    public final static String ERR_NO_PARAMETER = "No parameter was sent";
    public final static String ERR_EMPTY_PARAMETER = "Empty parameter";



}
