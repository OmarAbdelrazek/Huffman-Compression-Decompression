package huffman;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Compress {
	private static HashMap<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();
	private static HashMap<Integer, String> symToCode = new HashMap<Integer, String>();
	private static HashMap<String, Integer> codeToSym = new HashMap<String, Integer>();
	private static PriorityQueue<HuffmanNode> priorityQ;
	static int nodeCount = 0;
	public Compress(String path) throws Exception{
	    long startTime = System.currentTimeMillis();
		File file = new File(path);
		compressFile(file);
	    long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
		System.out.println("Character\tFrequency");
		frequencyMap.entrySet().forEach(entry->{
		System.out.println(Character.toString((char)((int)entry.getKey())) +"\t\t"+ entry.getValue());
		});
		System.out.println("========================");
		System.out.println("Byte\t\tCode\t\tNew Code");
		symToCode.entrySet().forEach(entry->{
			System.out.println(entry.getKey()+"\t\t"+Integer.toBinaryString(entry.getKey())+"\t\t"+ entry.getValue());
		});
		System.out.println("========================");
		System.out.println("Compressing Time = "+elapsedTime+"ms");
		File originalFile = new File(path);
		File compressedFile = new File((path+".cmp"));
		double compressionRatio = (double)(compressedFile.length())/(double)(originalFile.length())*100;
		System.out.println("Compression Ratio: "+compressionRatio+"%");
	}
	private static void fileEmpty(File f) throws IOException
	{
		String str = f+".cmp";
		BufferedOutputStream ewrite = new BufferedOutputStream(new FileOutputStream(str));
		ewrite.close();
		f.delete();
		System.exit(0);
	}
	private static void compressFile(File file) throws IOException
	{
		byte[] byteStream= displayByte(file);
		buildTree(frequencyMap);
		int f =setPrefixcodes();
		HuffmanNode node = priorityQ.peek();
		generatePrefixcodes(node ,"");
		mapingCodes(node);
		realCompress(byteStream,file,f);
	}
	
	private static void realCompress(byte[] byteStream,File file, int freq) throws IOException
	{	
		String st="";
		StringBuilder comp = new StringBuilder();
		StringBuilder code = new StringBuilder();
		String test = codeToSym.toString();
		System.out.println("Code to SYMMM"+test);
		test=test.replace(", "," ");
		test= test.substring(1, test.length()-1);
		comp.append(test);
		comp.append('\n');
		comp.append(freq);
		comp.append('\n');
		
		BufferedOutputStream br = null;
		for(int i =0; i<byteStream.length;i++)
		{
		 int data = byteStream[i];
		code.append(symToCode.get(data));
		}
		
		br = new BufferedOutputStream(new FileOutputStream(file.toString()+".cmp"));
		for(int j=0;j<comp.length();j++)
		{
			char a = comp.charAt(j);
			br.write(a);
			
		}
		for(int k =0; k<code.length();k++)
		{
			 st=st+code.charAt(k);
					
			if(st.length()==8)
			{
				int c = Integer.parseInt(st,2);
				//System.out.println(st+" "+c);
				br.write(c);
				st="";	
			}
		}
		if(st!="")
		{
			String formatted = (st+"00000000").substring(0,8);
			int format= Integer.parseInt(formatted,2);
			br.write(format);

		}
		br.close();
	}
	
	private static void mapingCodes(HuffmanNode node)
	{
		if(node.left!=null)
		{
			mapingCodes(node.left);
		}
		if(node.right!=null)
		{
			mapingCodes(node.right);
		}
		if (node.left ==null && node.right == null)
		{
			symToCode.put(node.symbol, node.code);
			codeToSym.put(node.code, node.symbol);
		}		
	}
	
	private static void generatePrefixcodes(HuffmanNode node, String codes)
	{
		if(node.left!=null)
		{
			node.left.code=node.code+"0";
			generatePrefixcodes(node.left, node.left.code);
			node.right.code=node.code+"1";
			generatePrefixcodes(node.right, node.right.code);
		}
	}
	
	private static int setPrefixcodes()
	{
	
		for (int w=0;w<nodeCount-1;w++)
		{
		HuffmanNode parent = new HuffmanNode(0);
		HuffmanNode leftNode = priorityQ.poll();
		HuffmanNode rightNode = priorityQ.poll();
		parent.setLeft(leftNode);
		parent.setRight(rightNode);
		parent.frequency = leftNode.getFrequency()+ rightNode.getFrequency();
		parent.symbol = leftNode.getSymbol() + rightNode.getSymbol();
		leftNode.code="0";
		rightNode.code="1";
		priorityQ.add(parent);
		}	
		HuffmanNode freq = priorityQ.peek();
		int finalfreq = freq.getFrequency();
		return finalfreq;
	}
	
	private static void getFrequency(byte[] bye,File file) throws IOException
	{
		if(file.length()==0)
		{
			fileEmpty(file);
			
			
		}
		for (int i=0; i<bye.length;i++)
		{
			int value = bye[i];

			if (!frequencyMap.containsKey(value))
			frequencyMap.put(value,1);
			else
			frequencyMap.put(value,frequencyMap.get(value)+1);
			}		
	}
	
	private static void buildTree(HashMap<Integer,Integer> frequencyMap)
	{
		priorityQ = new PriorityQueue<HuffmanNode>();
	for (int i : frequencyMap.keySet())
	{
		HuffmanNode e = new HuffmanNode(i,frequencyMap.get(i));
		nodeCount++;
		priorityQ.add(e);

	}
	}
	
	private static byte[] displayByte(File file)
	{
		FileInputStream fis = null;
		int i = (int) file.length();
		byte[] bye = new byte[i];
		try
		{
			fis = new FileInputStream(file);
			fis.read(bye);
			getFrequency(bye,file);
			
		}
			catch (Exception e) {
				System.out.println("File Not found in the path..");
			}
		return bye;		
	}
	
}
