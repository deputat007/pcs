package restservice.utils;

public class Phones {
    private String idMessage;
    private String variables;
    private String phone;

    public Phones(String idMessage, String variables, String phone) {
        this.phone = phone;
        this.variables = variables;
        this.idMessage = idMessage;
    }

    public String getIdMessage() {
        return this.idMessage;
    }

    public String getVariable() {
        return this.variables;
    }

    public String getPhone() {
        return this.phone;
    }

}
