package comp1140.ass2;

import java.util.Random;

public class TestUtility {
    static final int BASE_ITERATIONS = 100;
    static final int ROWS = 9;
    static final int COLS = 9;
    static final int TILES = 33;
    static final int ORIENTATIONS = 8;
    static final char BAD = Character.MAX_VALUE - 'Z';

    static char[] shuffleDeck(boolean red) {
        Random r = new Random();
        char[] rtn = new char[20];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 10; j++) {
                int idx;
                do {
                    idx = r.nextInt(20);
                } while (rtn[idx] != 0);
                rtn[idx] = (char) ((red ? 'A' : 'K') + j);
            }
        }
        return rtn;
    }

    static final String PATCH_CIRCLES[] = {
            "JObWXgHUYNaQKVIBDPZcLdefRASMCEFTG",
            "ODcMGYHNfVEgWIeACPBQJUFXRdKTLZSab",
            "VWDEXBIYcaZdUGSMLCNFeAHOfJQgKPRbT",
            "cfGVHFZaTbUIeNBdRWEgJXYKLMOPDACQS",
            "AfEUDFBQNKMRVTGeCdcHIWbXSYPgJLOZa"
    };

    static final String PLACEMENTS[] = {
            "SHGAEAAAFABD.GEDBJACD..OAACXFCCHAFDNEABaEGEhHBAVCECBGAC.PCEChFBALCACfDDAhEHAREACACHDTFGAWBHB.gBFBYFCC.QBDDhAIADGACdHGD.MAEAhADA.bAAB.",
            "BHHAJAAA.UFFBRACCdAFCKDFC..LBECZCFA..SHEAhFIAaADADAGD.GEABHCABVDEChBAAEBGA..IAFAhGIAeEACACAB.PABDXGCAbABBOAABhCIAYEHBgECGhIDA.CDDDFEABMFAC",
            "OFFCQAAAKGDC.RABD....VAEAEFGEXEGABAHB..hIDAICHAaEDEZCEEGDBAhEDAMBACCCADeBEEHBCAfABAhDEAgECAPDECWAFFDIAAcFAChAAASAHBNDDB.FADAhDIAAEHC....",
            "SHGAGAAAHABDFGGGTAFA.UCFBIEHABAHBRDACEEFFJDDEhAAAYHCGMCFDOEAEhACAPGBADCCDQDDBhIAAcFFD.fGAEaGCEeHFA.WCAD.hAEAgBEC.hAFAXABEADAB.",
            "EAGAUAAAFBGDBACA.KADDMADA..RAFCGABBeDFGCBFDHEEA.hAFA..IBDAhFIAbCEE.XAABSDAChAGAYBBBJDAEZEDEaGGAfFGCNBAAVFAATGDAhHGAWHAGLHFAhAHAOFFEDDDB.."
    };

    static final int SCORES[] = {
            34, 30, 20, 6, 12, 27, 17, 68, 32, -2
    };

    static final String INVALID_PLACEMENTS[] = {
            "SHGAEHGAFABD.GEDBJACD..OAACXFCCHAFDNEABaEGEhHBAVCECBGAC.PCEChFBALCACfDDAhEHAREACACHDTFGAWBHB.gBFBYFCC.QBDDhAIADGACdHGD.MAEAhADA.bAAB.",
            "BHHAJAAA.UHHBRACCdAFCKDFC",
            "OFFCQAAAKGDC.RABD....VAEAEFGEXEGABAHB..hIDAICHAaEDEZCEEGDBAhEDAMBACCCADeBEEHBCAfABAhDEAgECAPDECWAFFDIAAcFAChAAASAHBNDDB.OADAhDIAAEHC....",
            "SHGAGAAAHABDFGGGTAFA.UCFBIEHABAHBRDACEEFFJDDEhAAAYHCGMCFDOEAEhACAPGBADCCDQDDBhIAAcHGD.fGAEaGCEeHFA.WCAD.hAEAgBEC.hAFAXABEADAB.",
            "EAGAUAAAFBGDBACA.KADDMADA..RAFCGABBeDFGCBFDHEEA.hAFA..EBDAhFIAbCEE.XAABSDAChAGAYBBBJDAEZEDEaGGAfFGCNBAAVFAATGDAhHGAWHAGLHFAhAHAOFFEDDDB.."
    };

    static String badlyFormedTilePlacement(Random r) {
        char a = (char) ('A' + r.nextInt(ROWS));
        char bada = (char) ('A' + ROWS + r.nextInt(r.nextInt(BAD)));
        char b = (char) ('A' + r.nextInt(COLS));
        char badb = (char) ('A' + COLS + r.nextInt(BAD));
        char c = (char) ('A' + r.nextInt(TILES));
        if (c > 'Z') c += ('a'-'Z');
        char badc = (char) ('A' + TILES + r.nextInt(BAD));
        char d = (char) ('A' + ORIENTATIONS);
        char badd = (char) ('A' + ORIENTATIONS + r.nextInt(BAD));
        String test = "";
        switch (r.nextInt(5)) {
            case 0:
                test += "" + bada + b + c + d;
                break;
            case 1:
                test += "" + a + badb + c + d;
                break;
            case 2:
                test += "" + a + b + badc + d;
                break;
            case 3:
                test += "" + a + b + c + badd;
                break;
            default:
                test += "" + bada + b + badc + badd;
        }
        return test;

    }
}
