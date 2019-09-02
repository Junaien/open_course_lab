package greedy;

import java.util.LinkedList;
import java.util.Queue;

public class Solution955 {
        private static class Node{
            int from;
            int to;
            public Node(int from, int to){
                this.from = from;
                this.to   = to;
            }
        }

    /**
     *
     * @param A
     * @return minDeletionSize
     * Intuition: We can ignore thoes strings that are definitely sorted
     * Algorithm1:
     * Initialize trouble string set S to contain {T1, T2, .... }, where T1 is set of string
     * Initialize S = {A}
     * Initialize return value rt  = 0
     * For each character column j in trouble string set S:
     *
     *    For each set T in S:
     *      if some strings in T is strictly unsorted in column j then
     *          increment rt
     *          continue next column interation
     *      else if some strings str1,str2....stri are equal at column j then
     *          break T into multiple sets {stri1,stri2, stri3...}, {strk1, strk2, strk3...} ...
     *      end
     *    endfor
     *
     * endfor
     * return rt
     *
     * Proof:
     * 1.1: at any iteration j, set T contains strings iff all those strings that are the same by comparing first j character
     *      (ignoring any column deleted by Algorithm1)
     *       >>we prove this by induction
     *       base: j = 0, true
     *       hypothesis: j 1.1 holds
     *       induction: at iteration j+1, if s1.charAt(j+1) != s2.charAt(j+1), s1 and s2 would not be in the same set T
     *
     * 1.2: after we delete the column where column rt is incremented, A is sorted, so OPT <= OPTA1
     *       >>>let's say some string s1, s2 is not sorted after Algorithm1 ends,
     *       assume without loss of generality that s1 comes before s2 in A,
     *       there must be first column j where s1.charAt(j) > s2.charAt(j),
     *       case 1: j == 0
     *               at iteration j == 0, everything is in one group
     *               so column j must be deleted
     *       case 2: j != 0
     *               then by 1.1 we know that s1,s2 must be group together in iteration j - 1
     *               so column j must be deleted
     *
     * 1.3: in Algorithm1 if we deleted column j, then OPT must not contains column j, so OPT >= OPTA1
     *       >>>we delete column because if we don't delete it, there is two strings that is not gonna be sorted
     *
     * 1.4: by 1.1, 1.2, 1.3 we know OPT == OPTA1
     */
    public int minDeletionSizeBFS(String[] A) {
            //using bfs
            //edge case
            if(A == null || A.length <= 1)return 0;
            int rt = 0;

            Queue<Node> pq = new LinkedList<>();
            int level = -1;
            pq.add(new Node(0,A.length-1));
            while(!pq.isEmpty()){
                level++;
                if(level == A[0].length())return rt;
                int size = pq.size();
                boolean strictlyUnsorted = false;
                boolean strictlySorted   = true;
                Queue<Node> copyPQ = new LinkedList<>(pq);
                outer:for(int k = 0; k < size; k++) {
                    Node n = pq.poll();
                    int i = n.from, to = n.to;
                    while (i < to) {
                        if (A[i].charAt(level) > A[i + 1].charAt(level)) {
                            strictlyUnsorted = true;
                            strictlySorted = false;
                            break outer;
                        } else if (A[i].charAt(level) < A[i + 1].charAt(level)) {
                            i++;
                        } else {
                            int possibleFrom = i;
                            while (i < to && A[i].charAt(level) == A[i + 1].charAt(level)) i++;
                            pq.add(new Node(possibleFrom, i));
                            strictlySorted = false;
                        }
                    }
                }
                if(strictlyUnsorted){
                    rt++;
                    pq = copyPQ;
                }else if(strictlySorted){
                    return rt;
                }
            }
            return rt;
        }
    public int minDeletionSizeGreedy(String[] A) {
        boolean[] sorted = new boolean[A.length-1];
        for(int i = 0; i < A.length; i++){

        }
        return 0;
    }
    public static void main(String []args) {
        Solution955 s = new Solution955();
        String [] input = new String[]{
                "jkaibviqzsjskwsdsiufpwgqmosdahfvbnzzkzxwgdjalcrzxbempjvzehhkjthfkrwdiphrqjqofjytgluaqwxtgpcoqjbheyif",
                "nhwmzmsiwdgczdzmhritexmbfvrpaifbulgxahpptzsggpdqllckqtoqwjvrncunyhikiknqaecngmxbwgnfmsgqhrskyqswfdub",
                "clfwkuvwchydzkpyjkprfoyboiwrajgegbfpedgmdewoypyldpmkngvymmkprtzvyxujhpghtaacruwrjtwemsbwhubyynlcpqem",
                "usqmgwoicggkjnksffxerzceofhwadhpxijernlnierzgoclijqwicofhqeplyykfefjogbxpvzwwsrpjhuxritaetjljmnbrzsc",
                "johosvxkdxxbhjtxasyfljxkqqjyadluxnqkzuweanygssqexhbwsaehskvltdwcyfryyuopckttfkitrykrdonsovatenehclhe",
                "zqiuxslvdibrtjefspfsgbsbwezxafmwqnfjlddtmcwivcubhvgmagyxvjrlkiitvzbanbholuhxtilofshyguprhohxzddijmfc",
                "ikihsfgqcinvdulsakyfhtcfnmlkatnjyucozyrkusdwmtfjxnwhynblctjcnecbupcpdgxnrddxotaspyiqfbpmxyasqmuyoyna",
                "ljstsannvovbwpsykdjzpcjexngdaxpltvnazdkzivzxnzgftteskrtifsgwjkonmkghjqpnjjaftsdekacibjxhzmnjlutyggbz",
                "fdqihzvitujyceqdsfzyllaysxrkaltnoutrpgeyditmlvggacbknhntwbgjnyzjhlojbclexbszrbimznnvliiwesjrwsanklya",
                "tljnkibtqcobakusbtgdvfyjnsxjbzlcanvpseqogbdghdxdgozcxygpyqzuranyxxtbpcnothclfgjsnvjykzyttztzunimdqti",
                "kyygdobkewmlcdruanlccaqaxkcxbhqckeuyfsgyaryplbhmxmmpfhgblbrqzhrzcewdubghckrrkxijcpgmznphumldqhadmsrr",
                "bszmgekvpdcpyjnvemfhggifzqqdcmcysuvyagnvpsitdmeublmxrorvqynkagiskptborcdlbkvcimojljhstbwcpxyziydfcex",
                "ymejclmkuailzokcrnqnjhoqvpficrdxmanqkajqxkwvlmjjkmznnepifrvwwptkhldebhnvsnjsvfakwuobacoounghvgewkggj",
                "ymgufuhvdryiyzpvetfeiekqbmtzcfndxaobhgjaudtvolgctoearvfncqslqdnurzyzusohfdpiskxavapjmwaxxdtfkvdffxcf",
                "fjvsogmkibrpfaqrmqzgeqrrywblctvhquukakynnoiqtumugzrgedkssssseoaqjelauzrlbuxrujzghsjtfkgoqupyqaaukbjw",
                "ifwuabacggcronwihtbwverjqpvycnxzncdurhcanszwixaeimufacodptzjbxzmykjpigzuyxfrcrplkgsqigzsynrdxfejjqtl",
                "fqmhiagjrwsiqjielrovqjomdhsadjbteuupawqhxvmwdtyagnlqqxmawvitjguunxrfgvnkhjaqulhzugutwcsxxabrhytwhrcg",
                "qbtimricjkkbnjtzmkkqyjjxljpmdesfykcbctybwfnakfaefyryhdugpbnofkfunslyhzqldbfczxrvilhuprfpkdqjuoeaibyh",
                "aedcludugslmldsnvsrdbkalcruseslnoulruggahvblquzijvhgekfigradwerfkshxjzaavwdhkcfnikgacexlcgddizbzosaj",
                "jawohdbixqgwipurfnebgupquuajekmmispottwpjfnvnqgkywyiaijtwceyrpgtriiqhpsbzclytzdxjnpnorzcupgwblwztggr",
                "mltzfjkaaqqrlqhhqouysguqiyybegtvhsnvpbamojxdnrezrpkidjoyufexdvwjvnishirdprxsvndbibtcnqqhocyyginziipw",
                "zlfayvvfxpymsisinungepicqedcfebsojuotggjfteuwucqpplnkhrderwdzdqunkfbkdviqxablzapsxbvsbvsjnmbuwvfudyy",
                "dbdvkkvdxxlfzdqyavexbxfberouftckxednyacbxpqhcqxiadtjwobztcswfiixavcjabyrrbdcvvwhsydftxiqtdyuvlkooter",
                "euzwuotpibasokuinwhktetrmjlbfomcfybsnrptrmybaxgyzniwrzdhwhyercmuoojhxvcniwglptnedoebkarmfsenndxwdlth",
                "tfsrtuddzsclhmrgsmjymftjdyusgtkmpynhinzoslwewvhtiufbfraqsiaspnvnrzowzfaascepcjaqsoqenmnhafgvtzllcvta",
                "ywqmzscdgerlwzrcrhngkcnrctysguwichdypxebojmzpvehcbtzmkvpeyhttensotxxpqqvfljrckqryzekebdnhcafmkmdyvlo",
                "nswqjnooloabflcwfwwfrzpzatidgvyaoldjmmvzemnkuwxarjsnpkwxrixyusffyqyhpssudtatmzlihqdlirgzsaabxwmbcpzo",
                "damwbxcsyaqvfvbstvwtafdddjhjhdcnohhtefopbrzxqdmlgjhpcubkgaaayusbipqjdwxjzzbodxmqhkprzcdplspzioephhss",
                "vfszkmzkxzuezmmytyhsxhbjveqphidkldzhpwecyxkgaostikdytprdsxkxmsdxtqqlpoubnxuvxmnswmitijzermuhhqublvbm",
                "oblumanpgdlavdqohnsnroqwcvmihpfumzdejccessujtapdsshcnkhtycrbjqwckhuwubtvxfqagtwpyjgvfrtssrhjfohwvcme",
                "rarbkiftbwnbsxmjwwoebluivlhfhipbqoknwoyqnptroqjjtlmjpyzdnlrfhbpcohmjadvrfdwkbfunrdumhqrmnpzrzhrtlilx",
                "kqqvkuxejaxgqbilmhwyehmiioxkhgwwqmdtsbwpepvhssfjzrhwyxwmfliikdwhyyzwlxconuvqduivzazkyypwqhdlbrkggcfu",
                "tntcstjmjjcnhtmvwzxxanjkqepfhuzystqrqadjzwfhztdognelpwimrzefdcoqhoauurgpxwvevahugvkrehfzxzkceudueqwd",
                "wvjdiyszuxtjvyfpjxfuyzsowqchipagvayzctxvwrtzduyklokrvreijahlbixnpvradtkakigoeahxaavkpypyfwbsydmqippo",
                "nzjkxprtmfccexexjxzdatrylqnoichqmsrzkaqxespmllsmxfgmuugdrqbeaicttmcqzufdpzgitmeqcjssydlohmubiugvrpdz",
                "fjcokewebfklthtdcthcmvcjdawoispkkqjncmquuzsgmpqxxvkfydqyyyckfcimlhjinkwjiszicqbjhjjuknqglujuiprmctct",
                "ioyxcatdvlqkhfldsqcjpxntnmenidrndxvlboimgtrlctjszeohxdxhzxvzsnuiyykahoxdlaitynwhkdyunfatfyztpdguscqf",
                "bldghfebykcuvfqqqnypkgbjhsgjilukhqmzzhtwugkzujcwysulakmacednvstedirkggkftxrnvrziknsmmhornofkiigainrs",
                "pkairjprxzrquunffudmvqlnkjltigzpvmcwixzvuootsfcvqfgznybmrjchfuxgnjcpdpivrqhomlpsefoaysztrnhrffupmkgd",
                "arksidnqkkzqfhbaafgaluuhufnojnglzxzsluuhnohnnarjpapjiniuqaevwampbjhvgbvfovcphkgqyqgfsiohnsnatylvhmwy",
                "uoqpdiucbsdlzssxmwrrjsdhsrbkjyqwxzowzhtmwicebbkiqcckrnundrygltvznacsetwvgqsujhfgrezrqmxnnopngfyhhoug",
                "zffnigsjqsdafaaxcnijagsazopflfogrgpioekcxjikcbixrbpydqreyqtlnvjsmqntpqiasejkxnirgfaavbmyjjuafkftjqor",
                "rxhuuybabqsfyitohbjtlffissirlstmhxtzttxuiwfcpicbxhlbuccrduyfglqzkmnuxhucbewnsmljpkgxrwkhprarhodupyyp",
                "urkzbzsxewrmdggdasyfsfqysuivlvytspivtuwzhizytxihabiglhlzryoizpatxmdtrmxpnoclmxgbhscaboenwdgwvmjxaceb",
                "ehatuekerlqvwjdnvobupiefsjullrzdvkzwvykjlfhtlcynchtxgqmiwwgvffweoavpcjeogdykozsuklflbingilmfuvvdtguw",
                "syhdocwibkxhaglbtbhupzsbygdomckcuktfytrlyqhbytssdrheygsdugmkjgthvmdvupfjzmnehsizhuhnhuizglgtchymzces",
                "wirgaeucpfqauhlckmduwarjxontmnonkjtaymhembhbfoznzjulslkhfhhvposeltgwgtnyfcqhvojvbuowztndgdacnjjsadnc",
                "lcmixpheqjvbcbeteimldkioaxzqmspdinzsyjeorpjtvogyzunjmwiwsnrjnqjzunbjdnsajwtfjabawxqdqzyaywzueecpdljq",
                "iwtbadvoncgtotaolrcpmkjayepfmgqxlliwniejdapxerrbwcghfkyibdbhxniussuaxmpthnvootxgizqwkawwteaodfmbyjiv",
                "doepkfxwwzcrpfdesseguafanwslndpkkfuqontpznkcrfpsejlmbqdnoynrmgmhzbygoyacgbijzydzyrcrofgstxgklfevnmnu",
                "jiyapskrfeyekesnbgrtmlxsvryvokbfmymivbgtzrwabelcmnxfwbkyndhqxwgmpoxphnyewffgntautwhspltfyejbuydinfkf",
                "lkcnyzchqafzqhxrtowubcxlsvlloxislcctpfnpxccumxglzxuxyupspwsdfoagskdtvmbxsojzsgdizarytlhksvrtymhggqrk",
                "llowhbynuoexejkotjprplypocwuowniljddvzdfccyzbmsmiuxerllezjywpldupnitpfumhtdeyhzobhxpjzooxhvqiitasxud",
                "gpmnzwjzclwvilcbrmfoyvmkufobpnarligekvdrtzgespahsjraygfvhzwbqfugjszyqtrxmlzupwtalxkdurrptsyzxjvxtwby",
                "acueuavadgprdpesjcdqdeupljvqpnjvqdxbfvcepzeberyxxrtlymegjeagcjdgqgewwpokologsdmdtgpkxpofqtisuxxoyrby",
                "ocgakgagdlpzrioyxxaafxfrzrstptljmguixqjpuqvwtrgzxqytcqeezcjfbuokepgzwsnjznlvhtpzxvxnjielfjuoeaxabycx",
                "febghveheeupoyoylgvcowlnkzulptmthlczcuvxvhmfqmreqrelatvmxcoyotxqanysxbnvmlmoqoiyhklncitidgpkyyjxokhg",
                "iwvglytldvevnewydtxflmnflhfjpwmyhtcknndjwjlcnydwjaammidkszlgtyvjaomiawguxyfwqzvzdcgokdcnvfzpeisbgipn",
                "vgxgjnboayigwvpwqqvwuloeztuwppoudysecrsfjdgtxuiyapuupkgyznmzysuflmsqoytfopuqanueoubnpwnymhdafbmftuvn",
                "szvotgfxdpidpskgfccufnmocsiyqsbodlcbmxckxizbbxaecqwdulhkvpmxgevgqidyegdgroxssobwjkpdrmiaxggxvhstdauq",
                "pzfdjmweujzzezfjttrjqzidwdljqgkcuuyhvorbnrjjpzmibuuhjikwsjdetvkemurlkrzxyreqaugyuvxjxfnehwslbxlufclq",
                "itucinlpehiggwruzfmskvqagyciradftxyshogziwkdhpwfyvnkvanqdpsoldirepfbkuxqnsduhywqayvjjqshbhczetnvaiqj",
                "luawbtrgnapjpxratsrfvexbfnjdrkeqecomkzazuetimsskqwzvduogwelpxlpplfhdapvjrutpxxcprycpuwswzwvfyvmkucog",
                "bysehosmbuwvhslwriwnlwrqjggmrosvrdwgyzjktllmzamkvdnvdtgapfluqrzhruierlynytiftcezxoqmksnmiixhehfkrtet",
                "cybmtopysizkieahtyxpjigposxjrzvxfxsfjkwdwhbhcbusyjnuysyckusurqykwcfcgionhffggibfuinfndskbyejkwbyjzxa",
                "kbjnpsnkvhsknzbfumcawezzkcieskloaopyrraeylqtvobgyfmpoaoonxhdjxfijhoowxkvsjsqhbvnxvlypdugitvhjohgwrlh",
                "qzncvgvoznqyznqqntsyjgcnwgjosfvzqvgxxnczopcgnhvcxtgfwapkluxhmyaizqhdiyoljpsrpfpihyfyszjowowobywkabpe",
                "hudtfqjtdlpxjfflxsprcnefifjhttebkrrwrnbyfdxkbezvwicuvfxzmgtqoyecchutijkcgwrszwonnwimobikkpljklzemdii",
                "aiowvioihnbhxwhldqsvloqrksqstwfptctgrmxufxtjrjxzltatepewykhlsxgbiixsddzsfukhsrbfgryymypjaysmgzvtdpyi",
                "cbecqrzdyroqdvzdonatgykvzmrptenizmkjoculkwrsxamdrljvixneqiizluwmrzgqipeobinknlnqpnogksklwmtyietuhooe",
                "ryspxyyhfixcojydlclqimnnafdwtjonlktupupumzwventofubrdlnakzjqczmlmzwlvsgxjybtezsnfgrwqrgyvnlldequvqjx",
                "jmnxuaotyxnogbyqvhlffvftcxietvxdyhwfmgaddjrdjjsrdnojoyqxtodbvhnraiavxejwpadjqvxtixajeqvrkhzfotmgblzy",
                "izcdqinjoauqyqhnmragidbmcyqnulfgodtpmujzsmrkqrrloxnnicnwmdxkmtzfgzlfokxgorqullwxdomrdhzickiibcrobgiz",
                "gwsnegqtmwqlhgbsgxlypgqaqavwupitigzgfwmpkilegcxmniequibnzloelfayizyflervwqrykuxivqygoppazebfexjevmsj",
                "mrytjqjrzfnalopyelgplkluxdbeuxopisnojxmgslqcgfqartvkcvmfslytxzykwmhinwfhqytzaofmcaegzitmmqliemnhgpjx",
                "qejwrvbxoadzblxmrduggbbowhqcvmgvisjvafwqftitfhlvfwuiwxcjikfxlsnhyfcljbuunlgwkbjmwqjyxwlolqpqskkprgkv",
                "izoiazeezydeexvrxladzzxfyqhwvrsmmoqwhdzksvqqpubilrfexhmdwyacwetzmdqcrcnwwgcskeoxoqxttvypszhgawqyecqd",
                "dekxgmudppbecpngmxgvwyvimolbwilqaufvdoarqokokxbygprautivsotbrebuitiloyiolhlkeoydcrsfnmgxsjbceqmownqc",
                "vausicsrmyellulqnfqdrsbcentpwaoidyfynktlkwhkkrmaikfiunfuilmnonsqicbctnlcbljgcwqejudtegtvutrhipfhudps",
                "otrwvumcunzvvyrowgbftrlweiamxwhwvticqizacxvvegirwdaebemnfkujjzizthshlzsxjvqpdpsybhaylkvskgudnczuegxt",
                "ekrhesfwdfnvbzpbwlllmtweimttxzmhrgpvzydoppgemogarckhyhlmmsyudjjwpikosjshyyodbiogeaaocnblxtaczaevdomo",
                "lnfgelkdgtkbkfyosxjooufvlhbgxjxnpjwhplyvhhbolimcsgbmsmnibfxhyglmecescrobuebeqmfxswaehyltoswoigykived",
                "rjqrexjxeeenshdzydrgymfhszgfycdamikxusueaasmejckplnrkazygivmoqsjfnerxulheosrcyjewsruwfchyztmkqrrfxgq",
                "ukptxikwtzjconkkjrlidrqacqubyyerbjuurldzdftzwlqiqwhyajusjmpwqllvykstamcunjpoylcznwlsyeaeayouxnhjjjbe",
                "kzfuvhdatxzxxfdauzrfnmvmplowyqflozdtlfnjcvyntdtsiykwlufoswqylpoxjrmzdkwlelttzetkcbbltrspuqiedtopdcuf",
                "dhaxxbxxbjxtrmrojfwxaxrbwjazybkrrryrhdejwtlvjhulamonxfxnspqdfhovroxnjgtcqzzkdhjezzzenqrkvhmgxhjipbto",
                "fnuaafmpbxwmevhlvwrngssyjityymktzaparonariqvveatrddeyamjdelocwddhgswtpdjmoytgsvooenrltftknsebpnspzhh",
                "etiqphmaftkjmyijyufeyygxtnupyslzcixcfcqomkyudxhksbmeqksllfihmhfpsffhrmxtpbrebasfnhnxfemnpmrohnevznad",
                "drcssqgvswsjbwcwxqhsuthnsxsfylrclnfatmyqcidxznutgzbgmxuvhaziikrjzpksallrxzvtcnbdadgyccylnrrfoqnblxtw",
                "vnunjqqesceobgfdsshubxpbbpgmyttjdqjzqajlmfpppjrnwebgmyrgqmxihrqoaouudadgxpgvtejcneteglfwqhctzvxhqvto",
                "iwxoqfegigprgyynarcexkasqjkayfuiikrmoyqcvncjhmjatgbkuvbbtipgomhvodoammlvrqebippkuyhpisnndplbnoxfpmqe",
                "clfcqvpvenmpmphmywoedohrzngtzzekvhhidnhuuajrwmdasndlijuegzuqtaxzgwbylatthewthiawqgqormxgjquuwzeebeto",
                "bhlaidavxwwnuuxkkvxdhbeerrbuzsfdpfxiqzsfiwoxwprnunfxfhythdyhmcvmbgpadvgjmqgbambzvenzcfpaftpqkrzwbpze",
                "qkifkgubaxtwlvsbpawoleoxqxozzifyvaprufedwwzwuwopugzylkchtevxpjehcvrrvxpwgybomieyiqerponimcgwgmtauwhm",
                "vkluxkngfcrzjrtaejdxipodubdhzvknykmveccfrkkqmrxtkwqzhnyhnibfkltizehyyuqjmbtidiizvxtpukjenpjxyadjhozy",
                "gacaiyvevitzlroxceeqynezrgrvzglpsampluxqtwmqooyoggcajsyfecxgmwawgurlhutfcesyeajrgchzayoshrepsexrrdli",
                "swcmcbnkhfbdgwltlbyrxtzdiqptzhpqvtuezmzlfuwxdkwhllbuhgggeoowpcrcdcmxcjmczfpxhaqlfqqxhpjftnevcimojsxg",
                "phpqqpufkwedfikyjqcrlsiyniqzzuqhzujjlaqxcqalrcudmxedqdlxyswjroxlelgnucmnaeimsefhgnndebvciymqvkboccrh",
                "ngaqpxgaeyjlwnfbhsrpekibuwuozitrrkxmpfapwuwpowrcvnlliquevpthqzrwpmgyvnczaqdvufghzefkaemrgwcwfezjnnzk",
                "qrwrsqdnouzadtdgawwtbfkmbsmrzsyiighqszkiykogzygyeuafgwxfinldrkyypjyxyrmhulmuiekddjqdlmkfovhntjgshxea"};
        System.out.print(s.minDeletionSizeBFS(input));
//        s.minDeletionSizeGreedy(input);


    }
}
