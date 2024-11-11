package Sesion09.Actividad1.Model;

public class UserInf {
    private String username, stadistics, description;
    private int value;

    public UserInf(String _username, String _stadistics, String _description, int _value) {
        this.username = _username;
        this.stadistics = _stadistics;
        this.description = _description;
        this.value = _value;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String _username) {
        this.username = _username;
    }

    public String getStadistics() {
        return stadistics;
    }
    public void setStadistics(String _stadistics) {
        this.stadistics = _stadistics;
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String _description) {
        this.description = _description;
    }

    public int getValue() {
        return value;
    }
    public void setValue(int _value) {
        this.value = _value;
    }

    @Override
    public String toString() {
        return String.format(
            "Username: %s\n"
            + "Stadistics: %s\n"
            + "Description: %s\n"            
            + "Value: %d\n"
            , username, stadistics, description, value
        );
    }
}
