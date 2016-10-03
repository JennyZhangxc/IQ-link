package comp1110.ass2;

import java.util.*;

import static comp1110.ass2.Piece.A;

/**
 * This class provides the text interface for the Link Game
 *
 * The game is based directly on Smart Games' IQ-Link game
 * (http://www.smartgames.eu/en/smartgames/iq-link)
 */
public class LinkGame {
    static HashSet<String> A_placements=new HashSet<>(Arrays.asList("BAD", "VAA", "UAD", "RAE", "OAB", "NAE", "OAF", "QAD", "NAA", "KAF", "PAC", "JAE", "GAF", "IAD", "HAC", "KAB", "JAA", "GAB", "DAA", "CAD", "RAF", "UAA", "QAE", "TAD", "NAF", "JAF", "RAB", "KAC", "PAD", "IAE", "NAB", "OAC", "QAA", "JAB", "HAD", "IAA", "GAC", "EAA", "DAD", "QAF", "WAD", "PAA", "KAD", "PAE", "RAC", "OAD", "QAB", "TAA", "IAF", "NAC", "HAE", "JAC", "IAB", "BAA", "HAA", "EAD", "WAA", "PAF", "VAD", "NAD", "OAA", "OAE", "QAC", "JAD", "HAF", "KAE", "PAB", "IAC", "GAE", "HAB", "KAA", "CAA"));
    static HashSet<String> B_placements=new HashSet<>(Arrays.asList("OBH", "KBL", "NBK", "QBJ", "IBJ", "JBG", "KBD", "PBE", "NBC", "GBH", "QBB", "TBA", "HBE", "IBB", "EBD", "DBG", "CBJ", "RBH", "WBA", "OBI", "VBD", "UBG", "QBK", "TBJ", "NBL", "OBA", "IBK", "KBE", "PBF", "JBH", "GBI", "QBC", "NBD", "HBF", "IBC", "CBA", "BBD", "WBJ", "OBJ", "PBG", "QBL", "RBI", "IBL", "NBE", "OBB", "JBI", "QBD", "HBG", "KBF", "IBD", "JBA", "GBB", "EBJ", "OBK", "TBD", "PBH", "OBC", "NBF", "JBJ", "GBK", "QBE", "HBH", "KBG", "UBA", "RBB", "IBE", "JBB", "GBC", "EBA", "DBD", "UBJ", "CBG", "BBJ", "PBI", "QBF", "RBK", "WBD", "VBG", "OBL", "JBK", "KBH", "PBA", "NBG", "GBL", "RBC", "HBI", "IBF", "OBD", "JBC", "BBA", "HBA", "PBJ", "QBG", "RBL", "KBI", "PBB", "JBL", "NBH", "HBJ", "OBE", "IBG", "KBA", "JBD", "GBE", "HBB", "EBG", "DBJ", "VBA", "QBH", "RBE", "UBD", "NBI", "TBG", "PBK", "HBK", "KBJ", "IBH", "NBA", "OBF", "JBE", "PBC", "GBF", "HBC", "KBB", "DBA", "CBD", "VBJ", "BBG", "QBI", "RBF", "NBJ", "PBL", "WBG", "QBA", "HBL", "KBK", "OBG", "IBI", "NBB", "PBD", "JBF", "HBD", "KBC", "IBA"));
    static HashSet<String> C_placements=new HashSet<>(Arrays.asList("WCJ", "ECG", "DCJ", "RCI", "OCJ", "QCL", "OCB", "ICL", "KCF", "PCG", "JCI", "QCD", "NCE", "HCG", "ICD", "DCA", "JCA", "CCD", "GCB", "BCG", "OCK", "TCD", "PCH", "NCF", "OCC", "JCJ", "GCK", "QCE", "RCB", "HCH", "KCG", "UCA", "ICE", "JCB", "GCC", "OCL", "PCI", "WCD", "VCG", "UCJ", "RCK", "OCD", "NCG", "PCA", "JCK", "GCL", "QCF", "HCI", "KCH", "RCC", "ICF", "JCC", "HCA", "ECD", "DCG", "CCJ", "PCJ", "QCG", "RCL", "JCL", "KCI", "PCB", "NCH", "HCJ", "ICG", "OCE", "JCD", "KCA", "GCE", "CCA", "HCB", "BCD", "PCK", "UCD", "QCH", "TCG", "KCJ", "PCC", "NCI", "HCK", "RCE", "OCF", "ICH", "VCA", "KCB", "JCE", "GCF", "NCA", "HCC", "VCJ", "ECJ", "QCI", "RCF", "NCJ", "WCG", "PCL", "QCA", "HCL", "KCK", "ICI", "NCB", "OCG", "JCF", "PCD", "HCD", "KCC", "ICA", "ECA", "DCD", "CCG", "BCJ", "QCJ", "NCK", "QCB", "KCL", "OCH", "TCA", "ICJ", "NCC", "PCE", "JCG", "GCH", "BCA", "HCE", "KCD", "ICB", "RCH", "WCA", "VCD", "OCI", "UCG", "NCL", "QCK", "TCJ", "ICK", "OCA", "JCH", "KCE", "PCF", "NCD", "GCI", "QCC", "HCF", "ICC"));
    static HashSet<String> D_placements=new HashSet<>(Arrays.asList("CDG", "DDL", "ODL", "PDI", "RDK", "UDJ", "NDG", "ODD", "JDK", "PDA", "GDL", "QDF", "RDC", "HDI", "KDH", "UDB", "IDF", "JDC", "GDD", "HDA", "FDE", "PDJ", "ODE", "NDH", "PDB", "JDL", "MDK", "LDF", "QDG", "HDJ", "KDI", "IDG", "JDD", "MDC", "CDE", "HDB", "KDA", "EDG", "PDK", "QDH", "SDJ", "VDI", "KDJ", "PDC", "NDI", "RDE", "HDK", "IDH", "SDB", "VDA", "ODF", "JDE", "KDB", "NDA", "GDF", "HDC", "CDD", "BDG", "CDL", "PDL", "QDI", "VDJ", "KDK", "PDD", "QDA", "NDJ", "HDL", "RDF", "LDH", "ODG", "IDI", "VDB", "KDC", "JDF", "NDB", "HDD", "IDA", "EDE", "QDJ", "RDG", "NDK", "TDI", "QDB", "KDL", "IDJ", "NDC", "ODH", "TDA", "JDG", "PDE", "GDH", "HDE", "KDD", "IDB", "BDE", "EDD", "DDG", "EDL", "QDK", "RDH", "TDJ", "NDL", "WDI", "QDC", "ODI", "TDB", "IDK", "NDD", "PDF", "JDH", "WDA", "HDF", "KDE", "BDD", "ODA", "IDC", "WDJ", "BDL", "RDI", "WDB", "ODJ", "QDL", "RDA", "IDL", "ODB", "JDI", "KDF", "PDG", "NDE", "GDJ", "QDD", "HDG", "IDD", "JDA", "GDB", "DDE", "FDG", "ODK", "UDI", "ODC", "KDG", "PDH", "UDA", "JDJ", "GDK", "QDE", "NDF", "HDH", "IDE", "JDB", "GDC", "DDD"));
    static HashSet<String> E_placements=new HashSet<>(Arrays.asList("BEL", "SEJ", "PEK", "VEI", "OEF", "NEI", "SEB", "PEC", "QEH", "VEA", "HEK", "KEJ", "REE", "IEH", "NEA", "JEE", "GEF", "HEC", "KEB", "DEE", "FEG", "PEL", "QEI", "VEJ", "KEK", "PED", "NEJ", "QEA", "REF", "HEL", "IEI", "VEB", "LEH", "OEG", "JEF", "KEC", "NEB", "HED", "DED", "IEA", "CEG", "DEL", "QEJ", "TEI", "KEL", "PEE", "QEB", "TEA", "NEK", "REG", "OEH", "IEJ", "KED", "JEG", "GEH", "NEC", "HEE", "IEB", "FEE", "QEK", "REH", "NEL", "TEJ", "WEI", "QEC", "IEK", "NED", "OEI", "TEB", "JEH", "WEA", "PEF", "HEF", "KEE", "IEC", "OEA", "CEE", "EEG", "QEL", "REI", "WEJ", "QED", "REA", "OEJ", "IEL", "NEE", "PEG", "JEI", "WEB", "GEJ", "HEG", "KEF", "CED", "OEB", "IED", "BEG", "JEA", "GEB", "CEL", "OEK", "UEI", "OEC", "JEJ", "KEG", "PEH", "UEA", "NEF", "GEK", "QEE", "HEH", "IEE", "JEB", "GEC", "EEE", "REK", "OEL", "UEJ", "REC", "OED", "KEH", "PEI", "UEB", "JEK", "GEL", "QEF", "NEG", "HEI", "IEF", "BEE", "PEA", "JEC", "GED", "EED", "HEA", "DEG", "EEL", "PEJ", "NEH", "OEE", "JEL", "MEK", "PEB", "LEF", "QEG", "HEJ", "KEI", "IEG", "JED", "MEC", "BED", "HEB", "KEA"));
    static HashSet<String> F_placements=new HashSet<>(Arrays.asList("IFA", "QFJ", "TFI", "KFL", "PFE", "NFK", "QFB", "TFA", "RFG", "IFJ", "OFH", "JFG", "KFD", "NFC", "GFH", "BFE", "HFE", "EFD", "IFB", "DFG", "EFL", "TFJ", "QFK", "WFI", "PFF", "TFB", "NFL", "QFC", "RFH", "WFA", "OFI", "IFK", "KFE", "JFH", "BFD", "NFD", "HFF", "OFA", "IFC", "BFL", "QFL", "RFI", "WFJ", "QFD", "RFA", "IFL", "NFE", "OFJ", "JFI", "WFB", "PFG", "GFJ", "HFG", "KFF", "IFD", "OFB", "DFE", "JFA", "FFE", "GFB", "UFI", "QFE", "UFA", "OFK", "NFF", "PFH", "JFJ", "GFK", "HFH", "KFG", "OFC", "DFD", "IFE", "CFG", "JFB", "GFC", "DFL", "RFK", "OFL", "UFJ", "RFC", "OFD", "JFK", "KFH", "PFI", "UFB", "NFG", "QFF", "GFL", "HFI", "IFF", "JFC", "FFG", "PFA", "GFD", "HFA", "MFK", "OFE", "KFI", "PFJ", "JFL", "NFH", "QFG", "HFJ", "MFC", "LFF", "IFG", "CFE", "KFA", "PFB", "JFD", "HFB", "EFG", "SFJ", "PFK", "VFI", "NFI", "SFB", "OFF", "PFC", "QFH", "VFA", "RFE", "HFK", "KFJ", "IFH", "NFA", "JFE", "CFD", "GFF", "BFG", "HFC", "KFB", "CFL", "PFL", "VFJ", "OFG", "NFJ", "PFD", "LFH", "QFI", "VFB", "HFL", "KFK", "RFF", "IFI", "NFB", "JFF", "QFA", "HFD", "KFC", "EFE"));
    static HashSet<String> G_placements=new HashSet<>(Arrays.asList("EGG", "QGL", "WGJ", "PGG", "QGD", "RGI", "WGB", "OGJ", "IGL", "KGF", "JGI", "CGD", "GGJ", "NGE", "BGG", "HGG", "RGA", "OGB", "IGD", "FGE", "JGA", "CGL", "GGB", "UGI", "QGE", "UGA", "NGF", "OGK", "JGJ", "PGH", "GGK", "HGH", "KGG", "IGE", "OGC", "EGE", "JGB", "GGC", "UGJ", "RGK", "QGF", "UGB", "RGC", "OGL", "NGG", "PGI", "JGK", "GGL", "BGE", "HGI", "KGH", "OGD", "EGD", "IGF", "FGG", "PGA", "DGG", "JGC", "GGD", "HGA", "EGL", "MGK", "OGE", "JGL", "KGI", "PGJ", "NGH", "QGG", "MGC", "HGJ", "IGG", "BGD", "LGF", "JGD", "KGA", "PGB", "HGB", "BGL", "SGJ", "VGI", "RGE", "OGF", "SGB", "VGA", "KGJ", "PGK", "NGI", "QGH", "HGK", "IGH", "KGB", "PGC", "DGE", "JGE", "GGF", "NGA", "HGC", "PGL", "VGJ", "NGJ", "OGG", "PGD", "QGI", "VGB", "LGH", "RGF", "HGL", "KGK", "IGI", "NGB", "JGF", "DGD", "QGA", "CGG", "HGD", "KGC", "IGA", "DGL", "TGI", "OGH", "TGA", "NGK", "PGE", "QGJ", "KGL", "RGG", "IGJ", "NGC", "JGG", "GGH", "QGB", "HGE", "KGD", "IGB", "QGK", "TGJ", "WGI", "PGF", "NGL", "QGC", "TGB", "RGH", "WGA", "IGK", "OGI", "JGH", "KGE", "NGD", "CGE", "HGF", "IGC", "OGA"));
    static HashSet<String> H_placements=new HashSet<>(Arrays.asList("JHB", "GHC", "DHL", "RHK", "UHJ", "QHF", "RHC", "UHB", "NHG", "OHL", "JHK", "PHI", "GHL", "HHI", "KHH", "IHF", "OHD", "JHC", "FHG", "PHA", "GHD", "HHA", "QHG", "NHH", "PHJ", "JHL", "MHK", "LHF", "CHE", "HHJ", "KHI", "OHE", "IHG", "PHB", "EHG", "JHD", "MHC", "HHB", "KHA", "SHJ", "VHI", "RHE", "SHB", "VHA", "OHF", "KHJ", "PHK", "NHI", "QHH", "HHK", "IHH", "CHD", "JHE", "BHG", "KHB", "PHC", "NHA", "GHF", "HHC", "CHL", "VHJ", "RHF", "OHG", "VHB", "KHK", "PHL", "NHJ", "QHI", "HHL", "LHH", "IHI", "KHC", "PHD", "EHE", "JHF", "NHB", "QHA", "HHD", "IHA", "THI", "NHK", "OHH", "THA", "PHE", "QHJ", "RHG", "KHL", "IHJ", "NHC", "JHG", "BHE", "EHD", "QHB", "GHH", "DHG", "HHE", "KHD", "IHB", "EHL", "THJ", "WHI", "OHI", "THB", "NHL", "PHF", "WHA", "QHK", "RHH", "BHD", "OHA", "IHK", "NHD", "JHH", "QHC", "HHF", "KHE", "BHL", "IHC", "QHL", "WHJ", "PHG", "QHD", "RHI", "WHB", "IHL", "OHJ", "JHI", "KHF", "NHE", "GHJ", "DHE", "RHA", "HHG", "IHD", "OHB", "JHA", "FHE", "GHB", "UHI", "PHH", "UHA", "QHE", "OHK", "KHG", "JHJ", "GHK", "DHD", "NHF", "CHG", "HHH", "OHC", "IHE"));
    static HashSet<String> I_placements=new HashSet<>(Arrays.asList("BIL", "GIE", "EIK", "HIB", "KIA", "VII", "QIH", "VIA", "RIE", "NII", "SIJ", "PIK", "LIG", "HIK", "KIJ", "DIE", "OIF", "IIH", "NIA", "SIB", "BIK", "PIC", "JIE", "MID", "HIC", "KIB", "VIJ", "RIF", "SIC", "VIB", "OIG", "KIK", "PIL", "NIJ", "QII", "TIH", "HIL", "DID", "III", "LIH", "JIF", "CIG", "KIC", "PID", "NIB", "QIA", "HID", "DIL", "IIA", "WIH", "RIG", "OIH", "VIC", "KIL", "AID", "TII", "NIK", "QIJ", "CIF", "IIJ", "KID", "PIE", "JIG", "TIA", "NIC", "QIB", "HIE", "DIK", "LIA", "IIB", "TIJ", "WII", "NIL", "OII", "TIB", "WIA", "PIF", "QIK", "RIH", "IIK", "NID", "OIA", "JIH", "CIE", "FIL", "AIK", "QIC", "GII", "EIG", "HIF", "KIE", "IIC", "WIJ", "OIJ", "TIC", "PIG", "WIB", "QIL", "UIH", "RII", "CID", "OIB", "IIL", "NIE", "BIG", "JII", "GIJ", "EIF", "QID", "HIG", "KIF", "RIA", "CIL", "IID", "FIE", "JIA", "GIB", "XIH", "UII", "PIH", "UIA", "QIE", "WIC", "OIK", "JIJ", "KIG", "NIF", "BIF", "GIK", "EIE", "RIB", "HIH", "IIE", "OIC", "JIB", "CIK", "FIF", "GIC", "UIJ", "PII", "UIB", "QIF", "MIJ", "OIL", "BIE", "KIH", "PIA", "JIK", "GIL", "EID", "NIG", "HII", "XIA", "DIG", "OID", "IIF", "FIG", "JIC", "GID", "EIL", "HIA", "VIH", "RIL", "QIG", "UIC", "NIH", "SII", "JIL", "MIK", "PIJ", "BID", "LIF", "HIJ", "KII", "DIF", "IIG", "OIE", "JID", "MIC", "PIB"));
    static HashSet<String> J_placements=new HashSet<>(Arrays.asList("HJD", "KJC", "IJA", "WJH", "RJG", "VJC", "OJH", "KJL", "NJK", "QJJ", "TJI", "BJE", "EJD", "IJJ", "DJG", "JJG", "KJD", "PJE", "NJC", "QJB", "TJA", "HJE", "EJL", "IJB", "LJA", "WJI", "RJH", "WJA", "OJI", "BJD", "TJJ", "NJL", "QJK", "OJA", "DJF", "IJK", "FJL", "KJE", "PJF", "JJH", "BJL", "GJI", "TJB", "NJD", "QJC", "HJF", "EJK", "IJC", "WJJ", "OJJ", "TJC", "WJB", "PJG", "QJL", "RJI", "UJH", "IJL", "NJE", "OJB", "DJE", "JJI", "QJD", "GJJ", "RJA", "BJK", "HJG", "KJF", "IJD", "JJA", "FJE", "GJB", "XJH", "OJK", "PJH", "WJC", "UJI", "OJC", "DJD", "NJF", "CJG", "JJJ", "GJK", "QJE", "HJH", "KJG", "UJA", "RJB", "DJL", "IJE", "FJF", "JJB", "GJC", "UJJ", "PJI", "UJB", "QJF", "AJD", "MJJ", "OJL", "JJK", "KJH", "PJA", "NJG", "CJF", "GJL", "HJI", "XJA", "IJF", "OJD", "DJK", "JJC", "FJG", "GJD", "HJA", "PJJ", "UJC", "QJG", "MJK", "RJL", "SJI", "VJH", "CJE", "KJI", "PJB", "JJL", "NJH", "HJJ", "EJG", "MJC", "LJF", "OJE", "AJK", "IJG", "KJA", "JJD", "GJE", "HJB", "VJI", "QJH", "VJA", "RJE", "NJI", "SJJ", "PJK", "CJD", "LJG", "BJG", "HJK", "KJJ", "EJF", "IJH", "NJA", "SJB", "OJF", "JJE", "MJD", "PJC", "CJL", "HJC", "KJB", "VJJ", "QJI", "VJB", "RJF", "TJH", "NJJ", "PJL", "BJF", "LJH", "QJA", "HJL", "KJK", "EJE", "OJG", "IJI", "NJB", "SJC", "CJK", "PJD", "JJF"));
    static HashSet<String> K_placements=new HashSet<>(Arrays.asList("HKF", "IKC", "WKJ", "RKI", "WKB", "OKJ", "UKH", "CKD", "QKL", "BKG", "RKA", "OKB", "EKF", "IKL", "KKF", "PKG", "JKI", "CKL", "GKJ", "TKC", "NKE", "QKD", "HKG", "IKD", "FKE", "JKA", "GKB", "XKH", "OKK", "WKC", "PKH", "UKI", "NKF", "BKF", "OKC", "EKE", "JKJ", "QKE", "GKK", "RKB", "CKK", "HKH", "KKG", "UKA", "IKE", "JKB", "FKF", "GKC", "OKL", "PKI", "BKE", "UKJ", "OKD", "EKD", "NKG", "PKA", "DKG", "JKK", "MKJ", "GKL", "QKF", "HKI", "KKH", "UKB", "XKA", "EKL", "IKF", "FKG", "JKC", "GKD", "HKA", "PKJ", "UKC", "QKG", "MKK", "RKL", "SKI", "VKH", "BKD", "JKL", "KKI", "PKB", "DKF", "NKH", "MKC", "HKJ", "IKG", "BKL", "LKF", "OKE", "EKK", "JKD", "KKA", "GKE", "HKB", "PKK", "QKH", "SKJ", "VKI", "KKJ", "PKC", "DKE", "NKI", "BKK", "HKK", "MKD", "RKE", "LKG", "OKF", "IKH", "SKB", "VKA", "KKB", "JKE", "NKA", "HKC", "VKJ", "QKI", "VKB", "RKF", "NKJ", "TKH", "PKL", "DKD", "QKA", "LKH", "CKG", "HKL", "KKK", "IKI", "NKB", "SKC", "OKG", "JKF", "PKD", "DKL", "HKD", "KKC", "IKA", "QKJ", "VKC", "RKG", "TKI", "NKK", "AKD", "WKH", "CKF", "QKB", "KKL", "OKH", "TKA", "IKJ", "NKC", "PKE", "DKK", "JKG", "LKA", "HKE", "KKD", "IKB", "WKI", "RKH", "WKA", "OKI", "NKL", "QKK", "TKJ", "CKE", "IKK", "OKA", "EKG", "JKH", "FKL", "KKE", "PKF", "AKK", "NKD", "QKC", "GKI", "TKB"));
    static HashSet<String> L_placements=new HashSet<>(Arrays.asList("HLH", "ILE", "FLF", "JLB", "GLC", "OLL", "PLI", "ALD", "ULJ", "NLG", "CLF", "OLD", "JLK", "MLJ", "PLA", "QLF", "GLL", "DLK", "HLI", "KLH", "ULB", "XLA", "ILF", "JLC", "FLG", "GLD", "HLA", "PLJ", "SLI", "VLH", "CLE", "RLL", "OLE", "ALK", "NLH", "PLB", "ELG", "JLL", "MLK", "LLF", "QLG", "KLI", "ULC", "HLJ", "ILG", "JLD", "MLC", "GLE", "HLB", "KLA", "PLK", "QLH", "SLJ", "VLI", "CLD", "BLG", "KLJ", "PLC", "ELF", "NLI", "HLK", "MLD", "RLE", "ILH", "SLB", "VLA", "CLL", "LLG", "OLF", "JLE", "KLB", "NLA", "HLC", "PLL", "TLH", "QLI", "BLF", "VLJ", "KLK", "PLD", "ELE", "NLJ", "QLA", "CLK", "HLL", "RLF", "LLH", "OLG", "ILI", "SLC", "VLB", "KLC", "JLF", "NLB", "HLD", "ILA", "QLJ", "VLC", "RLG", "NLK", "TLI", "WLH", "BLE", "ELD", "QLB", "DLG", "KLL", "ILJ", "NLC", "OLH", "TLA", "JLG", "PLE", "ELL", "LLA", "HLE", "KLD", "ILB", "QLK", "RLH", "BLD", "TLJ", "NLL", "WLI", "DLF", "QLC", "BLL", "OLI", "TLB", "ILK", "NLD", "FLL", "PLF", "ELK", "JLH", "WLA", "GLI", "HLF", "KLE", "OLA", "ILC", "WLJ", "RLI", "WLB", "OLJ", "ULH", "QLL", "DLE", "RLA", "ILL", "OLB", "JLI", "BLK", "KLF", "PLG", "NLE", "QLD", "GLJ", "TLC", "HLG", "ILD", "JLA", "FLE", "GLB", "WLC", "OLK", "ULI", "DLD", "CLG", "XLH", "RLB", "OLC", "KLG", "PLH", "ULA", "JLJ", "GLK", "DLL", "NLF", "QLE"));
    static ArrayList<HashSet> Placements=new ArrayList<>(Arrays.asList(A_placements,B_placements,C_placements,D_placements,E_placements,F_placements,G_placements,H_placements,I_placements,J_placements,K_placements,L_placements));







