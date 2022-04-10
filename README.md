
## Object Oriented Design of Call Center
 Reference: https://github.com/donnemartin/system-design-primer/blob/master/solutions/object_oriented_design/call_center/call_center.ipynb
- Constraints and assumptions
  -What levels of employees are in the call center?
    - Operator
    - supervisor
    - director
 - Can we assume operators always get the initial calls?
    - Yes
 - If there is no available operators or the operator can't handle the call, does the call go to the supervisors?
   - Yes
 - If there is no available supervisors or the supervisor can't handle the call, does the call go to the directors?
   - Yes
 - Can we assume the directors can handle all calls?
   - Yes
 - What happens if nobody can answer the call?
   - It gets queued
 - Do we need to handle 'VIP' calls where we put someone to the front of the line?
   - No
 - Can we assume inputs are valid or do we have to validate them?
   - Assume they're valid

### Call - creates new call and handles related activities
```.java
public class Call {
    private int status;
    private User handledBy;
    private static int id = 0;
    private int callId;

    Call () {
        status = 0;
        id ++;
        this.callId = id;
    }

    // mark call as pickup status
    public void pickup(User user) {
        // complete the call
        this.status = 1;
        this.handledBy = user;
        System.out.println("Call Picked by "+user.getName());
    }

    // mark call as declined
    public void decline() {
        status = 2;
    }

    // mark call status as answered
    public void answer() {
        status = 3;
    }

    // returns the status of call
    public String getStatus()
    
    }
}

```


### User - handles user related activites 
```.java
public class User {
    private String name; // (name of the user)
    private int type; // type of user (operator, supervisor or director)
    private int status; // current status of user - 1 for free, 2 for busy


    // initializes new user
    User(String name, int type) {

        try {
            if (name.length() == 0) {
                throw new Exception("Name is Required!");
            }
            if (type <= 0 || type >= 4) {
                throw new Exception("Invalid user type!");
            }

            this.name = name;
            this.type = type;
            this.status = 1;

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    // returns the name of user
    public String getName() {
        return this.name;
    }

    // returns Type of user
    public String getType() {
        switch (this.type) {
            case 1:
                return "Operator";
            case 2:
                return "Supervisor";
            case 3:
                return "Director";
            default:
                return "Invalid User";
        }
    }

    // returns status of user
    public String getStatus() {
        if(this.status == 1){
            return "Available";
        } else return "Busy";
    }

    // assign call to the user
    public void assignCall() {
        try {
            if (this.status == 1) {
                this.status = 2;
            } else throw new Exception("User: " + this.name + " is busy!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String declineCall() {
        if(this.type == 3){
            return "Done!";
        } else return "Permission Denied!";
    }

    // mark call as finished
    public void finishCall(Call call) {
        call.answer();
        this.status = 1;
    }

}
```

## Call Handler - Receives a call and assign it to the available customer care executive
```.java
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
                this.availableUsers--;
                return user;
            }
        }
        return null;
    }

    // mark call as answered
    public void callAnswered(User user, Call call) {
        this.availableUsers++;
        System.out.println("Call Completed by "+user.getName());
        user.finishCall(call);
    }

    // queue the call
    public Boolean queueCall(Call call) {
        // add call to queue
        callQueue.add(call);
        return true;
    }

    // returns call queue
    public Queue<Call> callQueue() {
        return this.callQueue;
    }

}

```

## Call Center - Driver class
```.java
import java.util.*;

// util for IncomingCall return type
class IncomingCall {
    User user;
    Call call;
    IncomingCall(User u, Call c) {
        this.user = u;
        this.call = c;
    }
}

public class CallCenter {

    // accepts incoming call
    private ArrayList<User> users = new ArrayList<User>();
    private CallHandler ch;

    CallCenter() {

        // new users
        User operator1 = new User("Operator 1", 1);

        User operator2 = new User("Operator 2", 1);
        User operator3 = new User("Operator 3", 1);
        User supervisor1 = new User("Supervisor 1", 2);
        User supervisor2 = new User("Supervisor 2", 2);
        User director1 = new User("Director 1", 3);

        users.add(operator1);
        users.add(operator2);
        users.add(operator3);
        users.add(supervisor1);
        users.add(supervisor2);
        users.add(director1);

        ch = new CallHandler(users);

    }

    // handles incoming call
    // checks user availability and assigns the call if someone is available
    public IncomingCall incomingCall() {

        Call newCall = new Call(); // create new call object

        try{
            if(ch.isUserAvailable() == true){
                // call assigned to user
                User user = ch.assignUser();

                if(user == null) {
                    throw new Exception("All our customer executives are Busy at the moment.. Someone will take your call soon!!");
                }

                newCall.pickup(user);
                System.out.println("User : " + user.getName() + " assigned the call!!");
                return new IncomingCall(user, newCall);
            } else {
                throw new Exception("All our customer executives are Busy at the moment.. Someone will take your call soon!!");
            }

        } catch(Exception e) {
            // customer executive is not available so queue the call
            ch.queueCall(newCall);
            System.out.println(e.getMessage());
        }

        return null;
    }

    // marks call as complete
    public void completeCall(IncomingCall call) {
        ch.callAnswered(call.user, call.call);
    }

    // display all pending calls
    public void pendingCalls() {
        Queue<Call> callQueue = ch.callQueue();
        for(Call call: callQueue){
            System.out.println(call.getStatus());
        }
    }


    // tester
    public static void main(String args[]){
        CallCenter cc = new CallCenter();
        IncomingCall c1 = cc.incomingCall();
        IncomingCall c2 = cc.incomingCall();
        IncomingCall c3 = cc.incomingCall();
        IncomingCall c4 = cc.incomingCall();
        IncomingCall c5 = cc.incomingCall();
        IncomingCall c6 = cc.incomingCall();
        IncomingCall c7 = cc.incomingCall();
        IncomingCall c8 = cc.incomingCall();

        cc.completeCall(c1);
        cc.completeCall(c2);
        cc.completeCall(c3);

        IncomingCall c9 = cc.incomingCall();
        IncomingCall c10 = cc.incomingCall();
        IncomingCall c11 = cc.incomingCall();

        cc.pendingCalls();

    }

}

```
