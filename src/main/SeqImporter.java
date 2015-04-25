/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Genbank.Parser;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ralfne
 */
public class SeqImporter {
    public final static String NEWLINE=System.getProperty("line.separator");
    public final static String FILESEPARATOR=System.getProperty("file.separator");

    public static void main(String[] args) {
        try {
            HashMap<String, String> arguments=getCMDarguments(args);

            if(arguments.isEmpty()){
                System.out.println("intrepid usage:");
                System.out.println("-in <input directory>");
                System.out.println("-ext <genbank file extension>");
                System.out.println("-maxSeqLength <max length of sequence>");
                System.out.println("-out <output filename>");
                System.out.println("-speciesNamesFile <filename of unique species list>");
                return;
            }
            
            String inFolder=arguments.get("-in");
            String extension=arguments.get("-ext");
            String maxLengthS=arguments.get("-maxSeqLength");
            String outFile=arguments.get("-out");
            String speciesFile=arguments.get("-speciesNamesFile");
            
            ArrayList<String> keywords=new ArrayList<String>();
            //keywords.add("SS");
            keywords.add("18S");
            //keywords.add("ribosomal RNA");
            
            int maxLength=-1;
            if(maxLengthS!=null){
                maxLength=Integer.parseInt(maxLengthS);
            }            
            Parser parser=new Parser(keywords, maxLength, speciesFile);
            parser.parseFolder(inFolder, extension, outFile);
            
        } catch (Exception e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
    }
   
    private static HashMap<String,String> getCMDarguments(String[] args){
        HashMap<String,String> parsedArgs=new HashMap<String, String>();
        int i=0;
        while(i<args.length){
            String id=args[i];i++;
            String value=args[i];i++;
            parsedArgs.put(id,value);            
        }
        return parsedArgs;
    }
    
}
