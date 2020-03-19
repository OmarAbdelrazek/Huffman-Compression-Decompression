package huffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DecompressFolder {

	public DecompressFolder(File path) throws IOException {
	    long startTime = System.currentTimeMillis();
		Decompression.mapping(path);
		String dirPath = createFolder(path);
		DecompressingFolder(dirPath,path);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
	    System.out.println("========================");
		System.out.println("Compressing Time = "+elapsedTime+"ms");
	}
	public String createFolder(File path) {
		
		Path p = Paths.get(path.getAbsolutePath());
		String folderNameFull = p.getFileName().toString();
		String folderName = folderNameFull.split("\\.")[0];
		String fullPath = path.getAbsoluteFile().getParent() +"/"+folderName;
		System.out.println(folderName);
		File directory = new File(fullPath);
	      //Creating the directory
	      boolean bool = directory.mkdir();
	      if(bool){
	         System.out.println("Directory created successfully");
	      }else{
	         System.out.println("Sorry couldn’t create specified directory");
	      }
	      return fullPath;
	}
	public void DecompressingFolder(String output,File input) {
		String decompressedFolder = (input.getAbsolutePath()).substring(0,(input.getAbsolutePath()).length()-4);
		System.out.println(decompressedFolder);
		 BufferedReader br = null;
		    PrintWriter pw = null;
		    File source = new File(decompressedFolder);
		    try {
		         br = new BufferedReader(new FileReader(source));
		         //System.out.println(br.readLine());
		         //pw =  new PrintWriter(new FileWriter(dest));
		         String line;
		         while ((line = br.readLine()) != null) {
		        	 if(line.startsWith("$$")) {
		        		 output = line.substring(2, line.length());
		        		 System.out.println(output);
		        		 File dest = new File(output);
		        		 pw =  new PrintWriter(new FileWriter(dest));
		        		 continue;
		        	 }
		                pw.println(line);
		                pw.flush();
		                //Thread.sleep(1000);
		         }
		         br.close();
		         pw.close();
		    }catch (Exception e) {
		         e.printStackTrace();
		    }		
	}
	
	
	

}
