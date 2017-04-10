
public class file {
	public int index;
	public String name;
	public String location;
	public String tag;
	public float[] character;
	public int match;
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setLocation(String location){
		this.location = location;
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}
	
	public void setCharacter(float[] character){
		this.character = character;
	}
	
	public int getIndex(){
		return index;
	}
	
	public String getName(){
		return name;
	}
	
	public String getLocation(){
		return location;
	}
	
	public String getTag(){
		return tag;
	}
	
	public float[] getCharacter(){
		return character;
	}
}
