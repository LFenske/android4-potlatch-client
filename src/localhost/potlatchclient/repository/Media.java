package localhost.potlatchclient.repository;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Objects;

/**
 * A simple object to represent a media and its URL for viewing.
 * 
 */
public class Media {

	private long id;

	private String name;
	private String url;
	private String descr;
	private long   chainid;
	private long   likes;
	private long   flags;
	private Set<String> likers   = new HashSet<String>();
	private Set<String> flaggers = new HashSet<String>();
	
	public Media() {
		this.likes = 0;
		this.flags = 0;
	}

	public Media(String name, String url, String descr, long chainid, long likes, long flags) {
		super();
		this.name    = name;
		this.url     = url;
		this.descr   = descr;
		this.chainid = chainid;
		this.likes   = likes;
		this.flags   = flags;
	}

	public void addLiker(String user) {
		this.likes++;
		this.likers.add(user);
	}
	
	public void delLiker(String user) {
		this.likes--;
		this.likers.remove(user);;
	}
	

	public void addFlagger(String user) {
		this.flags++;
		this.flaggers.add(user);
	}
	
	public void delFlagger(String user) {
		this.flags--;
		this.flaggers.remove(user);;
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

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public String getDescr() {
		return descr;
	}

	public void setDesc(String descr) {
		this.descr = descr;
	}

	
	public long getChainid() {
		return chainid;
	}
	
	public void setChainid(long chainid) {
		this.chainid = chainid;
	}


	public long getLikes() {
		return likes;
	}
	
	public void setLikes(long likes) {
		this.likes = likes;
	}


	public Set<String> getLikers() {
		return likers;
	}
	
	public void setLikers(Set<String> likers) {
		this.likers = likers;
	}


	public long getFlags() {
		return flags;
	}
	
	public void setFlags(long flags) {
		this.flags = flags;
	}


	public Set<String> getFlaggers() {
		return flaggers;
	}
	
	public void setFlaggers(Set<String> flaggers) {
		this.flaggers = flaggers;
	}


	/**
	 * Two media will generate the same hashcode if they have exactly the same
	 * values for their name, url, and duration.
	 * 
	 */
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(name, url);
	}

	/**
	 * Two media are considered equal if they have exactly the same values for
	 * their name and url.
	 * 
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Media) {
			Media other = (Media) obj;
			// Google Guava provides great utilities for equals too!
			return  Objects.equal(name, other.name) &&
					Objects.equal(url , other.url );
		} else {
			return false;
		}
	}

}
