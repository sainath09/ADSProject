import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
class huffManTree{
	decoderNode root;
	public huffManTree() {
		// TODO Auto-generated constructor stub
		root=new decoderNode();
	}
	void decodeTree(ArrayList<decoderNode> decoderNode){
		decoderNode node=this.root;
		for(int i=0;i<decoderNode.size();i++){
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
		Decoder d=new Decoder();
		d.decode(BINFILE,hft.root);
	}

	private void decode(String bINFILE, decoderNode root) throws IOException {
		// TODO Auto-generated method stub
		FileInputStream fis=new FileInputStream(bINFILE);
		System.out.println(fis.available());
		int content;
		while ((content = fis.read()) != -1) {
			// convert to char and display it
			System.out.print((char) content);
		}
		
	}

}
