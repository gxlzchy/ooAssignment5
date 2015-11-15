package simpledatabase;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Table extends Operator{
	private BufferedReader br = null;
	private boolean getAttribute=false;
	private Tuple tuple;
	private String attributeNameLine,attributeColumnLine;
	public String from;
	
	public Table(String from){
		this.from = from;
		
		//Create buffer reader
		try{
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/datafile/"+from+".csv")));
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	
	/**
     * Create a new tuple and return the tuple to its parent.
     * Set the attribute list if you have not prepare the attribute list
     * @return the tuple
     */
	@Override
	public Tuple next(){
		if (!getAttribute){
			//read first two lines.
	        try {
				if ((attributeNameLine = br.readLine()) != null);
				if ((attributeColumnLine = br.readLine()) != null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        getAttribute=true;
		}
		
		String dataLine = null;
		try {
			if ((dataLine = br.readLine()) != null);
			else return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        tuple=new Tuple(attributeNameLine,attributeColumnLine,dataLine);
        tuple.setAttributeName();
        tuple.setAttributeType();
        tuple.setAttributeValue();
        return tuple;
	}
	

	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return tuple.getAttributeList();
	}
	
	public static void main(String[] args) {
		Table table=new Table("Student");
		Selection selection=new Selection(table,"Student","Programme","61031");
		Projection projection=new Projection(selection, "Name");
		Tuple tuple=projection.next();
		while (tuple!=null){
			for (int i=0;i<tuple.getAttributeList().size();i++){
				System.out.print(tuple.getAttributeList().get(i).getAttributeName()+' ');
				System.out.println(tuple.getAttributeList().get(i).getAttributeValue());
			}
			tuple=projection.next();
		}
	}
}