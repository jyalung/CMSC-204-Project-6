package project6;

public class Road implements Comparable<Road> {
	private Town source;
	private Town destination;
	private int degrees;
	private String name;

	public Road(Town source, Town destination, int degrees, String name) {
		this.source = new Town(source);
		this.destination = new Town(destination);
		this.name = name;
		this.degrees = degrees;
	}

	public Road(Town source, Town destination, String name) {
		this.source = new Town(source);
		this.destination = new Town(destination);
		this.name = name;
		this.degrees = 1;
	}

	@Override
	public int compareTo(Road otherRoad) {
		return this.name.compareTo(otherRoad.name);
	}

	public boolean contains(Town town) {
		return this.source.equals(town) || this.destination.equals(town);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (!(obj instanceof Road))
			return false;

		Road r = (Road) obj;

		return (this.source.equals(r.source) && this.destination.equals(r.destination))
				|| (this.source.equals(r.destination) && this.destination.equals(r.source));
	}
	
	public Town getDestination() {
		return destination;
	}
	
	public String getName() {
		return name;
	}
	
	public Town getSource() {
		return source;
	}

	public int getWeight() {
		return degrees;
	}

	@Override
	public String toString() {
		return source.getName() + " via " + name + " to " + destination.getName() + " " + degrees + " mi";
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

}