    /**
     * Determine whether a piece placement is well-formed according to the following:
     * - it consists of exactly three characters
     * - the first character is in the range A .. X
     * - the second character is in the range A .. L
     * - the third character is in the range A .. F if the second character is A, otherwise
     *   in the range A .. L
     *
     * @author Wei Wei
     * @param piecePlacement A string describing a piece placement
     * @return True if the piece placement is well-formed
     */
    static boolean isPiecePlacementWellFormed(String piecePlacement) {
        return piecePlacement.length() == 3 &&
                (       piecePlacement.charAt(0) >= 'A' &&
                        piecePlacement.charAt(0) <= 'X' &&
                        piecePlacement.charAt(1) > 'A' &&
                        piecePlacement.charAt(1) <= 'L' &&
                        piecePlacement.charAt(2) >= 'A' &&
                        piecePlacement.charAt(2) <= 'L' ||
                                piecePlacement.charAt(0) >= 'A' &&
                                piecePlacement.charAt(0) <= 'X' &&
                                piecePlacement.charAt(1) == 'A' &&
                                piecePlacement.charAt(2) >= 'A' &&
                                piecePlacement.charAt(2) <= 'F');
    }

    /**
     * Determine whether a placement string is well-formed:
     *  - it consists of exactly N three-character piece placements (where N = 1 .. 12);
     *  - each piece placement is well-formed
     *  - no piece appears more than once in the placement
     *
     * @author Wei Wei
     * @param placement A string describing a placement of one or more pieces
     * @return True if the placement is well-formed
     */
    static boolean isPlacementWellFormed(String placement) {
        int sublength = 3;
        if(placement != null && !placement.isEmpty()) {
            if (placement.length() % sublength != 0)
                return false;
            int length = placement.length() / sublength;
            String[] sublist = new String[length];
            String[] testlist = new String[length];
            for (int i = 0; i < length; i++) {
                sublist[i] = placement.substring(sublength * i, sublength * (i + 1));
                for (int j = i + 1; j < length; j++) {
                    testlist[j] = placement.substring(sublength * j + 1, sublength * j + 2);
                    if (sublist[i].substring(1, 2).equals(testlist[j])) {
                        return false;
                    }
                }
                if (!isPiecePlacementWellFormed(sublist[i])) {
                    return false;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Return a array of peg locations according to which pegs the given piece placement touches.
     * The values in the array should be ordered according to the units that constitute the
     * piece.
     * The code needs to account for the origin of the piece, the piece shape, and the piece
     * orientation.
     *
     * @author Wei Wei
     * @param piecePlacement A valid string describing a piece placement
     * @return An array of integers corresponding to the pegs which the piece placement touches,
     * listed in the normal order of units for that piece.   The value 0 corresponds to
     * peg 'A', 1 to peg 'B', etc.
     */
    static int[] getPegsForPiecePlacement(String piecePlacement) {
        ArrayList<Integer> peg_locations=new ArrayList<>();
        String[] sublist = new String[piecePlacement.length()/3];
        for(int position=0; position<piecePlacement.length()/3;position++) {
            sublist[position] = piecePlacement.substring(position * 3, (position + 1) * 3);
            int location_o = Character.getNumericValue(piecePlacement.charAt(0))-10;
            int o_column = location_o%6;
            int o_row = location_o/6;
            int l_a;//stands for the position of left above
            int l_b;//stands for the position of left below
            int r_a;//stands for the position of right above
            int r_b;//stands for the position of right below
            if (o_row%2==0){
                l_a = -7;
                l_b = 5;
                r_a = -6;
                r_b = 6;
            }
            else{
                l_a = -6;
                l_b = 6;
                r_a = -5;
                r_b = 7;
            }
            int[] positions = new int[3];
            positions[1] = location_o;
            if (sublist[position].charAt(1) >= 'A' && sublist[position].charAt(1) <= 'C') {
                switch (sublist[position].charAt(2)) {
                    case ('A'):
                    case ('G'):
                        positions[0] = location_o - 1;
                        positions[2] = location_o + 1;
                        break;
                    case ('B'):
                    case ('H'):
                        positions[0] = location_o + l_a;
                        positions[2] = location_o + r_b;
                        break;
                    case ('C'):
                    case ('I'):
                        positions[0] = location_o + r_a;
                        positions[2] = location_o + l_b;
                        break;
                    case ('D'):
                    case ('J'):
                        positions[0] = location_o + 1;
                        positions[2] = location_o - 1;
                        break;
                    case ('E'):
                    case ('K'):
                        positions[0] = location_o + r_b;
                        positions[2] = location_o + l_a;
                        break;
                    case ('F'):
                    case ('L'):
                        positions[0] = location_o + l_b;
                        positions[2] = location_o + r_a;
                        break;
                }
            }
            if (sublist[position].charAt(1) >= 'D' && sublist[position].charAt(1) <= 'H') {
                switch (sublist[position].charAt(2)) {
                    case ('A'):
                        positions[0] = location_o - 1;
                        positions[2] = location_o +r_a;
                        break;
                    case ('I'):
                        positions[2] = location_o - 1;
                        positions[0] = location_o +r_a;
                        break;
                    case ('B'):
                        positions[0] = location_o + l_a;
                        positions[2] = location_o + 1;
                        break;
                    case ('J'):
                        positions[2] = location_o + l_a;
                        positions[0] = location_o + 1;
                        break;
                    case ('C'):
                        positions[0] = location_o + r_a;
                        positions[2] = location_o + r_b;
                        break;
                    case ('K'):
                        positions[2] = location_o + r_a;
                        positions[0] = location_o + r_b;
                        break;
                    case ('D'):
                        positions[0] = location_o + 1;
                        positions[2] = location_o + l_b;
                        break;
                    case ('L'):
                        positions[2] = location_o + 1;
                        positions[0] = location_o + l_b;
                        break;
                    case ('E'):
                        positions[0] = location_o + r_b;
                        positions[2] = location_o - 1;
                        break;
                    case ('G'):
                        positions[2] = location_o + r_b;
                        positions[0] = location_o - 1;
                        break;
                    case ('F'):
                        positions[0] = location_o + l_b;
                        positions[2] = location_o + l_a;
                        break;
                    case ('H'):
                        positions[2] = location_o + l_b;
                        positions[0] = location_o + l_a;
                        break;
                }
            }
            if (sublist[position].charAt(1) >= 'I' && sublist[position].charAt(1) <= 'L') {
                switch (sublist[position].charAt(2)) {
                    case ('A'):
                        positions[0] = location_o - 1;
                        positions[1] = location_o;
                        positions[2] = location_o +l_a;
                        break;
                    case ('H'):
                        positions[2] = location_o - 1;
                        positions[1] = location_o;
                        positions[0] = location_o +l_a;
                        break;
                    case ('B'):
                        positions[0] = location_o + l_a;
                        positions[1] = location_o;
                        positions[2] = location_o + r_a;
                        break;
                    case ('I'):
                        positions[2] = location_o + l_a;
                        positions[1] = location_o;
                        positions[0] = location_o + r_a;
                        break;
                    case ('C'):
                        positions[0] = location_o + r_a;
                        positions[1] = location_o;
                        positions[2] = location_o + 1;
                        break;
                    case ('J'):
                        positions[2] = location_o + r_a;
                        positions[1] = location_o;
                        positions[0] = location_o + 1;
                        break;
                    case ('D'):
                        positions[0] = location_o + 1;
                        positions[1] = location_o;
                        positions[2] = location_o + r_b;
                        break;
                    case ('K'):
                        positions[2] = location_o + 1;
                        positions[1] = location_o;
                        positions[0] = location_o + r_b;
                        break;
                    case ('E'):
                        positions[0] = location_o + r_b;
                        positions[1] = location_o;
                        positions[2] = location_o + l_b;
                        break;
                    case ('L'):
                        positions[2] = location_o + r_b;
                        positions[1] = location_o;
                        positions[0] = location_o + l_b;
                        break;
                    case ('F'):
                        positions[0] = location_o + l_b;
                        positions[1] = location_o;
                        positions[2] = location_o - 1;
                        break;
                    case ('G'):
                        positions[2] = location_o + l_b;
                        positions[1] = location_o;
                        positions[0] = location_o - 1;
                        break;
                }
            }
            for(int i:positions){
                if (Math.abs(i%6-o_column)<=1){
                    peg_locations.add(i);
                }
                else{
                    peg_locations.add(-1);
                }
            }
        }
        int[]output =new int[peg_locations.size()];
        for(int i =0;i<peg_locations.size();i++){
            if(peg_locations.get(i)<0||peg_locations.get(i)>=24){
                output[i]=-1;
            }
            else{
                output[i]=peg_locations.get(i);
            }
        }
        return output;
    }

    /**
     * Determine whether the peg is out of bound or not.
     *
     * @author Wei Wei
     * @param value the test case value, pegPosition the peg position
     * @return True if the peg is outside the bound
      */
    static boolean isPegOutsideRange(int value, int pegPosition)
    {
        if((pegPosition == 5) || (pegPosition == 17) || (value == pegPosition + 1)) return true;
        else if((pegPosition == 11 || pegPosition == 23) && (value == pegPosition - 5 || value == pegPosition + 1 || value == pegPosition + 7)) return true;
        else if(value < 0 || value > 23) return true;
        else return false;
    }


    // FIXME Task 7: determine whether a placement is valid
    final static boolean[]PEGS_BALL=new boolean[24];
    final static boolean[][]PEGS_SURROUNDING=new boolean[24][6];
    final static boolean[]used_piece=new boolean[12];

    /**
     * Determine whether a placement is valid.  To be valid, the placement must be well-formed
     * and each piece must correctly connect with each other.
     *
     * @author Lei Huang
     * @param placement A placement string
     * @return True if the placement is valid
     */
    public static boolean isPlacementValid(String placement) {
//        setPieces();
        //Initialize PEGS_BALL and PEGS_RING;
        Arrays.fill(PEGS_BALL,false);
//        Arrays.fill(used_piece,false);
        for(int i=0;i<24;i++){
        Arrays.fill(PEGS_SURROUNDING[i],false);}

        //First judge whether the placement is well formed.
        if(!LinkGame.isPlacementWellFormed(placement)){return false;}

        //Break the placement into pieces(for each piece) and assign them into string array placements
        String[]placements=new String[placement.length()/3];
        for(int i=0;i<placement.length()/3;i++){
            placements[i]=placement.substring(i*3,(i+1)*3);}

        //test the validity of each piece
        for (String piece:placements) {
            //Use param piece_this to represent current piece.
            Piece piece_this=Piece.valueOf(Character.toString(piece.charAt(1)));
            int p=piece.charAt(1)-'A';
            //Use param orientation_this to represent current piece orientation.
            Orientation orientation_this=Orientation.valueOf(Character.toString(piece.charAt(2)));

            int[] positions=LinkGame.getPegsForPiecePlacement(piece);

            if (!Placements.get(p).contains(piece)){
                return false;
            }
//            //Judge whether the placement is out of bound.
//            for (int i:positions) {
//                if(i==-1)return false;}

//            //test whether the piece is used or not.
//            int piece_number=(int)(piece.charAt(1))-65;
//            if(!used_piece[piece_number]) {used_piece[piece_number]=true;}
//            else{return false;}


            //check whether the peg is occupied or not.
            for (int i=0;i<piece_this.units.length;i++) {
                if (Unit.Balls.contains(piece_this.units[i])) {
                    if (!PEGS_BALL[positions[i]]) {
                        PEGS_BALL[positions[i]] = true;
                    } else {
                        return false;
                    }
                }
            }
            //check whether the surrounding is occupied or not.
            piece_this.orientation(orientation_this);
            for (int i=0;i<piece_this.units.length;i++) {
                for(int j=0;j<6;j++) {
                    if (piece_this.units[i].surrounding_orientation[j]) {
                        if (!PEGS_SURROUNDING[positions[i]][j]) {
                            PEGS_SURROUNDING[positions[i]][j] = true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    final static boolean[]SOLUTION_PEGS_BALL=new boolean[24];
    final static boolean[][]SOLUTION_PEGS_SURROUNDING=new boolean[24][6];
    final static boolean[]SOLUTION_used_piece=new boolean[12];

    /**
     * Return an array of all solutions given a starting placement.
     *
     * @author Lei Huang
     * @param placement  A valid piece placement string.
     * @return An array of strings, each describing a solution to the game given the
     * starting point provied by placement.
     */
    static String[] getSolutions(String placement) {
        // FIXME Task 10: determine all solutions to the game, given a particular starting placement
        //Initialize PEGS_BALL, PEGS_RING and used_piece;
        Arrays.fill(SOLUTION_PEGS_BALL,false);
        Arrays.fill(SOLUTION_used_piece,false);
        for(int i=0;i<24;i++){
            Arrays.fill(SOLUTION_PEGS_SURROUNDING[i],false);}

        //First judge whether the placement is well formed.
        if(!LinkGame.isPlacementValid(placement)){
            System.out.println("Invalid input placement");
            return null;
        }

        //Break the placement into pieces(for each piece) and assign them into string array placements
        final int sublength=3;
        String[]placements=new String[placement.length()/sublength];
        for(int i=0;i<placement.length()/sublength;i++){
            placements[i]=placement.substring(i*sublength,(i+1)*sublength);}

        //Set the initial map situation for input placement
        for (String piece:placements) {
            //Use param piece_this to represent current piece.
            Piece piece_this=Piece.valueOf(Character.toString(piece.charAt(1)));
            //Use param orientation_this to represent current piece orientation.
            Orientation orientation_this=Orientation.valueOf(Character.toString(piece.charAt(2)));

            int piece_number=(int)(piece.charAt(1))-65;
            SOLUTION_used_piece[piece_number]=true;

            int[] positions=LinkGame.getPegsForPiecePlacement(piece);

            for (int i=0;i<piece_this.units.length;i++) {
                if (Unit.Balls.contains(piece_this.units[i])) {
                    SOLUTION_PEGS_BALL[positions[i]] = true;
                }
            }
            piece_this.orientation(orientation_this);
            for (int i=0;i<piece_this.units.length;i++) {
                for(int j=0;j<6;j++)
                    SOLUTION_PEGS_SURROUNDING[positions[i]][j] = true;
            }
        }

        //use a arraylist to instore the sub-solutions
        ArrayList<String>sub_solutions_0=new ArrayList<>();

        sub_solutions_0.add(placement);
        
        ArrayList<String>sub_solutions_1=FindNextSubSolutions(sub_solutions_0);
        ArrayList<String>sub_solutions_2=FindNextSubSolutions(sub_solutions_1);
        ArrayList<String>sub_solutions_3=FindNextSubSolutions(sub_solutions_2);
        ArrayList<String>sub_solutions_4=FindNextSubSolutions(sub_solutions_3);
        ArrayList<String>sub_solutions_5=FindNextSubSolutions(sub_solutions_4);
        ArrayList<String>sub_solutions_6=FindNextSubSolutions(sub_solutions_5);
        ArrayList<String>sub_solutions_7=FindNextSubSolutions(sub_solutions_6);
        ArrayList<String>sub_solutions_8=FindNextSubSolutions(sub_solutions_7);
        ArrayList<String>sub_solutions_9=FindNextSubSolutions(sub_solutions_8);
        ArrayList<String>sub_solutions_10=FindNextSubSolutions(sub_solutions_9);
        ArrayList<String>sub_solutions_11=FindNextSubSolutions(sub_solutions_10);

        ArrayList<String> Final_solutions=sub_solutions_11;

        String[]output=new String[Final_solutions.size()];
        for (int i = 0; i <Final_solutions.size(); i++) {
            output[i]=Final_solutions.get(i);
        }

        return output;
    }

    /**
     * Return an array of all valid placement of given placement and next piece.
     *
     * @author Lei Huang
     * @param placement_nextPiece  The String array contains the current placement
     *                             and the piece need to be searched for valid placement.
     * @return An array of strings, each describing a valid placements of original placement
     *         and possible placement for next piece.
     */
    static ArrayList<String> FindNextValidPieces(String[] placement_nextPiece){
            String placement=placement_nextPiece[0];
            String nextPiece=placement_nextPiece[1];
            int next=nextPiece.charAt(0)-'A';
            ArrayList<String>output=new ArrayList<>();

            //Initialize PEGS_BALL and PEGS_RING;
            Arrays.fill(PEGS_BALL,false);
            Arrays.fill(used_piece,false);
            for(int i=0;i<24;i++){
                Arrays.fill(PEGS_SURROUNDING[i],false);}

            //Break the placement into pieces(for each piece) and assign them into string array placements
            String[]placements=new String[placement.length()/3];
            for(int i=0;i<placement.length()/3;i++){
                placements[i]=placement.substring(i*3,(i+1)*3);}

            for (String piece:placements) {
                //Use param piece_this to represent current piece.
                Piece piece_this=Piece.valueOf(Character.toString(piece.charAt(1)));
                //Use param orientation_this to represent current piece orientation.
                Orientation orientation_this=Orientation.valueOf(Character.toString(piece.charAt(2)));

                int[] positions=LinkGame.getPegsForPiecePlacement(piece);

                for (int i=0;i<3;i++) {
                    if (Unit.Balls.contains(piece_this.units[i])) {
                        PEGS_BALL[positions[i]] = true;
                    }
                }

                piece_this.orientation(orientation_this);
                for (int i=0;i<3;i++) {
                    for(int j=0;j<6;j++) {
                        if (piece_this.units[i].surrounding_orientation[j]) {
                            PEGS_SURROUNDING[positions[i]][j] = true;
                        }
                    }
                }
            }
        for (Object test_sub:Placements.get(next)) {
            if (isNextPiecePlacementValid(test_sub.toString())) {
                output.add(placement+test_sub);
            }
        }
        return output;
    }

    /**
     * The function that judge whether the input piece placement is valid or not on existing placement
     *
     * @author Lei Huang
     * @param test_sub  The string that represents the piece placement being tested.
     * @return A boolean represents whether the next piece placement is valid or not.
     *         True means the piece placement is valid.
     *         False means the piece placment is invalid.
     */

    static boolean isNextPiecePlacementValid(String test_sub){

            //Use param piece_this to represent current piece.
            Piece piece_this=Piece.valueOf(Character.toString(test_sub.charAt(1)));
            //Use param orientation_this to represent current piece orientation.
            Orientation orientation_this=Orientation.valueOf(Character.toString(test_sub.charAt(2)));

            int[] positions=LinkGame.getPegsForPiecePlacement(test_sub);

//            for (int i:positions) {
//                if(i==-1)return false;}

            for (int i=0;i<piece_this.units.length;i++) {
                if (Unit.Balls.contains(piece_this.units[i])) {
                    if (PEGS_BALL[positions[i]]) {
                        return false;
                    }
                }
            }

            piece_this.orientation(orientation_this);
            for (int i=0;i<piece_this.units.length;i++) {
                for(int j=0;j<6;j++) {
                    if (piece_this.units[i].surrounding_orientation[j]) {
                        if (PEGS_SURROUNDING[positions[i]][j]) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }

    /**
     * The function that judge whether the game has finished or not(finished means all pieces have placed on board correctly).
     *
     * @author Lei Huang
     * @param placement  The string that represents the placement being tested.
     * @return A boolean represents whether piece placement has used all pieces or not.
     *         True means the piece placement has finished.
     *         False means the piece placment has not finished.
     */
    public static boolean isPlacementComplete(String placement){
        return (placement.length()/3)==12;
    }
    static boolean isAllPieceUsed(){
        boolean output=true;
        for (boolean i: SOLUTION_used_piece) {
            if (!i)
                output=false;
        }
        return output;
    }

    static ArrayList<String> FindNextSubSolutions(ArrayList<String>subsolution){
        ArrayList<String>NextSubsolutions=new ArrayList<>();
        int current_piece = 0;
        for (String solution:subsolution) {
            if(isAllPieceUsed()){
                NextSubsolutions=subsolution;
                break;
            }

            for (int i = 0; i < 12; i++) {
                if(!SOLUTION_used_piece[i]) {
                    current_piece = i;
                    String[]placement_nextPiece=new String[2];
                    placement_nextPiece[0]=solution;
                    placement_nextPiece[1]=""+(char)('A'+i);

                    for (String j:FindNextValidPieces(placement_nextPiece)) {
                        NextSubsolutions.add(j);
                    }
                    break;
                }

            }
        }
        SOLUTION_used_piece[current_piece]=true;
        return NextSubsolutions;
    }
    //
}
