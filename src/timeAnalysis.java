import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class binaryHeap{
	int left(int i){
		return (2*i)+1;
	}
	int right(int i){
		return (2*i)+2;
	}
	int parent(int i){
		return (i/2) - 1;
	}
	void heapify(ArrayList<node> l,int i,int m){
		int le=left(i);
		int re=right(i);
		int min=i;
		
		if(le<m && l.get(le).getFreq()<l.get(min).getFreq()) min=le;
		if(re<m && l.get(re).getFreq()<l.get(min).getFreq()) min=re;
		if(min!=i){
			Collections.swap(l,i,min);
			this.heapify(l,min,m);
		}
	}
	node extract_min(ArrayList<node> l){
		node temp=l.get(0);
		Collections.swap(l,0,l.size()-1);
		l.remove(l.size()-1);
		this.heapify(l,0,l.size());
		return temp;
	}
	void buildHeap(ArrayList<node> l, int n){
		for(int i=(n/2)-1;i>=0;i--){
			this.heapify(l,i,n);
		}	
	}
	void build_tree_using_binary_heap(ArrayList<node> l){
		ArrayList<node> temp=new ArrayList<node>(l);
		this.buildHeap(temp,temp.size());
		node root=new node();
		while(temp.size()>1){
			node n1=this.extract_min(temp);
			node n2=this.extract_min(temp);
			node n3=new node();
			n3.data=-1;
			n3.freq=n1.freq+n2.freq;
			n3.left=n1;
			n3.right=n2;
			temp.add(n3);
			int i=temp.size();
			while(i>1 && temp.get(parent(i)).getFreq() > temp.get(i-1).getFreq() ){
				Collections.swap(temp, i-1, parent(i));
				i=parent(i);
			}
			if(temp.size()==1) root=n3;
			
		}
		//this.genCodeTable(root);
	}
	void genCodeTable(node root){
		nodeHuffman nH=new nodeHuffman();
		ArrayList<nodeHuffman> codeTable = new ArrayList<nodeHuffman>();
		nH.treeTrav(root, nH.Hcode, codeTable);		
	}
}

class pairingHeapNode{
	node phNode;
	ArrayList<pairingHeapNode> children;
	pairingHeapNode(){
		phNode=new node();
		children=new ArrayList<pairingHeapNode>(); 
	}
	
}
class pairingHeap{
	pairingHeapNode root;
	int size;
   public pairingHeap() {
	// TODO Auto-generated constructor stub
	   root=null;
	   size=0;
   }
   void addelement(pairingHeapNode toAdd){
	   if(root==null) root=toAdd;
	   else if(root.phNode.getFreq() < toAdd.phNode.getFreq()){
		   root.children.add(toAdd);
	   }
	   else{
		   toAdd.children.add(root);
		   root=toAdd;
	   }
	   size++;
   }
   node extractMin(){
	   //System.out.println("test");
	   node minN=root.phNode;
	   ArrayList<pairingHeapNode> temp=new ArrayList<pairingHeapNode>();
	   for(int i=root.children.size()-1;i>0;i=i-2){
		   if(root.children.get(i).phNode.getFreq() > root.children.get(i-1).phNode.getFreq()){
			   temp.add(root.children.get(i-1));
			   root.children.get(i-1).children.add(root.children.get(i));
		   }
		   else{
			   temp.add(root.children.get(i));
			   root.children.get(i).children.add(root.children.get(i-1));
		   }
		   root.children.remove(root.children.size()-1);
		   root.children.remove(root.children.size()-1);
	   }
	   if(root.children.size()>0){
		   temp.add(root.children.get(root.children.size()-1));
		   root.children.remove(root.children.size()-1);
	   }
	   pairingHeapNode temproot = null;
	   //System.out.println(temp.size());
	   if(temp.size() > 0){
		   temproot=temp.get(temp.size()-1);
		   for(int i=temp.size()-2;i>=0;i--){
			   if(temp.get(i).phNode.getFreq() > temproot.phNode.getFreq()) 
				   temproot.children.add(temp.get(i));
			   else{
				   temp.get(i).children.add(temproot);
				   temproot=temp.get(i);
			   }
		   }
	   }
	   this.root=temproot;
	   size--;
	   return minN;	   
   }
   void build_tree_using_pairing_heap(ArrayList<node> l){
	   
	   node n1=new node();
	   node n2=new node();
	   
	   
	   for(int i=0;i<l.size();i++){
		   node temp=new node();
		   pairingHeapNode phNodeTemp=new pairingHeapNode();
		   temp.data=l.get(i).getData();
		   temp.freq=l.get(i).getFreq();
		   phNodeTemp.phNode=temp;
		   this.addelement(phNodeTemp);
		   
	   }
	   
	   //System.out.println("size:"+size+" list size"+l.size());
	   while(size>1){
		   pairingHeapNode ph=new pairingHeapNode();
		   node n3=new node();
		   //System.out.println("size:"+size);
		   n1=this.extractMin();
		   n2=this.extractMin();
		   long freq=n1.getFreq()+n2.getFreq();
		   n3.freq=freq;
		   n3.data=-1;
		   ph.phNode=n3;
		   this.addelement(ph);
	   }
	   
	   
   }
   
}

public class timeAnalysis {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		String INPUTFILE=args[0];
		Map<Integer, Long> fre=new HashMap<Integer, Long>();
		FileReader fr=null;
		try {	
			fr=new FileReader(INPUTFILE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<node> l=new ArrayList<node>();
		BufferedReader br=new BufferedReader(fr);
		String S=new String();
		while((S=br.readLine())!=null){
			int temp=Integer.parseInt(S);
			if(fre.containsKey(temp)) fre.put(temp, (long)fre.get(temp)+1);
			else fre.put(temp, (long)1);
		}
		Iterator it=fre.entrySet().iterator();
		while(it.hasNext()){
			node n=new node();
			Map.Entry temp=(Map.Entry)it.next();
			n.data=(int)temp.getKey();
			n.freq=(long)temp.getValue();
			l.add(n);
			it.remove();
		}
		ArrayList<node> binHeap=new ArrayList<node>(l);
		ArrayList<node> fourWayHeap=new ArrayList<node>(l);
		ArrayList<node> PairingHeap=new ArrayList<node>(l);
		
		//binary heap
		binaryHeap bh=new binaryHeap();
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < 10; i++){    //run 10 times on given data set 
			bh.build_tree_using_binary_heap(binHeap);	
		}
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Time Using Binary Heap:"+(stopTime-startTime)+" MilliSec");
		
		//pairing heap
		pairingHeap ph=new pairingHeap();
		startTime = System.currentTimeMillis();
		for(int i = 0; i < 10; i++){    //run 10 times on given data set 
			ph.build_tree_using_pairing_heap(PairingHeap);	
		}
		
	    stopTime = System.currentTimeMillis();
		System.out.println("Pairing Heap Time:"+(stopTime-startTime)+" MilliSec");
		
		
		//four Way heap
		
		node dummy=new node();
		fourWayHeap.add(0,dummy);
		fourWayHeap.add(0,dummy);
		fourWayHeap.add(0,dummy);
		fourWayHeap fh=new fourWayHeap();
		startTime = System.currentTimeMillis();
		for(int i = 0; i < 10; i++){    //run 10 times on given data set 
			fh.build_tree_using_4way_heap(fourWayHeap,INPUTFILE);			
		}
		stopTime = System.currentTimeMillis();
		System.out.println("Time for 4 way:"+(stopTime-startTime)+" MilliSec");
	}

}
