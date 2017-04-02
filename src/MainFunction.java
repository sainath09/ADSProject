import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MainFunction {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		Map<Integer, Long> fre=new HashMap<Integer, Long>();
		FileReader fr=null;
		try {
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
			node root=bh.build_tree_using_binary_heap(binHeap);	
		}
		
		long stopTime = System.currentTimeMillis();
		System.out.println(stopTime-startTime+" MilliSec");
		
		//four Way heap
		
		node dummy=new node();
		fourWayHeap.add(0,dummy);
		fourWayHeap.add(0,dummy);
		fourWayHeap.add(0,dummy);
		fourWayHeap fh=new fourWayHeap();
		startTime = System.currentTimeMillis();
		for(int i = 0; i < 1; i++){    //run 10 times on given data set 
			node root=fh.build_tree_using_4way_heap(fourWayHeap);			
		}
		stopTime = System.currentTimeMillis();
		System.out.println(stopTime-startTime+" MilliSec");
		
		//pairing Heaps
		
		
				
	}

}
