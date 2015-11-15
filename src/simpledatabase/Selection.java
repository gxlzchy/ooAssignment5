package simpledatabase;
import java.util.ArrayList;

public class Selection extends Operator{
	
	ArrayList<Attribute> attributeList;
	String whereTablePredicate;
	String whereAttributePredicate;
	String whereValuePredicate;

	
	public Selection(Operator child, String whereTablePredicate, String whereAttributePredicate, String whereValuePredicate) {
		this.child = child;
		this.whereTablePredicate = whereTablePredicate;
		this.whereAttributePredicate = whereAttributePredicate;
		this.whereValuePredicate = whereValuePredicate;
		attributeList = new ArrayList<Attribute>();
	}
	
	
	/**
     * Get the tuple which match to the where condition
     * @return the tuple
     */
	@Override
	public Tuple next(){
		Tuple tuple;
		while (true){
			tuple = child.next();
			if (tuple==null) return null;
			else{
				int i;
				boolean conditionFound=false;
				for (i=0;i<tuple.attributeList.size();i++){

//					System.out.print(tuple.getAttributeName(i)+' ');
//					System.out.println(tuple.getAttributeValue(i));
//					System.out.print(whereValuePredicate+' ');
//					System.out.println(tuple.getAttributeValue(i).equals('"'+whereValuePredicate+'"'));
					if (tuple.getAttributeName(i).equals(whereAttributePredicate)){
						conditionFound=true;
						if (tuple.getAttributeValue(i).equals('"'+whereValuePredicate+'"')
								||tuple.getAttributeValue(i).equals(whereValuePredicate))
							return tuple;
						else break;
					}	
				}
				if (!conditionFound) return tuple;
			}
		}	
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		
		return(child.getAttributeList());
	}

	
}