import java.util.*;

/**
 * Account class implements basic account operations. This class implements
 * AccountInt interface.
 *
 * @author seyda
 */
public class Account implements AccountInt {

	/**
	 * This method signs user in.
	 *
	 * @param sessions ArrayDeque
	 * @param accounts HashMap
	 * @param username String
	 * @param password String
	 */
	@Override
	public void signIn(ArrayDeque<String> sessions, HashMap<String, String> accounts, String username, String password) {
		if (accounts.get(username) != null) {
			if (accounts.get(username).equals(password)) {
				sessions.offer(username);
				System.out.println("User signed in.");
			} else {
				System.out.println("Password does not match with username.");
			}
		} else {
			System.out.println("Username could not be found.");
		}
	}

	/**
	 * This method signs user up.
	 *
	 * @param accounts HashMap
	 * @param username String
	 * @param password String
	 */
	@Override
	public void signUp(HashMap<String, String> accounts, String username, String password) {
		if (accounts.get(username) == null) {
			accounts.put(username, password);
			System.out.println("User signed up.");
		} else {
			System.out.println("Username must be unique.");
		}
	}

	/**
	 * This method signs user out.
	 *
	 * @param sessions ArrayDeque
	 */
	@Override
	public void signOut(ArrayDeque<String> sessions) {
		sessions.poll();
		System.out.println("User signed out.");
	}
}
