import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CallHandler {
    // maintains list of users
    private ArrayList<User> users = new ArrayList<User>();

    // maintains call queue
    private Queue<Call> callQueue = new LinkedList<Call>();

    // maintains count of available users
    private int availableUsers;

    // initilizes object with the list of users
    CallHandler(ArrayList<User> users) {
        this.users = users;
        this.availableUsers = this.users.size();
    }

    // check user availablity
    public Boolean isUserAvailable() {
        if(this.availableUsers > 0) {
            return true;
        }
        return false;
    }

    // assigns call to a user
    public User assignUser() {

        // assign to a user
        for(int i = 0; i < users.size(); i++) {
            if (users.get(i).getStatus() == "Available") {
                users.get(i).assignCall();
                User user = users.get(i);
                availableUsers--;
                return user;
            }
        }
        return null;
    }

    // mark call as answered
    public void callAnswered(User user, Call call) {
        this.availableUsers++;
        user.finishCall(call);
    }

    // queue the call
    public Boolean queueCall(Call call) {
        // add call to queue
        callQueue.add(call);
        return true;
    }

}
