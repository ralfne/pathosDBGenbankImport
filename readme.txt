ftp://ftp.ncbi.nih.gov/genbank/gbrel.txt:

152. gbbct99.seq - Bacterial sequence entries, part 99.
153. gbchg.txt - Accession numbers of entries updated since the previous release.
154. gbcon1.seq - Constructed sequence entries, part 1.
440. gbdel.txt - Accession numbers of entries deleted since the previous release.
441. gbenv1.seq - Environmental sampling sequence entries, part 1.
517. gbest1.seq - EST (expressed sequence tag) sequence entries, part 1.
994. gbgss1.seq - GSS (genome survey sequence) sequence entries, part 1.
1281. gbhtc1.seq - HTC (high throughput cDNA sequencing) sequence entries, part 1.
1437. gbinv1.seq - Invertebrate sequence entries, part 1.
1561. gbmam1.seq - Other mammalian sequence entries, part 1.
1570. gbnew.txt - Accession numbers of entries new since the previous release.
1571. gbpat1.seq - Patent sequence entries, part 1.
1786. gbphg2.seq - Phage sequence entries, part 2.
1787. gbpln1.seq - Plant sequence entries (including fungi and algae), part 1.
1876. gbpri1.seq - Primate sequence entries, part 1.
1924. gbrel.txt - Release notes (this document).
1925. gbrod1.seq - Rodent sequence entries, part 1.
1956. gbsts1.seq - STS (sequence tagged site) sequence entries, part 1.
1976. gbsyn1.seq - Synthetic and chimeric sequence entries, part 1.
1984. gbtsa1.seq - TSA (transcriptome shotgun assembly) sequence entries, part 1.
2140. gbuna1.seq - Unannotated sequence entries, part 1.
2141. gbvrl1.seq - Viral sequence entries, part 1.
2173. gbvrt1.seq - Other vertebrate sequence entries, part 1.



The detailed format for the LOCUS line format is as follows:

Positions  Contents
---------  --------
01-05      'LOCUS'
06-12      spaces
13-28      Locus name
29-29      space
30-40      Length of sequence, right-justified
41-41      space
42-43      bp
44-44      space
45-47      spaces, ss- (single-stranded), ds- (double-stranded), or
           ms- (mixed-stranded)
48-53      NA, DNA, RNA, tRNA (transfer RNA), rRNA (ribosomal RNA), 
           mRNA (messenger RNA), uRNA (small nuclear RNA).
           Left justified.
54-55      space
56-63      'linear' followed by two spaces, or 'circular'
64-64      space
65-67      The division code
68-68      space
69-79      Date, in the form dd-MMM-yyyy (e.g., 15-MAR-1991)

Legal values for the division code include:

PRI - primate sequences
ROD - rodent sequences
MAM - other mammalian sequences
VRT - other vertebrate sequences
INV - invertebrate sequences
PLN - plant, fungal, and algal sequences
BCT - bacterial sequences
VRL - viral sequences
PHG - bacteriophage sequences
SYN - synthetic sequences
UNA - unannotated sequences
EST - EST sequences (Expressed Sequence Tags) 
PAT - patent sequences
STS - STS sequences (Sequence Tagged Sites) 
GSS - GSS sequences (Genome Survey Sequences) 
HTG - HTGS sequences (High Throughput Genomic sequences) 
HTC - HTC sequences (High Throughput cDNA sequences) 
ENV - Environmental sampling sequences
CON - Constructed sequences
TSA - Transcriptome Shotgun Assembly sequences
