import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class decoderNode{
	int data;
	String code;
	decoderNode left;
	decoderNode right;
	public decoderNode() {
		// TODO Auto-generated constructor stub
		data=-1;
		code="";
		left=null;
		right=null;
	}
}
class decoderTest{
	
}
class huffManTree{
	decoderNode root;
	
	public huffManTree() {
		// TODO Auto-generated constructor stub
		root=new decoderNode();
	}
	
	void decodeTree(ArrayList<decoderNode> decoderNode){
		//decoderNode node=this.root;
		for(int i=0;i<decoderNode.size();i++){
			decoderNode node=this.root;
			String code=decoderNode.get(i).code;
			//System.out.println(code+" "+i+" "+code.length());
			for(int j=0;j<code.length();j++){
				if(code.charAt(j)=='0'){
					if(node.left == null)  node.left = new decoderNode();
					node=node.left; 
				}
				else {
					if(node.right == null) node.right=new decoderNode();
					node=node.right; 
				}
			}
			node.data=decoderNode.get(i).data;
		}
	}
	
	void decoder(String BINFILE) throws IOException{
		FileInputStream fis=new FileInputStream(BINFILE);
		String decoderWriter="/home/kps/workspace/ADSProject/src/decoded.txt";
		FileWriter fw = new FileWriter(decoderWriter);
		BufferedWriter bw = new BufferedWriter(fw);
		byte[] buffer=null;
		buffer=new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		//System.out.println(buffer.length);
//		for(int i=0;i<buffer.length;i++){
//			System.out.println(buffer[i]);	
//		}
		
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<buffer.length;i++){
			byte b1 = buffer[i];
			String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
			sb.append(s1);
		}
		//System.out.println("SB LENGTH"+sb.length());
		decoderNode temp=this.root;
		long l=sb.length();
		for(int i = 0; i <= l; i++){
			//System.out.println(i+" "+l);
			if(temp.data != -1){
				StringBuilder content = new StringBuilder();
				content.append(temp.data);
				content.append("\n");
				bw.write(content.toString());
				//System.out.println(temp.data);
				temp=this.root;
				i--;
			}
			else if (i!=l){
				if(sb.charAt(i)=='0') temp=temp.left;
				else temp=temp.right;
			}
		}
	bw.close();
	}
}

public class Decoder {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String BINFILE="/home/kps/workspace/ADSProject/src/encoded.bin";
		String CODETABLE="/home/kps/workspace/ADSProject/src/code_table.txt";
		FileReader fr=new FileReader(CODETABLE);
		BufferedReader br=new BufferedReader(fr);
		String S=new String();
		ArrayList<decoderNode> decoder=new ArrayList<decoderNode>();
		while((S=br.readLine())!=null){
			decoderNode dn = new decoderNode();
			String[] sSplit=S.split(" "); 
			//System.out.println(sSplit[0]+"jvf"+sSplit[1]);
			dn.data=Integer.parseInt(sSplit[0]);
			dn.code=sSplit[1];
			decoder.add(dn);
		}
		br.close();
		huffManTree hft=new huffManTree();
		hft.decodeTree(decoder);
		//String s="000000000000001111100000000000000111110000000000000011111";
		long startTime = System.currentTimeMillis();
		hft.decoder(BINFILE);
		long stopTime = System.currentTimeMillis();
		System.out.println("time for decode:"+(stopTime-startTime)+" MilliSec");
		
	}
}
