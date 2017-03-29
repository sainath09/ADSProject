import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class node{
	int data;
	int freq;
	node(){
		data=0;
		freq=0;
	}
	int getData(){
		return this.data;
	}
	int getFreq(){
		return this.freq;
	}
}
class huffManTree{
	int data;
	int freq;
	huffManTree left;
	huffManTree right;
	huffManTree(int dat,int fre){
		data=dat;
		freq=fre;
	}
}
class binaryHeap{
	int left(int i){
		return (2*i)+1;
	}
	int right(int i){
		return (2*i)+2;
	}
	void heapify(ArrayList<node> l,int i,int m){
		int le=left(i);
		int re=right(i);
		int min=i;
		
		if(le<m && l.get(le).getFreq()<l.get(min).getFreq()) min=le;
		if(re<m && l.get(re).getFreq()<l.get(min).getFreq()) min=re;
		if(min!=i){
			Collections.swap(l,i,min);
			//System.out.println(l.get(i).getFreq());
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
}
public class MainFunction {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		
		
		Map<Integer, Integer> fre=new HashMap<Integer, Integer>();
		FileReader fr=null;
		try {
			//fr = new FileReader("example.txt");
			fr=new FileReader("/home/kps/workspace/ADSProject/src/example.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<node> l=new ArrayList<node>();
		BufferedReader br=new BufferedReader(fr);
		String S=new String();
		
		while((S=br.readLine())!=null){
			int temp=Integer.parseInt(S);
			if(fre.containsKey(temp)) fre.put(temp, (int)fre.get(temp)+1);
			else fre.put(temp, 1);
			
		}
		Iterator it=fre.entrySet().iterator();
		while(it.hasNext()){
			node n=new node();
			Map.Entry temp=(Map.Entry)it.next();
			n.data=(int)temp.getKey();
			n.freq=(int)temp.getValue();
			l.add(n);
			//System.out.println(n.data+ " "+n.freq);
			it.remove();
		}
		//binary heap
		binaryHeap bh=new binaryHeap();
		bh.buildHeap(l,l.size());
		for(int i=0;i<l.size();i++){
			node t=l.get(i);
			//System.out.println(t.data+ " "+t.freq);
			//System.out.println(l.get(i).getData()+" "+l.get(i).getFreq());
		}
		long startTime = System.currentTimeMillis();
		while(l.size()>1){
			node n1=bh.extract_min(l);
			node n2=new node();
			n2=bh.extract_min(l);
			node n3=new node();
			n3.data=0;
			n3.freq=n1.freq+n2.freq;
			l.add(n3);
			bh.heapify(l, l.size()-1, l.size());
		}
		for(int i=0;i<l.size();i++){
			node t=l.get(i);
			//System.out.println(t.data+ " "+t.freq);
			System.out.println(l.get(i).getData()+" "+l.get(i).getFreq());
		}
		long stopTime = System.currentTimeMillis();
		System.out.println(stopTime-startTime+"MilliSec");
		
	}

}
