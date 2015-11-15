package simpledatabase;
import java.util.ArrayList;

public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;
	private int cur=-1;

	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
		
	}
	
	private int compare(Attribute a, Attribute b){
		Type type=a.getAttributeType();
		switch(type.type){
			case INTEGER:
				return Integer.compare((Integer)a.getAttributeValue(), (Integer)b.getAttributeValue());
				
			case DOUBLE:
				return Double.compare((Double)a.getAttributeValue(), (Double)b.getAttributeValue());
				
			case LONG:
				return Long.compare((Long)a.getAttributeValue(), (Long)b.getAttributeValue());
			
			case SHORT:
				return Short.compare((Short)a.getAttributeValue(), (Short)b.getAttributeValue());
				
			case FLOAT:
				return Float.compare((Float)a.getAttributeValue(), (Float)b.getAttributeValue());
			
			case STRING:
				return String.valueOf(a.getAttributeValue()).compareTo(String.valueOf(b.getAttributeValue()));
			
			case BOOLEAN:
				int val1 = (Boolean)a.getAttributeValue()? 1 : 0;
				int val2 = (Boolean)b.getAttributeValue()? 1 : 0;
				return Integer.compare(val1, val2);
			
			case CHAR:
				return Character.compare((char)a.getAttributeValue(), (char)b.getAttributeValue());
			
			case BYTE:
				return Byte.compare((Byte)a.getAttributeValue(), (Byte)b.getAttributeValue());
		}
		return 0;
	}
	
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next(){
		if (tuplesResult.size()==0){
			while (true){
				Tuple temp=child.next();
				if (temp!=null) tuplesResult.add(temp);
				else break;
			}
			if (tuplesResult.size()==0) return null;
			
			//calculate compare index
			int compareIndex=-1;
			Tuple sampleTuple=tuplesResult.get(0);
			for (int i=0;i<sampleTuple.getAttributeList().size();i++)
				if (sampleTuple.getAttributeName(i).equals(orderPredicate)
						||sampleTuple.getAttributeName(i).equals('"'+orderPredicate+'"'))
							compareIndex=i;
			
			//selection sort
			for (int i=0;i<tuplesResult.size()-1;i++)
				for (int j=i+1;j<tuplesResult.size();j++){
					Attribute left=tuplesResult.get(i).getAttributeList().get(compareIndex);
					Attribute right=tuplesResult.get(j).getAttributeList().get(compareIndex);
					if (compare(left,right)>0){
						Tuple temp=tuplesResult.get(i);
						tuplesResult.set(i, tuplesResult.get(j));
						tuplesResult.set(j, temp);
					}
				}
		}
		cur++;
		if (cur<tuplesResult.size()) return tuplesResult.get(cur);
		else return null;
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}