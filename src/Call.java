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
    public String getStatus(){

        if(status == 0) {
            return "Call Id: " + this.callId + "In Coming...";
        }
        else if (status == 1) {
            return "Call Id: " + this.callId + "In Progress";
        }
        else if (status == 2) {
            return "Call Id: " + this.callId + "Declined...";
        }
        return "Call Id: " + this.callId + "Answered...";
    }
}
