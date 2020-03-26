package org.pesmypetcare.usermanagerlib.datacontainers;

public class Pet {
    private String name;
    private PetData body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetData getBody() {
        return body;
    }

    public void setBody(PetData body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "{"
            + "name='" + name + '\''
            + ", body=" + body
            + '}';
    }
}
