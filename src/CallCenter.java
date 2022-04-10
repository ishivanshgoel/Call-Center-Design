import java.util.*;

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
    // checks user avaiability and assigns the call if someone is available

    public User incomingCall() {
        Call newCall = new Call(); // create new call object
        if(ch.isUserAvailable() == true){
            // call assigned to user
            User user = ch.assignUser();
            newCall.pickup(user);
            System.out.println("User : " + user.getName() + " assigned the call!!");
            return user;
        } else {
            // user not available so queue the call
            ch.queueCall(newCall);
            System.out.println("All our customer executives are Busy at the moment.. Someone will take your call soon!!");
            return new User("---Dummy---", 1);
        }
    }


    // tester
    public static void main(String args[]){
        CallCenter cc = new CallCenter();
        User user1 = cc.incomingCall();
        User user2 = cc.incomingCall();
        User user3 = cc.incomingCall();
        User user4 = cc.incomingCall();
        User user5 = cc.incomingCall();
        User user6 = cc.incomingCall();
        User user7 = cc.incomingCall();
        User user8 = cc.incomingCall();
    }

}
