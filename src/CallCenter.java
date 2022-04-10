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
