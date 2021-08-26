package person;

public abstract class APerson {

    /**
     * Properties
     */

    private String id;
    private String name;
    private String email;

    /**
     * Setter Method
     */

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter Method
     */

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    /**
     * Display information
     */

    public abstract void showInformation();
}
