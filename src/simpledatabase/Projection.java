package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator{
	
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;

	public Projection(Operator child, String attributePredicate){
		
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();
		
	}
	
	
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next(){
		Tuple tuple=child.next();
		if (tuple==null) return null;
		for (int i=0;i<tuple.getAttributeList().size();i++){
			//System.out.println(tuple.getAttributeName(i));
			if (tuple.getAttributeName(i).equals(attributePredicate)){
				Attribute attr=tuple.getAttributeList().get(i);
				newAttributeList=new ArrayList<Attribute>();
				newAttributeList.add(attr);
				return new Tuple(newAttributeList);
			}		
		}	
		return null;
		
	}
		

	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}