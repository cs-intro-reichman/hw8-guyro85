/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }
    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        if (name == null){
            return null;
        }
        for (User user : users){
            if (user != null && user.getName().toLowerCase().equals(name.toLowerCase())){
                return user;
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (getUser(name) != null || userCount >= users.length){
            return false;
        }
        User newUser = new User(name);
        for (int i = 0; i < users.length; i++){
            if (users[i] == null){
                users[i] = newUser;
                break;
            }
        }
        userCount++;
        return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        if (getUser(name1) == null || getUser(name2) == null){
            return false;
        }
        if (name1.equals(name2)){
            return false;
        }
        if (getUser(name1).addFollowee(name2)){
            return true;
        }
        return false;
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User recomendedUser = users[0];
        User current = getUser(name);
        int maxMutualFollows = 0;
        int mutualFollows = 0;
        for (User user : users) {
            if (user == null || user.getName().equals(name)){
                continue;
            }
            for (String fol : user.getfFollows()) {
                if (fol == null){
                    continue;
                }
                if (current.follows(fol)){                   
                    mutualFollows++;
                }
            }
            if (mutualFollows > maxMutualFollows){
                maxMutualFollows = mutualFollows;
                recomendedUser = user;
            }
            mutualFollows = 0;
        }

        return recomendedUser.getName();
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        User popular = users[0];
        int maxFollows = 0;
        int follows = 0;
        for (User user : users) {
            if (user == null){
                continue;
            }
            follows = followeeCount(user.getName());
            if (follows > maxFollows){
                popular = user;
                maxFollows = follows;
            }
            follows = 0;
        }
        if (popular == null){
            return null;
        }
        return popular.getName();
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int count = 0;
        for (User user : users) {
            if (user == null){
                continue;
            }
            if (user.follows(name)){
                count++;
            }
        }
        return count;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        String ans = "Network:";
       for (User user : users) {
        if (user != null){
            ans += "\n" + user.toString() ;
        }
       }
       return ans;
    }
}
