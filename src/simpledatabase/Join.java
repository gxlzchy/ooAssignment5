package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;	//save the tuples got from left child.

	
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
		
	}

	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){
		while (true){
			Tuple rightTuple=this.rightChild.next();
			if (rightTuple==null)	return null;
			if (tuples1.size()==0){
				Tuple leftTuple;
				while (true){
					leftTuple=leftChild.next();
					if (leftTuple !=null) tuples1.add(leftTuple);
					else break;
				}
			}
			if (tuples1.size()==0) return null;
			for (int i=0;i<tuples1.size();i++){//find a correspond left tuple.
				boolean correspond=true, nameMatched=false;
				Tuple leftTuple=tuples1.get(i);
				for (int r=0;r<rightTuple.getAttributeList().size();r++)
					for (int l=0;l<leftTuple.getAttributeList().size();l++)
						if (rightTuple.getAttributeName(r).equals(leftTuple.getAttributeName(l))){
							nameMatched=true;
							if (!rightTuple.getAttributeValue(r).equals(leftTuple.getAttributeValue(l)))
								correspond=false;//one of attribute's name is same, value is different;
						}
							
				if (nameMatched && correspond){//join two tuples(will not delete tuples.)
					newAttributeList=rightTuple.getAttributeList();
					for (int l=0;l<leftTuple.getAttributeList().size();l++){
						boolean needAdd=true;
						for (int r=0;r<newAttributeList.size();r++)
							if (rightTuple.getAttributeName(r).equals(leftTuple.getAttributeName(l)))
								needAdd=false;
						if (needAdd) newAttributeList.add(leftTuple.getAttributeList().get(l));
					}
					return new Tuple(newAttributeList);
				}
				else if (!nameMatched) return null;
			}
		}
	}
	
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}

}