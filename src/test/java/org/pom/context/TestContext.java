package org.pom.context;

public class TestContext {

    private static final ThreadLocal<TestContext> INSTANCE =
            ThreadLocal.withInitial(TestContext::new);

    private String email;
    private String password;
    private String username;
    private String ticketTitle;
    private String ticketDescription;

    private TestContext() {}

    public static TestContext get() {
        return INSTANCE.get();
    }

    public static void reset() {
        INSTANCE.remove();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getTicketTitle() { return ticketTitle; }
    public void setTicketTitle(String ticketTitle) { this.ticketTitle = ticketTitle; }

    public String getTicketDescription() { return ticketDescription; }
    public void setTicketDescription(String ticketDescription) { this.ticketDescription = ticketDescription; }
}
