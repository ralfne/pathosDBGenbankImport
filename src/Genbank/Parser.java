/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Genbank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import main.SeqImporter;

/**
 *
 * @author ralfne
 */
public class Parser {
    private BufferedWriter m_Writer=null;
    private ArrayList<String> m_Keywords=new ArrayList<String>();
    private int m_MaxSeqLength=-1;
    private HashMap<String,String> m_Species=null;
    private String m_SpeciesFilename="";
    
    public Parser(ArrayList<String> keywords, int maxSeqLength, String speciesOutFile){
        m_Keywords=keywords;
        m_MaxSeqLength=maxSeqLength;
        m_SpeciesFilename=speciesOutFile;
    }
    
    public void parseFolder(String inFolder,String extension, String outFilename)throws Exception{
        try {
            if(m_SpeciesFilename!=null){ m_Species=new HashMap<String, String>(); }
            File folder=new File(inFolder);
            m_Writer=new BufferedWriter(new FileWriter(outFilename));
            for(File f : folder.listFiles()){
                if(!f.isDirectory()){
                    if(f.getAbsolutePath().endsWith(extension)){
                        System.out.println("Parsing file " + f.getAbsolutePath());
                        parseFile(f.getAbsolutePath(), outFilename);
                    }
                }
            }
            m_Writer.flush();
            m_Writer.close();
            possiblyWriteSpeciesNames();
            System.out.println("Done!");
        } catch (Exception e) {
            String eS=e.getMessage();
            throw new Exception("Error in parseFolder - " + SeqImporter.NEWLINE + eS);
        }
    }
    
    private void possiblyWriteSpeciesNames()throws Exception{
        BufferedWriter bw=null;
        try {
            if(m_Species!=null){
                System.out.print("writing species names...");
                bw=new BufferedWriter(new FileWriter(m_SpeciesFilename));
                Iterator<String> iter=m_Species.keySet().iterator();
                while(iter.hasNext()){
                    String key=iter.next();
                    bw.write(key);
                    bw.newLine();
                }
                bw.flush();
            }
        } catch (Exception e) {
            throw e;
        } finally{
            if(bw!=null){ bw.close(); }
        }
    }
    
    public void parseFile(String inFilename, String outFilename)throws Exception{
        BufferedReader br=null;
        try {
            br=new BufferedReader(new FileReader(inFilename));        
            String line="";
            ArrayList<String> rec=null;
            int counter=0;
            while((line=br.readLine())!=null){
                if(rec==null){
                    if(line.startsWith("LOCUS       ")){
                        rec=new ArrayList<String>();
                        rec.add(line);
                    }
                }else{
                    if(line.startsWith("//")){
                        parseRecord(rec);
                        rec=null;
                        counter++;
                    }else{
                        rec.add(line);
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Error in parseFile: " + inFilename  + SeqImporter.NEWLINE + e.getMessage());
        } finally{
            if(br!=null){ br.close(); }
        }    
    }
    
    private void parseRecord(ArrayList<String> rec)throws Exception{
        try{
            if(isRibosomalSeq(rec)){
                writeFastaRecord(rec);
            }               
        }catch(Exception e){
            if(rec==null){
                throw new Exception("Error in parseRecord; rec==null");
            }else{
                if(rec.isEmpty()){
                    throw new Exception("Error in parseRecord; rec.isEmpty()");
                }else{
                    throw new Exception("Error in parseRecord; rec = " + rec.get(0));
                }
            }
        } 
    }
    
    private boolean isRibosomalSeq(ArrayList<String> rec){
        ArrayList<String> keywordsSection=getRecordSection(rec, "KEYWORDS");
        if(keywordsSection==null){
            String sd="";
        }
        boolean b=sectionContainsText(keywordsSection, m_Keywords);
        if(b){ return true; }
        ArrayList<String> featuresSection=getRecordSection(rec, "FEATURES");
        if(keywordsSection==null){
            String sd="";
        }
        b=sectionContainsText(featuresSection, m_Keywords);
        if(b){ return true; }
        return false;
    }
    
    private boolean sectionContainsText(ArrayList<String> section, ArrayList<String> texts){
        Iterator<String> iter=texts.iterator();
        while(iter.hasNext()){
            String text=iter.next();
            if(sectionContainsText(section, text)){
                return true;
            }
        }
        return false;
    }
    
    private boolean sectionContainsText(ArrayList<String> section, String text){
        Iterator<String> iter=section.iterator();
        while(iter.hasNext()){
            String line=iter.next();
            if(line.contains(text)){
                return true;
            }
        }
        return false;
    }
    
    private ArrayList<String> getRecordSection(ArrayList<String> rec, String id){
        ArrayList<String> section=null;
        int i=id.lastIndexOf(" ");
        Iterator<String> iter=rec.iterator();
        while(iter.hasNext()){
            String line=iter.next();
            if(section==null){
                if(line.startsWith(id)){
                    section=new ArrayList<String>();
                    section.add(line);
                }
            }else{
                if(line.substring(i+1,i+2).trim().isEmpty()){
                    section.add(line);
                }else{
                    break;
                }
            }
        }
        return section;
    }
    
    private void writeFastaRecord(ArrayList<String> rec)throws Exception{
        String fastaRec=getRecordAsFasta(rec);
        if(fastaRec!=null){
            m_Writer.write(fastaRec);
            m_Writer.newLine();
        }
    }
    
    private String getRecordAsFasta(ArrayList<String> rec){
        StringBuilder sb=new StringBuilder();
        ArrayList<String> org=getRecordSection(rec,"  ORGANISM");
        String orgS=org.get(0);
        orgS=orgS.substring(12).trim();
        String locus=rec.get(0).substring(12,20).trim();
        sb.append(">").append(locus).append("|").append(orgS).append(SeqImporter.NEWLINE);
        
        ArrayList<String> seqArray=getRecordSection(rec,"ORIGIN");
        String seq=getSequence(seqArray);
        if(m_MaxSeqLength>0){
            if(seq.length()>m_MaxSeqLength){
                return null;
            }
        }
        possiblyRegisterSpecies(orgS);
        sb.append(seq);
        
        return sb.toString();
    }
    
    private void possiblyRegisterSpecies(String species){
        if(m_Species!=null){
            String s=m_Species.get(species);
            if(s==null){
                m_Species.put(species, species);
            }
        }
    }
    
    private String getSequence(ArrayList<String> seqLines){
        StringBuilder sb=new StringBuilder();
        Iterator<String> iter=seqLines.iterator();
        while(iter.hasNext()){
            String line=iter.next();
            if(!line.startsWith("ORIGIN")){
                line=line.substring(10);
                line=line.replace(" ","");
                sb.append(line);
            }
        }
        return sb.toString();
    }
    
}

