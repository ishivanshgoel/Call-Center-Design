import java.security.spec.ECField;

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