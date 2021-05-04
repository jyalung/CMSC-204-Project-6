package project6;

import java.util.ArrayList;

public class Town implements Comparable<Town>{
	private String name;
	private ArrayList<Town> adjacentTowns;

	public Town(String name) {
		this.name = name;
		adjacentTowns = new ArrayList<Town>();
	}

	public Town(Town templateTown) {
		this.name = templateTown.name;
		
		this.adjacentTowns = new ArrayList<Town>();
		
		for(Town t: templateTown.adjacentTowns) {
			this.adjacentTowns.add(t);
		}
	}

	@Override
	public int compareTo(Town t) {
		return name.compareTo(t.name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		
		if(!(obj instanceof Town))
			return false;
		
		Town t = (Town) obj;
		
		return this.name.equals(t.name);
			
	}

	public String getName() {
		return name;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}

	public void addAdjacentTown(Town t) {
		if(adjacentTowns.contains(t))
			return;
		
		adjacentTowns.add(t);
	}

	public void removeAdjacentTown(Town t) {
		adjacentTowns.remove(t);
	}

	public boolean isAdjacentTo(Town t) {
		return adjacentTowns.contains(t);
	}
}