package localhost.potlatchclient.repository;

import java.util.Comparator;

import com.google.common.base.Objects;

/**
 * A simple object to represent a gift chain.
 * 
 */
public class Chain implements Comparator<Chain> {

	private long id;

	private String name;
	
	public Chain() {
	}

	public Chain(String name) {
		super();
		this.name    = name;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * Two chains will generate the same hashcode if they have exactly the same
	 * values for their id and name.
	 * 
	 */
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(id, name);
	}

	/**
	 * Two chains are considered equal if they have exactly the same values for
	 * their name.
	 * 
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Chain) {
			Chain other = (Chain) obj;
			// Google Guava provides great utilities for equals too!
			return  Objects.equal(name, other.name) &&
					Objects.equal(id  , other.id  );
		} else {
			return false;
		}
	}

	@Override
	public int compare(Chain lhs, Chain rhs) {
		if (lhs.getId() < rhs.getId()) return -1;
		if (lhs.getId() > rhs.getId()) return +1;
		return 0;
	}

}
