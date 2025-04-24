package ultilities;

import com.github.javafaker.Faker;

public class DataUltilities {
    private Faker faker;
    public static DataUltilities getData(){
        return new DataUltilities();
    }
    public DataUltilities(){
        faker = new Faker();
    }
    public String getFirstName(){
        return faker.name().firstName();
    }
    public String getLastName(){
        return faker.name().lastName();
    }
    public String getMiddleName(){
        return faker.name().nameWithMiddle();
    }
    public String getUsername (){
        return faker.name().username();
    }
    public String getPassword () {
        return faker.internet().password(10, 20, true, true);
    }
}
