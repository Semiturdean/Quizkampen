package quizkampen;

public class Player 
{
	private String username;
	private String password;
	private String membershipType;
	//
	public Player()
	{
		membershipType = "basic";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = ((membershipType == "basic") || (membershipType == "premium") ? membershipType : null);
	}
	
	

}
