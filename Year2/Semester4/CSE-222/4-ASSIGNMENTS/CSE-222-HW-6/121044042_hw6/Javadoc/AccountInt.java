import java.util.*;

/**
 * AccountInt interface defines basic account operations.
 *
 * @author seyda
 */
public interface AccountInt {

	/**
	 * This method signs user in.
	 *
	 * @param sessions ArrayDeque
	 * @param accounts HashMap
	 * @param username String
	 * @param password String
	 */
	public void signIn(ArrayDeque<String> sessions, HashMap<String, String> accounts, String username, String password);

	/**
	 * This method signs user up.
	 *
	 * @param accounts HashMap
	 * @param username String
	 * @param password String
	 */
	public void signUp(HashMap<String, String> accounts, String username, String password);

	/**
	 * This method signs user out.
	 *
	 * @param sessions ArrayDeque
	 */
	public void signOut(ArrayDeque<String> sessions);
}
