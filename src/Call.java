public class Call {
    private int status;
    private User handledBy;

    Call () {
        status = 0;
    }

    // mark call as pickup status
    public void pickup(User user) {
        // complete the call
        status = 1;
        handledBy = user;
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
            return "In Coming...";
        }
        else if (status == 1) {
            return "In Progress";
        }
        else if (status == 2) {
            return "Declined...";
        }
        return "Answered...";
    }
}
